package com.brian.controller;

import com.brian.exception.ExceptionHandling;
import com.brian.exception.domain.EmailExistException;
import com.brian.exception.domain.UserNotFoundException;
import com.brian.exception.domain.UsernameExistException;
import com.brian.model.User;
import com.brian.service.UserService;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestController
@RequestMapping("/user")
public class UserController extends ExceptionHandling {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, EmailExistException, UsernameExistException {
        User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
    @GetMapping("/home")
    public String homePage() {
        return "home page here!";
    }

}
