package com.main.ajarul.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ajarul.entity.Device;
import com.main.ajarul.entity.Schedule;
import com.main.ajarul.entity.User;

public interface ScheduleRepository
        extends JpaRepository<Schedule,Long>{

    List<Schedule>
        findByDevice(Device device);

    List<Schedule>
        findByActiveTrue();
        
    List<Schedule> findByDeviceHomeUser(
        User user);
    List<Schedule> findByDeviceDeviceId(
            String deviceId);
}