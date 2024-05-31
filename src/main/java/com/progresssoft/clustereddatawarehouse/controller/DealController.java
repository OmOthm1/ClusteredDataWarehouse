package com.progresssoft.clustereddatawarehouse.controller;

import com.progresssoft.clustereddatawarehouse.model.Deal;
import com.progresssoft.clustereddatawarehouse.service.DealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deals")
@RequiredArgsConstructor
@Slf4j
@Validated
public class DealController {
    private final DealService dealService;

    /**
     * Endpoint to create a new deal.
     *
     * @param deal the deal to create
     * @return the created deal
     */
    @PostMapping
    public ResponseEntity<Deal> createDeal(@Valid @RequestBody Deal deal) {
        log.info("Received request to create deal with ID: {}", deal.getDealUniqueId());
        Deal savedDeal = dealService.saveDeal(deal);
        log.info("Deal with ID: {} created successfully", deal.getDealUniqueId());
        return ResponseEntity.ok(savedDeal);
    }
}
