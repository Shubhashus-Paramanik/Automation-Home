package com.main.ajarul.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "homes")
public class Home {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;   
    
    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices;
    
    //i think one home has many devices
    //@ManyToMany
    //JoinColumn(name="device_id")
    //private Device device;
    

  

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
  public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
