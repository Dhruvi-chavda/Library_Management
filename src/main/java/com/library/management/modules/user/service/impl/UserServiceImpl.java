package com.library.management.modules.user.service.impl;

import com.library.management.exception.NotAcceptableException;
import com.library.management.exception.NotFoundException;
import com.library.management.modules.user.domain.User;
import com.library.management.modules.user.repository.UserRepository;
import com.library.management.modules.user.service.UserService;
import com.library.management.modules.user.transfer.LoginRequest;
import com.library.management.modules.user.transfer.LoginTransfer;
import com.library.management.modules.user.transfer.UserRequest;
import com.library.management.utils.TokenUtils;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;


    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, TokenUtils tokenUtils, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    @Override
    public void createUser(UserRequest userRequest) {
        Optional<User> userOpt = userRepository.findByEmail(userRequest.getEmail());
        if (userOpt.isPresent()){
            throw new NotAcceptableException(NotAcceptableException.NotAcceptableExceptionMSG.USER_ALREADY_EXIST);
        }

        User newUser = new User();
        newUser.setUserName(userRequest.getUserName());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setEmail(userRequest.getEmail());
        newUser.setCreated(LocalDateTime.now());
        newUser.setUpdated(LocalDateTime.now());
        userRepository.save(newUser);
    }

    @Override
    public LoginTransfer loginAuthenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException(NotFoundException.UserNotFound.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new NotAcceptableException(NotAcceptableException.NotAcceptableExceptionMSG.PASSWORD_DOES_NOT_MATCH);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        loginAuthenticateUser(user.getEmail(), loginRequest.getPassword());
        return setJWTResponseData(userDetails);
    }

    private void loginAuthenticateUser(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("INVALID CREDENTIALS");
        }
    }

    public LoginTransfer setJWTResponseData(final UserDetails userDetails) {
        LoginTransfer loginTransfer = new LoginTransfer();
        String token = tokenUtils.generateToken(userDetails);
        loginTransfer.setAccessToken(token);
        loginTransfer.setTokenType("Bearer");
        loginTransfer.setEmail(userDetails.getUsername());
        return loginTransfer;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NotFoundException.UserNotFound.USER_NOT_FOUND));
        userRepository.delete(user);
    }
}
