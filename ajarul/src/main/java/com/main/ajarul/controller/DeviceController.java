package com.main.ajarul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.ajarul.dto.DeviceRequest;
import com.main.ajarul.dto.DeviceStatusDto;
import com.main.ajarul.dto.DeviceUpdateREquest;
import com.main.ajarul.entity.Device;
import com.main.ajarul.service.DeviceService;
import com.main.ajarul.dto.DeviceControlRequest;
import com.main.ajarul.dto.DeviceInfoDto;
import com.main.ajarul.dto.DevicePermissionDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public Device createDevice(@RequestBody DeviceRequest request) {
        return deviceService.createDevice(request);
    }

    @GetMapping
    public List<Device> getMyDevices() {
       return deviceService.getMyDevices();
    }

    @PostMapping("/control")
    public Device controlDevice(@RequestBody DeviceControlRequest request) {
        return deviceService.controlDevice(request);
    }

    // @GetMapping("/state/{deviceId}")
    // public Device getDeviceState(@PathVariable String deviceId) {
    //    return deviceService.getDeviceState(deviceId);
    // }
     @GetMapping("/{deviceId}/state")
    public DeviceStatusDto getState(@PathVariable String deviceId) {

        return deviceService.getDeviceState(deviceId);
    }
    

    @PostMapping("/{deviceId}/heartbeat")
    public ResponseEntity<Void> heartbeat(@PathVariable String deviceId) {
       deviceService.heartbeat(deviceId);

    return ResponseEntity.ok().build();
    }

// @GetMapping("/state/{deviceId}")
// public DeviceStatusDto getDeviceState(
//         @PathVariable String deviceId) {
//     return deviceService.getDeviceState(deviceId);
// }

///
@GetMapping("/{deviceId}/info")
public DeviceInfoDto getInfo(@PathVariable String deviceId) {
    return deviceService.getDeviceInfo(deviceId);
}

@DeleteMapping("/{deviceId}")
public ResponseEntity<Void> deleteDevice(@PathVariable String deviceId){
    deviceService.deleteDevice(deviceId);
    return ResponseEntity.noContent().build();
}
@PutMapping("/{deviceId}")
public Device updateDevice(
        @PathVariable String deviceId,
        @RequestBody DeviceUpdateREquest request) {

    return deviceService.updateDevice(
            deviceId,
            request);
}

@GetMapping("/{deviceId}/permissions")
public DevicePermissionDto permissions(
        @PathVariable String deviceId){

    return deviceService
            .getPermissions(deviceId);
}
}
