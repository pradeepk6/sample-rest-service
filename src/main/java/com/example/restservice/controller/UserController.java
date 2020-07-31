package com.example.restservice.controller;

import com.example.restservice.model.User;
import com.example.restservice.service.BpdtsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@Validated
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    BpdtsService bpdtsService;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return bpdtsService.findById(id);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return bpdtsService.findAll();
    }

    @GetMapping("/city/{city}/users")
    public List<User> getAllUsersInAndAroundCity(@PathVariable @NotBlank String city) {
        return bpdtsService.findUsersWithin(city);
    }
}
