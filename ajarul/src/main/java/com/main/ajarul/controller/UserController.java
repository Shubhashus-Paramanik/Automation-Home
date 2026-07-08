package com.main.ajarul.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.ajarul.dto.ChangePasswordRequest;
import com.main.ajarul.dto.LoginRequest;
import com.main.ajarul.dto.LoginResponse;
import com.main.ajarul.dto.RegisterRequest;
import com.main.ajarul.dto.UserProfileDto;
import com.main.ajarul.dto.UserUpdateRequest;
import com.main.ajarul.security.JwtService;
import com.main.ajarul.service.UserService;


@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }
    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }
    

   @GetMapping("/")
    public String home() {
        return "Home Automation Backend Running";
    }
    
    //create temporary end point

    @GetMapping("/test-token")
    public String testToken(){
        String token=jwtService.generateToken("dhriti@gmail.com");
        return token;
    }
@GetMapping("/me")
public UserProfileDto me() {
    return userService.getCurrentUserProfile();
}

@GetMapping("/profile")
public UserProfileDto getProfile(){
    return userService.getProfile();
}

@PutMapping("/profile")
public UserProfileDto updateProfile(@RequestBody UserUpdateRequest request){
    return userService.updateProfile(request);
}

@PutMapping("/change-password")
public String changePassword(@RequestBody ChangePasswordRequest request){
userService.changePassword(request);

    return "Password changed successfully";
}

    
}
