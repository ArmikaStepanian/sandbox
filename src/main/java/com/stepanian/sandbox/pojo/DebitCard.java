package com.stepanian.sandbox.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitCard {

    private String cardNumber;
    private String maskedCardNumber;
    private LocalDate expireDate;
    private String productName;
    private BigDecimal availableAmount;
}
