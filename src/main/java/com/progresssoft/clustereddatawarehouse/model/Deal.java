package com.progresssoft.clustereddatawarehouse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Entity representing a deal.
 */
@Data
@Entity
@Table(name = "deals")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Deal Unique ID is mandatory")
    @Column(nullable = false, unique = true)
    private String dealUniqueId;

    @NotBlank(message = "From Currency ISO Code is mandatory")
    @Size(min = 3, max = 3, message = "From Currency ISO Code must be 3 characters long")
    @Column(nullable = false)
    private String fromCurrencyIso;

    @NotBlank(message = "To Currency ISO Code is mandatory")
    @Size(min = 3, max = 3, message = "To Currency ISO Code must be 3 characters long")
    @Column(nullable = false)
    private String toCurrencyIso;

    @NotNull(message = "Deal Timestamp is mandatory")
    @Column(nullable = false)
    private Timestamp dealTimestamp;

    @NotNull(message = "Deal Amount is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Deal Amount must be greater than zero")
    @Column(nullable = false)
    private BigDecimal dealAmount;
}
