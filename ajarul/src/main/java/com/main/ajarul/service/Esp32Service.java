package com.main.ajarul.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.ajarul.dto.DeviceStatusDto;
import com.main.ajarul.entity.Device;
import com.main.ajarul.exception.ResourceNotFoundException;
import com.main.ajarul.repository.DeviceRepository;

@Service
public class Esp32Service {

    @Autowired
    private DeviceRepository deviceRepository;

    public DeviceStatusDto getDeviceState(String deviceId) {

        Device device = deviceRepository
                .findByDeviceId(deviceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Device not found"));

        DeviceStatusDto dto = new DeviceStatusDto();

        dto.setLightState(device.isLightState());
        dto.setFanState(device.isFanState());
        dto.setTvState(device.isTvState());
        dto.setPlugState(device.isPlugState());

        return dto;
    }

    public void heartbeat(String deviceId) {

        Device device = deviceRepository
                .findByDeviceId(deviceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Device not found"));

        device.setLastSeen(LocalDateTime.now());

        deviceRepository.save(device);
    }
}