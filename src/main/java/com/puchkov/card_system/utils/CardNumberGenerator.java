package com.puchkov.card_system.utils;

import java.util.Random;

public class CardNumberGenerator {
    private static final String BIN = "2020";
    private static final int CARD_LENGTH = 16;

    public static String generate() {
        StringBuilder cardNumber = new StringBuilder(BIN);
        Random random = new Random();

        for (int i = 0; i < CARD_LENGTH - BIN.length() - 1; i++) {
            cardNumber.append(random.nextInt(10));
        }

        String partialNumber = cardNumber.toString();
        int checkDigit = calculateLuhnCheckDigit(partialNumber);
        cardNumber.append(checkDigit);

        return cardNumber.toString();
    }

    private static int calculateLuhnCheckDigit(String number) {
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) digit = (digit % 10) + 1;
            }
            sum += digit;
            alternate = !alternate;
        }
        return (10 - (sum % 10)) % 10;
    }
}
