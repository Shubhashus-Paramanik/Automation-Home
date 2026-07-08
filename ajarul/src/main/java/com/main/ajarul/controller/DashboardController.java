package com.main.ajarul.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.ajarul.dto.DashboardStatsDto;
import com.main.ajarul.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
@Autowired
    private DashboardService dashboardService;

    @GetMapping
    public DashboardStatsDto getStats() {
        return dashboardService.getStats();
    }
}
