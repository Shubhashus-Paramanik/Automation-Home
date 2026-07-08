package com.main.ajarul.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ajarul.entity.Home;
import com.main.ajarul.entity.User;

public interface HomeRepository extends JpaRepository<Home, Long> {
    List<Home> findByUser(User user);
    

}
