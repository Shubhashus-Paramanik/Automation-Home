package com.main.ajarul.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "devices")
public class Device {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
private String deviceId;
private String name;
private boolean online;
private LocalDateTime  lastSeen;

@ManyToOne
@JoinColumn(name = "home_id")
private Home home;



// @ManyToOne
// @JoinColumn(name = "user_id")
// private User user;
//i think many device has one home
private boolean lightState;
private boolean fanState;
private boolean tvState;
private boolean plugState;


public Long getId() {
    return id;
}
public void setId(Long id) {
    this.id = id;
}
public String getDeviceId() {
    return deviceId;
}
public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
}
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}

public LocalDateTime  getLastSeen() {
    return lastSeen;
}
public void setLastSeen(LocalDateTime  last_seen) {
    this.lastSeen = last_seen;
}
public boolean isOnline() {
    return online;
}
public void setOnline(boolean is_online) {
    this.online = is_online;
}
// public User getUser() {
//     return user;
// }
// public void setUser(User user) {
//     this.user = user;
// }


public Home getHome() {
    return home;
}
public void setHome(Home home) {
    this.home = home;
}
public boolean isLightState() {
    return lightState;
}
public void setLightState(boolean lightState) {
    this.lightState = lightState;
}
public boolean isFanState() {
    return fanState;
}
public void setFanState(boolean fanState) {
    this.fanState = fanState;
}
public boolean isTvState() {
    return tvState;
}
public void setTvState(boolean tvState) {
    this.tvState = tvState;
}
public boolean isPlugState() {
    return plugState;
}
public void setPlugState(boolean plugState) {
    this.plugState = plugState;
}
}
