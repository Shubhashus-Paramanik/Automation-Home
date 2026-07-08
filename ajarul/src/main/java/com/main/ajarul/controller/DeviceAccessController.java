package com.main.ajarul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.ajarul.dto.DevicePermissionDto;
import com.main.ajarul.dto.DeviceShareRequest;
import com.main.ajarul.entity.Device;
import com.main.ajarul.service.DeviceService;

;

@RestController
@RequestMapping("/api/device-access")
public class DeviceAccessController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public void share(
            @RequestBody
            DeviceShareRequest request) {

        deviceService
                .shareDevice(
                        request);
    }

    @GetMapping
    public List<Device> shared() {

        return deviceService
                .getSharedDevices();
    }

    @DeleteMapping("/{id}")
    public void remove(
            @PathVariable
            Long id) {

        deviceService
                .removeAccess(id);
    }

    
}