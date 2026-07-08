package com.main.ajarul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.main.ajarul.dto.ScheduleRequest;
import com.main.ajarul.entity.Schedule;
import com.main.ajarul.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public Schedule create(
            @RequestBody
            ScheduleRequest request){

        return scheduleService
                .createSchedule(
                        request);
    }

    @GetMapping("/{deviceId}")
    public List<Schedule> get(
            @PathVariable
            String deviceId){

        return scheduleService
                .getSchedules(
                        deviceId);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable
            Long id){

        scheduleService
                .deleteSchedule(id);
    }
    @GetMapping
    public List<Schedule> getMySchedules() {
    
        return scheduleService
                .getMySchedules();
    }
}