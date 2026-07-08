package com.main.ajarul.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.main.ajarul.dto.ScheduleRequest;
import com.main.ajarul.entity.Device;
import com.main.ajarul.entity.Schedule;
import com.main.ajarul.entity.User;
import com.main.ajarul.repository.DeviceRepository;
import com.main.ajarul.repository.ScheduleRepository;
import com.main.ajarul.repository.UserRepository;
import java.time.LocalTime;

@Service
public class ScheduleServiceImpl
        implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
public Schedule createSchedule(
        ScheduleRequest request) {

    Device device =
            deviceRepository
                    .findByDeviceId(
                            request.getDeviceId())
                    .orElseThrow(
                            () -> new RuntimeException(
                                    "Device not found"));

    Schedule schedule =
            new Schedule();

    schedule.setDevice(device);

    schedule.setDeviceType(
            request.getDeviceType());

    schedule.setState(
            request.isState());

    schedule.setScheduleTime(
           LocalTime.parse(
                    request.getScheduleTime()));

    schedule.setRepeatType(
            request.getRepeatType());

    schedule.setActive(true);

    return scheduleRepository.save(
            schedule);
}

   @Override
public List<Schedule> getSchedules(
        String deviceId) {

    return scheduleRepository
            .findByDeviceDeviceId(
                    deviceId);
}

    @Override
    public void deleteSchedule(
            Long id) {

        scheduleRepository.deleteById(id);
    }
    @Override
public List<Schedule> getMySchedules() {

    String email =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

    User user =
            userRepository
                    .findByEmail(email)
                    .orElseThrow();

    return scheduleRepository
            .findByDeviceHomeUser(user);
}
}