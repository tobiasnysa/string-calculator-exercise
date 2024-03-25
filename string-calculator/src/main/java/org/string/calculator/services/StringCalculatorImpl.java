package org.string.calculator.services;

public class StringCalculatorImpl implements StringCalculator {

    NumberExtractor numberExtractor;
    StringCalculatorArgsValidator stringCalculatorArgsValidator;

    public StringCalculatorImpl(
            NumberExtractor numberExtractor,
            StringCalculatorArgsValidator stringCalculatorArgsValidator
    ) {
        this.numberExtractor = numberExtractor;
        this.stringCalculatorArgsValidator = stringCalculatorArgsValidator;
    }

    @Override
    public int add(String numbers) {

        if (numbers.isEmpty() || !stringCalculatorArgsValidator.isValid(numbers)) {
            return 0;
        }

        int[] numArray = numberExtractor.extractIntegersFromString(numbers);

        int sum = 0;

        for (int i = 0; i < numArray.length; i++) {
            sum += numArray[i];
        }

        return sum;
    }
}
