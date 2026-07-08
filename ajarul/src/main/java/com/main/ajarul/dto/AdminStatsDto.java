package com.main.ajarul.dto;

public class AdminStatsDto {
private long totalUsers;
private long totalHomes;
private long totalDevices;
private long totalSchedules;
private long totalNotifications;


public long getTotalUsers() {
    return totalUsers;
}
public void setTotalUsers(long totalUsers) {
    this.totalUsers = totalUsers;
}
public long getTotalHomes() {
    return totalHomes;
}
public void setTotalHomes(long totalHomes) {
    this.totalHomes = totalHomes;
}
public long getTotalDevices() {
    return totalDevices;
}
public void setTotalDevices(long totalDevices) {
    this.totalDevices = totalDevices;
}
public long getTotalSchedules() {
    return totalSchedules;
}
public void setTotalSchedules(long totalSchedules) {
    this.totalSchedules = totalSchedules;
}
public long getTotalNotifications() {
    return totalNotifications;
}
public void setTotalNotifications(long totalNotifications) {
    this.totalNotifications = totalNotifications;
}


}
