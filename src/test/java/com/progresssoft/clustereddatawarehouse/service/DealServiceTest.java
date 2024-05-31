package com.progresssoft.clustereddatawarehouse.service;

import com.progresssoft.clustereddatawarehouse.exception.DuplicateDealException;
import com.progresssoft.clustereddatawarehouse.model.Deal;
import com.progresssoft.clustereddatawarehouse.repository.DealRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DealServiceTest {

    @Mock
    private DealRepository dealRepository;

    @InjectMocks
    private DealService dealService;

    @Test
    public void testSaveDeal_Success() {
        Deal deal = new Deal();
        deal.setDealUniqueId("123");
        deal.setFromCurrencyIso("USD");
        deal.setToCurrencyIso("JOD");
        deal.setDealTimestamp(new Timestamp(System.currentTimeMillis()));
        deal.setDealAmount(new BigDecimal("1000.00"));

        when(dealRepository.existsByDealUniqueId("123")).thenReturn(false);
        when(dealRepository.save(deal)).thenReturn(deal);

        Deal savedDeal = dealService.saveDeal(deal);

        assertNotNull(savedDeal);
        assertEquals("123", savedDeal.getDealUniqueId());
        verify(dealRepository, times(1)).existsByDealUniqueId("123");
        verify(dealRepository, times(1)).save(deal);
    }

    @Test
    public void testSaveDeal_DuplicateDealException() {
        Deal deal = new Deal();
        deal.setDealUniqueId("123");

        when(dealRepository.existsByDealUniqueId("123")).thenReturn(true);

        assertThrows(DuplicateDealException.class, () -> dealService.saveDeal(deal));
        verify(dealRepository, times(1)).existsByDealUniqueId("123");
        verify(dealRepository, never()).save(deal);
    }
}