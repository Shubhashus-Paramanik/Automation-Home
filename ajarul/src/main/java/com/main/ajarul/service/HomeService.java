package com.main.ajarul.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.main.ajarul.dto.HomeRequest;
import com.main.ajarul.entity.Home;
import com.main.ajarul.entity.User;
import com.main.ajarul.repository.HomeRepository;
import com.main.ajarul.repository.UserRepository;
import com.main.ajarul.exception.ResourceNotFoundException;
import com.main.ajarul.exception.UnauthorizedDeviceException;

@Service
public class HomeService {

    @Autowired
    private HomeRepository homeRepository;
    @Autowired
    private UserRepository userRepository;

    
    private User getCurrentUser(){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() ->
            new ResourceNotFoundException("User not found"));
    }

    public Home createHome(HomeRequest request){
        // String email=SecurityContextHolder.getContext().getAuthentication().getName();
        // User  user=userRepository.findByEmail(email).orElseThrow();
        User user=getCurrentUser();
        Home home=new Home();
        home.setName(request.getName());
        home.setUser(user);

Home savedHome = homeRepository.save(home);



return savedHome;    
}

public List<Home> getHomes(){
       User user=getCurrentUser();
       return homeRepository.findByUser(user);
}

public Home getHome(Long id){
    User user = getCurrentUser();
    Home home = homeRepository
            .findById(id)
            .orElseThrow(() ->
            new ResourceNotFoundException("Home not found"));
    if(!home.getUser().getId().equals(user.getId())){
        throw new UnauthorizedDeviceException("Unauthorized Home");
    }
    return home;
}
public Home updateHome(Long id, HomeRequest request){
    Home home = getHome(id);
    home.setName(request.getName());
    return homeRepository.save(home);
}

public void deleteHome(Long id){
    Home home = getHome(id);
    homeRepository.delete(home);
}
}
