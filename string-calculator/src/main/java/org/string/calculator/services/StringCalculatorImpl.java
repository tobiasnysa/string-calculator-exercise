package org.string.calculator.services;

import java.util.Arrays;

public class StringCalculatorImpl implements StringCalculator {
    @Override
    public int add(String numbers) {

        if (numbers.isEmpty()) {
            return 0;
        }

        int[] numArray = retrieveIntegersFromString(numbers);

        int sum = 0;

        for (int i = 0; i < numArray.length; i++) {
            sum+=numArray[i];
        }

        return sum;
    }

    private int[] retrieveIntegersFromString(String numbers) throws IllegalArgumentException {
        String[] stringNumbers =  numbers.split(",");

        int[] intNumbers = new int[stringNumbers.length];

        for (int i = 0; i < intNumbers.length; i++) {
            intNumbers[i] = Integer.parseInt(stringNumbers[i]);
        }

        return intNumbers;
    }
}
