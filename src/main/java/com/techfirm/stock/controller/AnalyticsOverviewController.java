package com.techfirm.stock.controller;

import com.techfirm.stock.model.AnalyticsOverview;
import com.techfirm.stock.service.AnalyticsOverviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("api")
public class AnalyticsOverviewController {
    private final AnalyticsOverviewService analyticsService;

    public AnalyticsOverviewController(AnalyticsOverviewService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/overview")
    public ResponseEntity<AnalyticsOverview> getAnalyticsOverview(){
        AnalyticsOverview overview = analyticsService.getAnalyticsOverview();
        return ResponseEntity.ok().body(overview);
    }
}
