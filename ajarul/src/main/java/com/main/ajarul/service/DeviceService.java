package com.main.ajarul.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.ajarul.dto.DeviceControlRequest;
import com.main.ajarul.dto.DeviceInfoDto;
import com.main.ajarul.dto.DevicePermissionDto;
import com.main.ajarul.dto.DeviceRequest;
import com.main.ajarul.dto.DeviceShareRequest;
import com.main.ajarul.dto.DeviceStatusDto;
import com.main.ajarul.dto.DeviceUpdateREquest;
import com.main.ajarul.entity.Device;
import com.main.ajarul.entity.DeviceAccess;
import com.main.ajarul.entity.Home;
import com.main.ajarul.entity.User;
import com.main.ajarul.exception.DeviceAlreadyExistsException;
import com.main.ajarul.exception.ResourceNotFoundException;
import com.main.ajarul.exception.UnauthorizedDeviceException;
import com.main.ajarul.repository.DeviceAccessRepository;
import com.main.ajarul.repository.DeviceRepository;
import com.main.ajarul.repository.HomeRepository;
import com.main.ajarul.repository.UserRepository;

@Service
public class DeviceService {
        @Autowired
        private DeviceRepository deviceRepository;
        @Autowired
        private HomeRepository homeRepository;
        @Autowired
        private UserRepository userRepository;
        
        @Autowired
        private DeviceAccessRepository deviceAccessRepository;
        @Autowired
        private SimpMessagingTemplate messagingTemplate;

        private User getCurrentUser() {
                String email = SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName();

                return userRepository
                                .findByEmail(email)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }

