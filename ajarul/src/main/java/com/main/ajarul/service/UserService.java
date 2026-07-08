package com.main.ajarul.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.main.ajarul.config.PasswordConfig;
import com.main.ajarul.dto.ChangePasswordRequest;
import com.main.ajarul.dto.LoginRequest;
import com.main.ajarul.dto.LoginResponse;
import com.main.ajarul.dto.RegisterRequest;
import com.main.ajarul.dto.UserProfileDto;
import com.main.ajarul.dto.UserUpdateRequest;
import com.main.ajarul.entity.User;
import com.main.ajarul.repository.UserRepository;
import com.main.ajarul.security.JwtService;

@Service
public class UserService {
private final PasswordEncoder passwordEncoder;

@Autowired
  private UserRepository userRepository;

@Autowired
  private PasswordConfig passwordConfig;
  

  @Autowired 
private JwtService jwtService;



private User getCurrentUser() {

    String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    return userRepository
            .findByEmail(email)
            .orElseThrow();
}

UserService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
}

public String registerUser(RegisterRequest registerRequest){
  

    // Check if user with the same email already exists
    if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
        return "User with this email already exists.";
    }

    // Create a new User entity and save it to the database
    User user = new User();
    user.setName(registerRequest.getName());
    user.setEmail(registerRequest.getEmail());
    user.setPasswordHash(
       passwordConfig.passwordEncoder().encode(registerRequest.getPassword())
    ); // In a real application, make sure to hash the password before saving
    userRepository.save(user);

    return "User registered successfully.";
}
public LoginResponse loginUser(LoginRequest loginRequest){
        if(loginRequest.getEmail()==null || loginRequest.getPassword()==null){
        return new LoginResponse("Email and Password are required");
    }
    Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
    if (optionalUser.isEmpty()) {
        throw new ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "Invalid email or password"
        );
    }

    User user = optionalUser.get();

    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
        throw new ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "Invalid email or password"
        );
    }

    String token = jwtService.generateToken(user.getEmail());
    return new LoginResponse(token);


}

public UserProfileDto getCurrentUserProfile(){
    String email=SecurityContextHolder.getContext().getAuthentication().getName();

    User user=userRepository.findByEmail(email).orElseThrow();

    UserProfileDto dto=new UserProfileDto();

    dto.setId(user.getId());
    dto.setName(user.getName());
    dto.setEmail(user.getEmail());

    return dto;
}

public UserProfileDto getProfile(){
 User user = getCurrentUser();

    UserProfileDto dto = new UserProfileDto();

    dto.setId(user.getId());
    dto.setName(user.getName());
    dto.setEmail(user.getEmail());

    return dto;
}

public UserProfileDto updateProfile(UserUpdateRequest request){
     User user = getCurrentUser();

    user.setName(request.getName());

    userRepository.save(user);
   
    UserProfileDto dto = new UserProfileDto();

    dto.setId(user.getId());
    dto.setName(user.getName());
    dto.setEmail(user.getEmail());

    return dto;
}

public void changePassword(ChangePasswordRequest request){
    User user = getCurrentUser();

    if (!passwordEncoder.matches(
            request.getCurrentPassword(),
            user.getPasswordHash())) {

        throw new RuntimeException("Current password is incorrect");
    }

    user.setPasswordHash(
            passwordEncoder.encode(
                    request.getNewPassword()));

    userRepository.save(user);

}
}
