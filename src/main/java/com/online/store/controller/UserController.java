package com.online.store.controller;

import com.online.store.dto.request.UserLoginRequest;
import com.online.store.dto.request.UserRequest;
import com.online.store.dto.response.UserLoginResponse;
import com.online.store.dto.response.UserResponse;
import com.online.store.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api("REST APIs related to User Entity")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "Register new user in system")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registryUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Request to register {}", userRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userRequest));
    }

    @ApiOperation(value = "User login with password and email")
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        log.info("Request to login {}", userLoginRequest);
        return ResponseEntity
                .ok()
                .body(userService.loginUser(userLoginRequest));
    }

    @ApiOperation(value = "View a list of users from the system")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<UserResponse> findAllUsers(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                           @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                           @RequestParam(required = false, defaultValue = "firstName") String sortBy) {
        log.info("Request to get all users {}", sortBy);
        return userService.findAllUsers(pageNumber, pageSize, sortBy);
    }

    @ApiOperation(value = "View a user by id from the system")
    @ApiImplicitParam(
            name = "id",
            value = "Id user in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id) {
        log.info("Request to get user by id {}", id);
        return ResponseEntity
                .ok()
                .body(userService.getUserById(id));
    }

    @ApiOperation(value = "Update an existing user in the system")
    @ApiImplicitParam(
            name = "id",
            value = "Id user in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> modifyUser(@RequestBody UserRequest userRequest,
                                                   @PathVariable Long id) {
        log.info("Request to modify user {}", userRequest);
        return ResponseEntity
                .ok()
                .body(userService.modifyUser(userRequest, id));
    }

    @ApiOperation(value = "Delete user from the system by id")
    @ApiImplicitParam(
            name = "id",
            value = "Id user in db",
            required = true,
            dataType = "Long",
            paramType = "path")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        log.info("Request to delete user {}", id);
        userService.deleteUser(id);
    }

}
