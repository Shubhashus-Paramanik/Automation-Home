package com.main.ajarul.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name="schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="device_id")
    private Device device;

    private String deviceType;

    private boolean state;

    private LocalTime scheduleTime;

    private String repeatType;

    private boolean active=true;
    private LocalDate lastExecutedDate;

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device=device;
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

    public LocalTime getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(LocalTime scheduleTime) {
        this.scheduleTime=scheduleTime;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType=repeatType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active=active;
    }

    public LocalDate getLastExecutedDate() {
        return lastExecutedDate;
    }

    public void setLastExecutedDate(LocalDate lastExecutedDate) {
        this.lastExecutedDate=lastExecutedDate;
    }
}