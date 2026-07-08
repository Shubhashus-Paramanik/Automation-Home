package com.main.ajarul.dto;

public class DeviceStatusDto {
 private boolean lightState;
    private boolean fanState;
    private boolean tvState;
    private boolean plugState;
    
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
