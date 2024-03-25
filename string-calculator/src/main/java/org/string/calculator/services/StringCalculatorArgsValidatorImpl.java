package org.string.calculator.services;

import java.util.regex.Pattern;

public class StringCalculatorArgsValidatorImpl implements StringCalculatorArgsValidator {
    public boolean isValid(String numbers) {
        if (Pattern.compile("[,\n]$").matcher(numbers).find()) {
            throw new IllegalArgumentException("Separator cannot be at the end of string");
        }

        return true;
    }
}
