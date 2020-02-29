package com.stepanian.sandbox.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DebitCard {

    private final String cardNumber;
    private final String maskedCardNumber;
    private final LocalDate expireDate;
    private final String productName;
    private final BigDecimal availableAmount;
}
