package com.progresssoft.clustereddatawarehouse.service;

import com.progresssoft.clustereddatawarehouse.exception.DuplicateDealException;
import com.progresssoft.clustereddatawarehouse.model.Deal;
import com.progresssoft.clustereddatawarehouse.repository.DealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealService {
    private final DealRepository dealRepository;

    /**
     * Saves a deal to the database.
     *
     * @param deal the deal to save
     * @return the saved deal
     * @throws DuplicateDealException if a deal with the same unique ID already exists
     */
    public Deal saveDeal(Deal deal) {
        log.info("Attempting to save deal with ID: {}", deal.getDealUniqueId());

        if (dealRepository.existsByDealUniqueId(deal.getDealUniqueId())) {
            log.warn("Duplicate deal ID: {}", deal.getDealUniqueId());
            throw new DuplicateDealException("Deal with this ID already exists");
        }

        Deal savedDeal = dealRepository.save(deal);
        log.info("Deal with ID: {} saved successfully", deal.getDealUniqueId());
        return savedDeal;
    }
}