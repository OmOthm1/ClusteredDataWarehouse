package com.progresssoft.clustereddatawarehouse.controller;

import com.progresssoft.clustereddatawarehouse.exception.DuplicateDealException;
import com.progresssoft.clustereddatawarehouse.model.Deal;
import com.progresssoft.clustereddatawarehouse.service.DealService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DealController.class)
public class DealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealService dealService;

    @Test
    public void testCreateDeal_Success() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date parsedDate = dateFormat.parse("2023-05-30T00:00:00.000+00:00");
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

        Deal deal = new Deal();
        deal.setDealUniqueId("123");
        deal.setFromCurrencyIso("USD");
        deal.setToCurrencyIso("JOD");
        deal.setDealTimestamp(timestamp);
        deal.setDealAmount(new BigDecimal("1000.00"));


        when(dealService.saveDeal(any(Deal.class))).thenReturn(deal);

        String dealJson = """
                {
                    "dealUniqueId": "123",
                    "fromCurrencyIso": "USD",
                    "toCurrencyIso": "JOD",
                    "dealTimestamp": "2023-05-30T00:00:00.000+00:00",
                    "dealAmount": 1000.00
                }
                """;

        mockMvc.perform(post("/deals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dealJson))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "dealUniqueId": "123",
                            "fromCurrencyIso": "USD",
                            "toCurrencyIso": "JOD",
                            "dealTimestamp": "2023-05-30T00:00:00.000+00:00",
                            "dealAmount": 1000.00
                        }
                        """));

        Mockito.verify(dealService, Mockito.times(1)).saveDeal(any(Deal.class));
    }

    @Test
    public void testCreateDeal_MissingId() throws Exception {
        String invalidDealJson = """
                {
                    "dealUniqueId": "",
                    "fromCurrencyIso": "USD",
                    "toCurrencyIso": "JOD",
                    "dealTimestamp": "2023-05-30T00:00:00.000+00:00",
                    "dealAmount": 1000.00
                }
                """;

        mockMvc.perform(post("/deals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidDealJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                            "dealUniqueId": "Deal Unique ID is mandatory"
                        }
                        """));
    }

    @Test
    public void testCreateDeal_InvalidCurrencyISO() throws Exception {
        String invalidCurrencyISOJson = """
                {
                    "dealUniqueId": "123",
                    "fromCurrencyIso": "US",
                    "toCurrencyIso": "DINAR",
                    "dealTimestamp": "2023-05-30T00:00:00.000+00:00",
                    "dealAmount": 1000.00
                }
                """;

        mockMvc.perform(post("/deals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCurrencyISOJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                            "fromCurrencyIso": "From Currency ISO Code must be 3 characters long",
                            "toCurrencyIso": "To Currency ISO Code must be 3 characters long"
                        }
                        """));
    }

    @Test
    public void testCreateDeal_InvalidDealAmount() throws Exception {
        String invalidDealAmountJson = """
                {
                    "dealUniqueId": "123",
                    "fromCurrencyIso": "USD",
                    "toCurrencyIso": "JOD",
                    "dealTimestamp": "2023-05-30T00:00:00.000+00:00",
                    "dealAmount": -1000.00
                }
                """;

        mockMvc.perform(post("/deals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidDealAmountJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                            "dealAmount": "Deal Amount must be greater than zero"
                        }
                        """));
    }


    @Test
    public void testCreateDeal_DuplicateDealException() throws Exception {
        Deal deal = new Deal();
        deal.setDealUniqueId("123");
        deal.setFromCurrencyIso("USD");
        deal.setToCurrencyIso("JOD");
        deal.setDealTimestamp(new Timestamp(System.currentTimeMillis()));
        deal.setDealAmount(new BigDecimal("1000.00"));

        when(dealService.saveDeal(any(Deal.class))).thenThrow(new DuplicateDealException("Deal with this ID already exists"));

        String dealJson = """
                {
                    "dealUniqueId": "123",
                    "fromCurrencyIso": "USD",
                    "toCurrencyIso": "JOD",
                    "dealTimestamp": "2023-05-30T00:00:00.000+00:00",
                    "dealAmount": 1000.00
                }
                """;

        mockMvc.perform(post("/deals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dealJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Deal with this ID already exists"));
    }
}
