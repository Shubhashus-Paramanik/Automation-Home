package com.main.ajarul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.main.ajarul.dto.DeviceStatusDto;
import com.main.ajarul.service.Esp32Service;

@RestController
@RequestMapping("/api/esp32")
public class ESP32Controller {

    @Autowired
    private Esp32Service esp32Service;

    // ESP32 reads current device state
    @GetMapping("/{deviceId}/state")
    public DeviceStatusDto getState(
            @PathVariable String deviceId) {

        return esp32Service.getDeviceState(deviceId);
    }

    // ESP32 tells server it is alive
    @PostMapping("/{deviceId}/heartbeat")
    public ResponseEntity<Void> heartbeat(
            @PathVariable String deviceId) {

        esp32Service.heartbeat(deviceId);

        return ResponseEntity.ok().build();
    }
}