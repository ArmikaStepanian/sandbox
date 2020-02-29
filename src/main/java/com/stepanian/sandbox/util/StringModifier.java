package com.stepanian.sandbox.util;

public class StringModifier {

    public static String maskCardNumber(String cardNumber) {
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