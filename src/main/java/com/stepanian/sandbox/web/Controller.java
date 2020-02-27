package com.stepanian.sandbox.web;

import com.stepanian.sandbox.DebitCard;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
public class Controller {

    @GetMapping(value = "/api/getCard")
    public HttpEntity<DebitCard> returnCard() {
        return new ResponseEntity<>(card(), HttpStatus.OK);
    }

    private DebitCard card() {
        String cardNumber = "1563145896354789";
        return new DebitCard(cardNumber, maskCardNumber(cardNumber),
                LocalDate.of(2021, 12, 5), "Kluch", BigDecimal.valueOf(156983.89)
        );
    }

    private String maskCardNumber(String cardNumber) {
        int length = cardNumber.length();
        StringBuilder maskedNumber = new StringBuilder();

        maskedNumber.append(cardNumber, 0, 6);
        for (int i = 6; i < length - 4; i++) {
            maskedNumber.append("X");
        }
        maskedNumber.append(cardNumber, length - 4, length);

        return maskedNumber.toString();
    }

}
