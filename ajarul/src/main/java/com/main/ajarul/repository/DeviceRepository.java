package com.main.ajarul.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ajarul.entity.Device;
import com.main.ajarul.entity.User;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    // List<Device> findByUser(User user);
    Optional<Device>findByDeviceId(String deviceId);
    List<Device> findByHomeUser(User user);

}
