//package com.blogapplication.controller;
//
//import com.blogapplication.payload.JwtRequest;
//import com.blogapplication.payload.JwtResponse;
//import com.blogapplication.security.JwtHelper;
//import com.blogapplication.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private AuthenticationManager manager;
//
//    @Autowired
//    private JwtHelper helper;
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/login")
//    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
//
//        this.doAuthenticate(request.getUsername(), request.getPassword());
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
//        String token = this.helper.generateToken(userDetails);
//
//        JwtResponse response = JwtResponse.builder()
//                .token(token)
//                .username(userDetails.getUsername()).build();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    private void doAuthenticate(String username, String password) {
//
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
//        try {
//            manager.authenticate(authentication);
//
//        } catch (BadCredentialsException e) {
//            throw new BadCredentialsException(" Invalid Username or Password  !!");
//        }
//    }
//}
package com.ecom.controller;

import com.ecom.Repo.UserRepo;
import com.ecom.entities.User;
import com.ecom.payload.JwtRequest;
import com.ecom.payload.JwtResponse;
import com.ecom.payload.RefreshTokenRequest;
import com.ecom.payload.UserDto;
import com.ecom.security.JwtHelper;
import com.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailsService userDetailsService;

//    @PostMapping("/login")
//    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
//        authenticate(request.getUsername(), request.getPassword());
//
//        // Load user details using the username
//        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
//        String token = jwtHelper.generateToken(userDetails);
//
//        // Retrieve the User object directly
//        User user = userRepo.findByEmail(request.getUsername());
//
//        // If the user is not found, return NOT_FOUND status
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        // Create the response with JWT token and roles
////        JwtResponse response = JwtResponse.builder()
////                .token(token)
//////                .refreshToken(jwtHelper.generateRefreshToken(userDetails.getUsername())) // Generate refresh token
////                .username(userDetails.getUsername())
////                .roles(userDetails.getAuthorities()
////                        .stream()
////                        .map(GrantedAuthority::getAuthority)
////                        .collect(Collectors.toList()))
////                .build();
////
////        return ResponseEntity.ok(response);
//        JwtResponse response = JwtResponse.builder()
//                .token(token)
//                .refreshToken(jwtHelper.generateRefreshToken(userDetails.getUsername())) // ✅ generate refresh token
//                .username(userDetails.getUsername())
//                .roles(userDetails.getAuthorities()
//                        .stream()
//                        .map(GrantedAuthority::getAuthority)
//                        .collect(Collectors.toList()))
//                .build();
//        return ResponseEntity.ok(response);
//
//    }
@PostMapping("/login")
public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
    authenticate(request.getUsername(), request.getPassword());

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    String token = jwtHelper.generateToken(userDetails);
    String refreshToken = jwtHelper.generateRefreshToken(userDetails.getUsername());

    User user = userRepo.findByEmail(request.getUsername());
    if (user == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    JwtResponse response = JwtResponse.builder()
            .token(token)
            .refreshToken(refreshToken) //  Include refresh token here
            .username(userDetails.getUsername())
            .roles(userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()))
            .build();

    return ResponseEntity.ok(response);
}

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        try {
            userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String username = jwtHelper.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtHelper.validateRefreshToken(refreshToken, userDetails)) {
            String newToken = jwtHelper.generateToken(userDetails);
            String newRefreshToken = jwtHelper.generateRefreshToken(username);
            return ResponseEntity.ok(JwtResponse.builder()
                    .token(newToken)
                    .username(username)
                    .refreshToken(newRefreshToken)
                    .roles(userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}

//    @PostMapping("/refresh-token")
//    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
//        // Extract the refresh token from the request
//        String refreshToken = refreshTokenRequest.getRefreshToken();
//
//        // Get the username from the refresh token
//        String username = helper.getUsernameFromToken(refreshToken);
//
//        // Load user details based on the username
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//        // Validate the refresh token
//        if (helper.validateRefreshToken(refreshToken, userDetails)) {
//            // Generate a new token, passing the UserDetails object
//            String newToken = helper.generateToken(userDetails); // Use userDetails, not just username
//
//            // Return the new JWT token with the existing refresh token in the response
//            return ResponseEntity.ok(JwtResponse.builder()
//                    .token(newToken)  // New JWT token
//                    .username(username)  // Username
//                    .refreshToken(refreshToken)  // The original refresh token
//                    .build());
//        } else {
//            // If the token is not valid, return an Unauthorized response
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }




//import com.ecom.service.CognitoService;
//import com.ecom.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.*;
//import com.ecom.payload.UserDto;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//   private CognitoService cognitoService;
//    @Autowired
//    private UserService userService;
//
//
//
//    @Autowired
//    public AuthController(CognitoService cognitoService) {
//        this.cognitoService = cognitoService;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
//        try {
//            String username = userDto.getUsername();
//            String password = userDto.getPassword();
//            String email = userDto.getEmail();
//            String phoneNumber = userDto.getPhoneNumber();
//
//            cognitoService.signUpUser(username, password, email, phoneNumber);
//            userService.createUser(userDto);
//
//            return ResponseEntity.ok("User registered successfully. Please confirm your email.");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error registering user: " + e.getMessage());
//        }
//    }
//
//
//    @PostMapping("/confirm")
//    public ResponseEntity<String> confirmUser(@RequestParam String username) {
//        try {
//            cognitoService.confirmUserSignUp(username);
//            return ResponseEntity.ok("User confirmed successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error confirming user: " + e.getMessage());
//        }
//    }
//}
