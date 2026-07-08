package com.main.ajarul.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.main.ajarul.dto.DashboardStatsDto;
import com.main.ajarul.dto.DeviceInfoDto;
import com.main.ajarul.entity.Device;
import com.main.ajarul.entity.User;
import com.main.ajarul.exception.ResourceNotFoundException;
import com.main.ajarul.repository.DeviceRepository;
import com.main.ajarul.repository.HomeRepository;
import com.main.ajarul.repository.UserRepository;

@Service
public class DashboardService {

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private DeviceRepository deviceRepository;

  @Autowired
    private UserRepository userRepository;


    private User getCurrentUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));
    }

    public DashboardStatsDto getStats() {

          User user = getCurrentUser();

        List<Device> devices =
                deviceRepository.findByHomeUser(user);

        DashboardStatsDto dto =
                new DashboardStatsDto();

        dto.setTotalHomes(
                homeRepository.findByUser(user).size());

        dto.setTotalDevices(devices.size());

        long online =
                devices.stream()
                        .filter(d ->
                                d.getLastSeen() != null &&
                                d.getLastSeen()
                                 .isAfter(
                                    java.time.LocalDateTime.now()
                                    .minusSeconds(30)
                                 ))
                        .count();

        dto.setOnlineDevices(online);
        dto.setOfflineDevices(
                devices.size() - online);

        List<DeviceInfoDto> recentDevices =
            devices.stream()
                    .limit(5)
                    .map(device -> {

                        DeviceInfoDto info =
                                new DeviceInfoDto();

                        info.setDeviceId(
                                device.getDeviceId());

                        info.setName(
                                device.getName());

                        info.setOnline(
                                device.getLastSeen() != null &&
                                device.getLastSeen()
                                        .isAfter(
                                                LocalDateTime.now()
                                                        .minusSeconds(30)));

                        info.setLightState(device.isLightState());
                        info.setFanState(device.isFanState());
                        info.setTvState(device.isTvState());
                        info.setPlugState(device.isPlugState());

                        return info;
                    })
                    .toList();

    dto.setRecentDevices(recentDevices);

        return dto;
    }


}

