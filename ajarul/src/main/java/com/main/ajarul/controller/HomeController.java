package com.main.ajarul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.ajarul.dto.HomeRequest;
import com.main.ajarul.entity.Home;
import com.main.ajarul.service.HomeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/homes")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @PostMapping
    public Home createHome(@RequestBody HomeRequest request){
        return homeService.createHome(request);

    }
    @GetMapping
     public List<Home> getHomes() {

        return homeService.getHomes();
    }

    @GetMapping("/{id}")
    public Home getHome(@PathVariable Long id) {
        return homeService.getHome(id);
    }
     @PutMapping("/{id}")
    public Home updateHome(
            @PathVariable Long id,
            @RequestBody HomeRequest request){

        return homeService.updateHome(id, request);
    }
    @DeleteMapping("/{id}")
    public String deleteHome(@PathVariable Long id){

        homeService.deleteHome(id);

        return "Home Deleted Successfully";
    }
    
    
    

}
