package org.string.calculator.services;

public class StringCalculatorImpl implements StringCalculator {

    NumberExtractor numberExtractor;

    public StringCalculatorImpl(NumberExtractor numberExtractor) {
        this.numberExtractor = numberExtractor;
    }

    @Override
    public int add(String numbers) {

        if (numbers.isEmpty()) {
            return 0;
        }

        int[] numArray = numberExtractor.extractIntegersFromString(numbers);

        int sum = 0;

        for (int i = 0; i < numArray.length; i++) {
            sum+=numArray[i];
        }

        return sum;
    }
}
