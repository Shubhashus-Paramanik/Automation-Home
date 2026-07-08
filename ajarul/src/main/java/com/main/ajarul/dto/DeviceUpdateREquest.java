package com.main.ajarul.dto;

public class DeviceUpdateREquest {
 private String name;
    private Long homeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHomeId() {
        return homeId;
    }

    public void setHomeId(Long homeId) {
        this.homeId = homeId;
    }
}
