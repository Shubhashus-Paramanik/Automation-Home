package com.main.ajarul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.ajarul.dto.AdminStatsDto;
import com.main.ajarul.repository.DeviceRepository;
import com.main.ajarul.repository.HomeRepository;
import com.main.ajarul.repository.ScheduleRepository;
import com.main.ajarul.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private HomeRepository homeRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;


public AdminStatsDto getStats() {

        AdminStatsDto dto =new AdminStatsDto();

        dto.setTotalUsers(
                userRepository.count());

        dto.setTotalHomes(
                homeRepository.count());

        dto.setTotalDevices(
                deviceRepository.count());

        dto.setTotalSchedules(
                scheduleRepository.count());



        return dto;
    }
}
