package com.main.ajarul.entity;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "users")
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;

@Column(unique =true)
private String email;
private String passwordHash;
private LocalDateTime  createdAt=LocalDateTime.now();

@OneToMany(mappedBy = "user")
private List<Home> homes;

//@OneToMany
//@JoinColumn(name="home_id")
//private Home home;
// @ManyToOne
// @JoinColumn(name="home_id")
// private Home home;


public Long getId() {
    return id;
}
public void setId(Long id) {
    this.id = id;
}
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public String getPasswordHash() {
    return passwordHash;
}
public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
}
public LocalDateTime  getCreatedAt() {
    return createdAt;
}
public void setCreatedAt(LocalDateTime  createdAt) {
    this.createdAt = createdAt;
}


}