package com.main.ajarul.service;

import java.util.List;

import com.main.ajarul.dto.ScheduleRequest;
import com.main.ajarul.entity.Schedule;

public interface ScheduleService {

    Schedule createSchedule(
            ScheduleRequest request);

    List<Schedule>
        getSchedules(
            String deviceId);

    void deleteSchedule(
            Long id);
    List<Schedule> getMySchedules();
}