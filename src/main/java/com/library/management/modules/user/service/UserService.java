package com.library.management.modules.user.service;

import com.library.management.modules.user.transfer.LoginRequest;
import com.library.management.modules.user.transfer.LoginTransfer;
import com.library.management.modules.user.transfer.UserRequest;
import jakarta.transaction.Transactional;

public interface UserService {

    @Transactional
    void createUser(UserRequest userRequest);

    LoginTransfer loginAuthenticateUser(LoginRequest loginRequest);

    void deleteUser(Long id);
}
