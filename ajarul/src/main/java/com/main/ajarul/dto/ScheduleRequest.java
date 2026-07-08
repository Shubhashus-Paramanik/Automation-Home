package com.main.ajarul.dto;

public class ScheduleRequest {

    private String deviceId;

    private String deviceType;

    private boolean state;

    private String scheduleTime;

    private String repeatType;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId=deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType=deviceType;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state=state;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime=scheduleTime;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType=repeatType;
    }
}