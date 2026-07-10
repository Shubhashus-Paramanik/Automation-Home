package com.main.ajarul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.main.ajarul.dto.DeviceStatusDto;
import com.main.ajarul.service.Esp32Service;

@RestController
@RequestMapping("/api/esp32")
public class Esp32Controller {

    @Autowired
    private Esp32Service esp32Service;

    @GetMapping("/{deviceId}/state")
    public DeviceStatusDto getState(
            @PathVariable String deviceId,
            @RequestHeader("X-DEVICE-KEY") String deviceKey) {

        return esp32Service.getState(deviceId, deviceKey);
    }

    @PostMapping("/{deviceId}/heartbeat")
    public ResponseEntity<Void> heartbeat(
            @PathVariable String deviceId,
            @RequestHeader("X-DEVICE-KEY") String deviceKey) {

        esp32Service.heartbeat(deviceId, deviceKey);

        return ResponseEntity.ok().build();
    }
}