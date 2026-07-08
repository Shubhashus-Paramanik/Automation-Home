package com.main.ajarul.dto;

public class DeviceInfoDto {
private String deviceId;
private String name;
private boolean online;
private boolean lightState;
private boolean fanState;
private boolean tvState;
private boolean plugState;
private Long homeId;

public Long getHomeId() {
    return homeId;
}
public void setHomeId(Long homeId) {
    this.homeId = homeId;
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
