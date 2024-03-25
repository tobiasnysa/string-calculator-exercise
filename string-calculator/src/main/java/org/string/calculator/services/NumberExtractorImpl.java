package org.string.calculator.services;

public class NumberExtractorImpl implements NumberExtractor {
    @Override
    public int[] extractIntegersFromString(String numbers) throws IllegalArgumentException {
        String[] stringNumbers = numbers.split("[,\n]");

        int[] intNumbers = new int[stringNumbers.length];

        for (int i = 0; i < intNumbers.length; i++) {
            intNumbers[i] = Integer.parseInt(stringNumbers[i]);
        }

        return intNumbers;
    }
}
