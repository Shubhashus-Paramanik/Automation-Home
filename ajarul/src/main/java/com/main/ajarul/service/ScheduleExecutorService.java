package com.main.ajarul.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.main.ajarul.dto.DeviceControlRequest;
import com.main.ajarul.entity.Device;
import com.main.ajarul.entity.Schedule;
import com.main.ajarul.repository.ScheduleRepository;

@Service
public class ScheduleExecutorService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DeviceService deviceService;

    @Scheduled(fixedRate = 10000)//check every 10 seconds
    public void executeSchedules() {

        System.out.println(
                "CHECKING SCHEDULES");

        LocalTime now =
                LocalTime.now()
                        .withSecond(0)
                        .withNano(0);

        List<Schedule> schedules =
                scheduleRepository
                        .findByActiveTrue();
        System.out.println(
                "NOW = " + now);

        for (Schedule schedule : schedules) {

            LocalTime scheduleTime =
                    schedule.getScheduleTime()
                            .withSecond(0)
                            .withNano(0);

            // if (scheduleTime.equals(now)) {
            //     execute(schedule);
            // }

            if (scheduleTime.equals(now)
            && !LocalDate.now()
                    .equals(
                        schedule.getLastExecutedDate())) {

        execute(schedule);

        schedule.setLastExecutedDate(
                LocalDate.now());

        scheduleRepository.save(
                schedule);

        System.out.println(
                "EXECUTED ONCE: "
                        + schedule
                                .getDevice()
                                .getDeviceId());
    }
        }
    }

    private void execute(
            Schedule schedule) {

        Device device =
                schedule.getDevice();

        DeviceControlRequest request =
                new DeviceControlRequest();

        request.setDeviceId(
                device.getDeviceId());

        request.setLightState(
                device.isLightState());

        request.setFanState(
                device.isFanState());

        request.setTvState(
                device.isTvState());

        request.setPlugState(
                device.isPlugState());

        switch (
                schedule.getDeviceType()) {

            case "LIGHT":
                request.setLightState(
                        schedule.isState());
                break;

            case "FAN":
                request.setFanState(
                        schedule.isState());
                break;

            case "TV":
                request.setTvState(
                        schedule.isState());
                break;

            case "PLUG":
                request.setPlugState(
                        schedule.isState());
                break;
        }

        // deviceService
        //         .controlDevice(
        //                 request);
        deviceService.controlDeviceBySchedule(
        request);
        System.out.println(
                "EXECUTED: "
                        + device.getDeviceId());
    }
}