package com.main.ajarul.dto;

import java.util.List;

public class DashboardStatsDto {


    private long totalHomes;
    private long totalDevices;
    private long onlineDevices;
    private long offlineDevices;
    private List<DeviceInfoDto> recentDevices;

   
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

    public long getOnlineDevices() {
        return onlineDevices;
    }

    public void setOnlineDevices(long onlineDevices) {
        this.onlineDevices = onlineDevices;
    }

    public long getOfflineDevices() {
        return offlineDevices;
    }

    public void setOfflineDevices(long offlineDevices) {
        this.offlineDevices = offlineDevices;
    }
    public List<DeviceInfoDto> getRecentDevices() {
        return recentDevices;
    }

    public void setRecentDevices(List<DeviceInfoDto> recentDevices) {
        this.recentDevices = recentDevices;
    }

}
