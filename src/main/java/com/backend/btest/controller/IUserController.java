package com.backend.btest.controller;

import com.backend.btest.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserController {

    @ApiOperation(value = "Get all the users")
    ResponseEntity<List<User>> getAllUsers();

}