package com.library.management.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.management.utils.TokenUtils;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class AuthenticationToken extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TokenUtils tokenUtils;
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    @Setter
    private UserDetailsService userDetailsService;
    @Value("${rate.limit.enabled}")
    private boolean rateLimitEnabled;
    @Value("${rate.limit.request.limit}")
    private Integer rateLimitRequestLimit;
    @Value("${rate.limit.request.time}")
    private Integer rateLimitRequestTime;

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain) throws IOException {

        String reqId = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        try {
            //put requestId in MDC
            MDC.put("requestId", reqId);

            String authToken = null;
            String userName = null;

            // If Regular CRM User
            String header = httpRequest.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                authToken = header.substring(7);
                //get client id
                userName = tokenUtils.getUserNameFromToken(authToken);
            }
            applyRateLimit(header, httpRequest);
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

                MDC.put("userName", userDetails.getUsername());

                if (Boolean.TRUE.equals(tokenUtils.validateToken(authToken, userDetails))) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            chain.doFilter(httpRequest, httpResponse);
        } catch (Exception ex) {

            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpResponse.setStatus(429);
        } finally {
            MDC.clear();
        }
    }


    private String getUrlKey(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String path = uri.getPath();
        // Remove slashes and split the path into segments
        String[] pathSegments = path.split("/");

        // Construct the new format
        StringBuilder newFormat = new StringBuilder();
        for (String segment : pathSegments) {
            if (!segment.isEmpty()) {
                newFormat.append(segment).append("-");
            }
        }
        newFormat.append("key");
        return newFormat.toString();
    }

    private boolean applyRateLimit(String header, HttpServletRequest httpRequest) throws URISyntaxException {
        if (!rateLimitEnabled) {
            return true;
        }
        if (null == header)
            header = getUrlKey(httpRequest.getRequestURI());
        Bucket bucket = buckets.computeIfAbsent(header, key -> createNewBucket());
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed())
            return true;
        else
            throw new RuntimeException("Too many requests");
    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.simple(rateLimitRequestLimit, Duration.ofSeconds(rateLimitRequestTime));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
