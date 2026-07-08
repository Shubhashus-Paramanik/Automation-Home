package com.main.ajarul.dto;

public class DeviceResponse {
private Long id;
private String deviceId;
private String name;
private boolean online;
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
public boolean isOnline() {
    return online;
}
public void setOnline(boolean online) {
    this.online = online;
}

}