        private Device getAuthorizedDevice(String deviceId) {
        
              

    Device device =
            deviceRepository
            .findByDeviceId(deviceId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Device not found"));

    getUserRole(device);

    return device;
}

        

        public Device createDevice(DeviceRequest request) {

                if (deviceRepository.findByDeviceId(request.getDeviceId()).isPresent()) {
                        throw new DeviceAlreadyExistsException("Device already exists");
                }

                User user = getCurrentUser();
                Home home = homeRepository.findById(request.getHomeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Home not found"));

                if (!home.getUser().getId().equals(user.getId())) {
                        throw new UnauthorizedDeviceException("Unauthorized Device");
                }
                Device device = new Device();
                device.setDeviceId(request.getDeviceId());
                device.setName(
                                request.getName());
                device.setHome(home);
                device.setLightState(false);
                device.setFanState(false);
                device.setTvState(false);
                device.setPlugState(false);
                // device.setUser(user);

                Device savedDevice = deviceRepository.save(device);


                return savedDevice;
        }

        public List<Device> getMyDevices() {

                User user = getCurrentUser();
                return deviceRepository.findByHomeUser(user);

        }

        @Transactional
        public Device controlDevice(DeviceControlRequest request) {

                 System.out.println("================================");
    System.out.println("CONTROL API CALLED");
    System.out.println(request.getDeviceId());
    System.out.println("================================");

                
                Device device = getAuthorizedDevice(request.getDeviceId());
                System.out.println("BEFORE SEND");
                
              

                System.out.println("AFTER SEND");
                requirePermission(device,"OWNER","EDITOR");

                

                device.setLightState(request.isLightState());
                device.setFanState(request.isFanState());
                device.setTvState(request.isTvState());
                device.setPlugState(request.isPlugState());

                Device savedDevice = deviceRepository.save(device);
            

              
        
                Map<String,Object> msg = new HashMap<>();

                // msg.put("deviceId", device.getDeviceId());
                // msg.put("time", System.currentTimeMillis());
                // msg.put("status", "UPDATED");
                // Map<String,Object> msg = new HashMap<>();

msg.put("deviceId", savedDevice.getDeviceId());

msg.put("lightState",
        savedDevice.isLightState());

msg.put("fanState",
        savedDevice.isFanState());

msg.put("tvState",
        savedDevice.isTvState());

msg.put("plugState",
        savedDevice.isPlugState());

msg.put("online",
        savedDevice.isOnline());

msg.put("time",
        System.currentTimeMillis());

System.out.println(
        "SENDING: " + msg);

messagingTemplate.convertAndSend(
        "/topic/device/"
                + savedDevice.getDeviceId(),
        msg);
                
                System.out.println(
                    "SENDING TO : /topic/device/" +
                    device.getDeviceId());
                
                // messagingTemplate.convertAndSend(
                //     "/topic/device/" + device.getDeviceId(),
                //     msg);

                // notificationService.createNotification(
                //                 getCurrentUser(),
                //                 "Device Controlled",
                //                 savedDevice.getName() + " state changed.",
                //                 "DEVICE");
                // return savedDevice;
                

        System.out.println(
                "SENDING TO : "
                + "/topic/device/"
                + device.getDeviceId());
        
        // messagingTemplate.convertAndSend(
        //         "/topic/device/" + device.getDeviceId(),
        //         msg);
        
        System.out.println(
                "MESSAGE SENT");
        System.out.println("MANUAL CONTROL");

        
        return savedDevice;
        }

@Transactional
public Device controlDeviceBySchedule(
        DeviceControlRequest request){

    Device device =
            deviceRepository
                    .findByDeviceId(
                            request.getDeviceId())
                    .orElseThrow();

    device.setLightState(
            request.isLightState());

    device.setFanState(
            request.isFanState());

    device.setTvState(
            request.isTvState());

    device.setPlugState(
            request.isPlugState());

    Device saved =
            deviceRepository
                    .save(device);

Map<String,Object> msg =
        new HashMap<>();

msg.put(
        "deviceId",
        saved.getDeviceId());

msg.put(
        "lightState",
        saved.isLightState());

msg.put(
        "fanState",
        saved.isFanState());

msg.put(
        "tvState",
        saved.isTvState());

msg.put(
        "plugState",
        saved.isPlugState());

msg.put(
        "online",
        saved.isOnline());

System.out.println(
        "SENDING TO : /topic/device/"
                + saved.getDeviceId());  
System.out.println(
        "SENDING: "+msg
);
System.out.println("SCHEDULE CONTROL");

messagingTemplate
        .convertAndSend(
                "/topic/device/"
                + saved.getDeviceId(),
                msg);

    return saved;
}


        public void heartbeat(String deviceId) {
                Device device = deviceRepository.findByDeviceId(deviceId)
                                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
                // device.setOnline(true);
                device.setLastSeen(LocalDateTime.now());

                deviceRepository.save(device);

                System.out.println(
        "SENDING TO : /topic/device/"
        + device.getDeviceId()
);

                messagingTemplate.convertAndSend(
        "/topic/device/" + device.getDeviceId(),
        device);

        }

        public DeviceStatusDto getDeviceState(String deviceId) {

                Device device = getAuthorizedDevice(deviceId);
                DeviceStatusDto dto = new DeviceStatusDto();
                dto.setLightState(device.isLightState());
                dto.setFanState(device.isFanState());
                dto.setTvState(device.isTvState());
                dto.setPlugState(device.isPlugState());

                return dto;
        }

        

        public boolean isOnline(Device device) {
                if (device.getLastSeen() == null) {
                        return false;
                }
                return device.getLastSeen().isAfter(LocalDateTime.now().minusSeconds(30));
        }

        public DeviceInfoDto getDeviceInfo(String deviceId) {

                Device device = getAuthorizedDevice(deviceId);
                DeviceInfoDto dto = new DeviceInfoDto();
                dto.setDeviceId(device.getDeviceId());
                dto.setName(device.getName());
                dto.setOnline(isOnline(device));
                dto.setHomeId(device.getHome().getId());
                return dto;
        }

        @Transactional
        public void deleteDevice(String deviceId) {
                Device device = getAuthorizedDevice(deviceId);

                requirePermission(
            device,
            "OWNER");



                // delete device
                deviceRepository.delete(device);
        }

        @Transactional
        public Device updateDevice(
                        String deviceId,
                        DeviceUpdateREquest request) {
                                
                               

                Device device = getAuthorizedDevice(deviceId);
                 requirePermission(device,"OWNER");

                Home home = homeRepository
                                .findById(request.getHomeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Home not found"));

                if (!home.getUser().getId()
                                .equals(getCurrentUser().getId())) {

                        throw new UnauthorizedDeviceException(
                                        "Unauthorized Home");
                }

                device.setName(request.getName());
                device.setHome(home);

                Device updated = deviceRepository.save(device);

                
                return updated;
        }

        @Transactional
        public void shareDevice(DeviceShareRequest request) {

                Device device = getAuthorizedDevice(
                                request.getDeviceId());

                requirePermission(
            device,
            "OWNER");

                User user = userRepository
                                .findByEmail(
                                                request.getEmail())
                                .orElseThrow(
                                                () -> new ResourceNotFoundException(
                                                                "User not found"));

                if (deviceAccessRepository
                                .existsByDeviceAndUser(
                                                device,
                                                user)) {

                        throw new RuntimeException(
                                        "Already shared");
                }

                DeviceAccess access = new DeviceAccess();

                access.setDevice(device);
                access.setUser(user);
                access.setRole(
                                request.getRole());

                deviceAccessRepository.save(
                                access);

              
        }

        public List<Device> getSharedDevices() {

                User user = getCurrentUser();

                System.out.println(
                                "Current User = "
                                                + user.getEmail());

                return deviceAccessRepository
                                .findByUser(user)
                                .stream()
                                .map(
                                                DeviceAccess::getDevice)
                                .toList();
        }

        public void removeAccess(Long accessId){

    DeviceAccess access=
            deviceAccessRepository
            .findById(accessId)
            .orElseThrow(
                ()->new ResourceNotFoundException(
                        "Access not found"));

    requirePermission(
            access.getDevice(),
            "OWNER");

    deviceAccessRepository.delete(access);
}

        private String getUserRole(Device device) {

                User currentUser = getCurrentUser();

                // owner
                if (device.getHome()
                                .getUser()
                                .getId()
                                .equals(currentUser.getId())) {

                        return "OWNER";
                }

                return deviceAccessRepository
                                .findByDeviceAndUser(
                                                device,
                                                currentUser)
                                .map(access -> access.getRole())
                                .orElseThrow(() -> new UnauthorizedDeviceException(
                                                "No access"));
        }

        private void requirePermission(
        Device device,
        String... roles){

    String userRole =
            getUserRole(device);

    for(String role : roles){

        if(role.equals(userRole)){
            return;
        }
    }

    throw new UnauthorizedDeviceException(
            "Permission denied");
}

public DevicePermissionDto getPermissions(
        String deviceId){

    Device device =
            getAuthorizedDevice(deviceId);

    String role =
            getUserRole(device);

    DevicePermissionDto dto =
            new DevicePermissionDto();

    dto.setCanControl(
            role.equals("OWNER")
            || role.equals("EDITOR"));

    dto.setCanEdit(
            role.equals("OWNER"));

    dto.setCanDelete(
            role.equals("OWNER"));

    dto.setCanShare(
            role.equals("OWNER"));

    return dto;
}


}
