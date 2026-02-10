package com.example.Consistify.Controller;

import com.example.Consistify.DTO.DashboardDTO;
import com.example.Consistify.Service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// The API endpoint for the dashboard should be "https://localhost:8080/api/v1/dashboard" to get the user's dashboard data, which includes their practice statistics and progress towards their goals.
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    // The DashboardController is responsible for handling requests related to the user's dashboard, which includes their practice statistics and progress towards their goals.
    // It has a single endpoint to retrieve the dashboard data for the authenticated user.
    private final DashboardService dashboardService;

    // This endpoint retrieves the dashboard data for the authenticated user, which includes their practice statistics and progress towards their goals.
    @GetMapping
    public ResponseEntity<DashboardDTO> getMyDashboard() {
        return ResponseEntity.ok(dashboardService.getMyDashboard());
    }
}