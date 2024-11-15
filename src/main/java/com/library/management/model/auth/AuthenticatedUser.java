package com.library.management.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticatedUser implements UserDetails, Serializable {
    @Serial
    @JsonIgnore
    private static final long serialVersionUID = 7483831456232557575L;

    @JsonIgnore
    private final String userName;

    @JsonIgnore
    private final String password;


    public AuthenticatedUser(String userName, String password) {
        this.userName = userName;
        this.password = password;

    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    @JsonIgnore
    public String getUsername() {
        return this.userName;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}

