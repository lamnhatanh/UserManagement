package com.brian.service;

import com.brian.exception.domain.EmailExistException;
import com.brian.exception.domain.UserNotFoundException;
import com.brian.exception.domain.UsernameExistException;
import com.brian.model.User;

import java.util.List;


public interface UserService {

    User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException;
    List<User> getUsers();
    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
