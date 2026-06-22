package com.aurea.controller;

import com.aurea.dto.DashboardResponse;
import com.aurea.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// =============================================
// DASHBOARD CONTROLLER
// =============================================
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
class DashboardController {

    private final DashboardService dashboardService;

    // GET /api/dashboard → statistiche generali
    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboard());
    }
}
