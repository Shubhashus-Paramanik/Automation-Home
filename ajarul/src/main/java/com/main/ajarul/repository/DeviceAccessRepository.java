package com.main.ajarul.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.ajarul.entity.Device;
import com.main.ajarul.entity.DeviceAccess;
import com.main.ajarul.entity.User;

public interface DeviceAccessRepository extends JpaRepository<DeviceAccess,Long>{

List<DeviceAccess> findByUser(User user);

    boolean existsByDeviceAndUser(
            Device device,
            User user);

Optional<DeviceAccess> findByDeviceAndUser(
            Device device,
            User user);
            
}
