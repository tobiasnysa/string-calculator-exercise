package org.string.calculator;

import org.string.calculator.services.*;

public class Main {
    public static void main(String[] args) {
        NumberExtractor numberExtractor = new NumberExtractorImpl();
        StringCalculatorArgsValidator stringCalculatorArgsValidator = new StringCalculatorArgsValidatorImpl();
        StringCalculator calculator = new StringCalculatorImpl(numberExtractor, stringCalculatorArgsValidator);

        try {
            if (args.length > 0) {
                System.out.println(
                        calculator.add(args[0].replace("\\n", "\n"))
                );
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}