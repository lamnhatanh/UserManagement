package com.brian.controller;

import com.brian.exception.ExceptionHandling;
import com.brian.exception.domain.EmailExistException;
import jakarta.persistence.NoResultException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController extends ExceptionHandling {

    @GetMapping("/home")
    public String homePage() throws NoResultException {
        throw new NoResultException("This email has already taken!");
    }
}
