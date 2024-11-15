package com.library.management.modules.user.controller;

import com.library.management.model.RestResponse;
import com.library.management.modules.user.service.UserService;
import com.library.management.modules.user.transfer.LoginRequest;
import com.library.management.modules.user.transfer.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create New User")
    @PostMapping()
    RestResponse createUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
        return new RestResponse(true, "User created SuccessFully");
    }

    @Operation(summary = "User Login")
    @PostMapping("/login")
    RestResponse login(@RequestBody LoginRequest loginRequest){
        return new RestResponse(true, userService.loginAuthenticateUser(loginRequest));
    }

    @Operation(summary = "Delete User")
    @DeleteMapping("/id")
    RestResponse deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return new RestResponse(true, "Delete User SuccessFully");
    }

}
