package com.brian.controller;

import com.brian.exception.ExceptionHandling;
import com.brian.exception.domain.EmailExistException;
import com.brian.exception.domain.UserNotFoundException;
import com.brian.exception.domain.UsernameExistException;
import com.brian.model.User;
import com.brian.model.UserPrincipal;
import com.brian.service.UserService;
import com.brian.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static com.brian.constant.SecurityConstant.JWT_TOKEN_HEADER;

@RestController
@RequestMapping("/user")
public class UserController extends ExceptionHandling {

    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    private UserService userService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
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

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

}
