package org.string.calculator.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculatorArgsValidatorImpl implements StringCalculatorArgsValidator {

    String errorMsg = "";

    public boolean isValid(String numbers) {
        String delimiter = NumberExtractorImpl.determineDelimiter(numbers);
        numbers = NumberExtractorImpl.retrieveNumbersWithoutDelimiter(numbers);

        if (
                isSeparatorAtTheEnd(numbers, delimiter)
                        || foundNegativeNumber(numbers, delimiter)
                        || foundInvalidDelimiter(numbers, delimiter)

        ) {
            throw new IllegalArgumentException(errorMsg);
        }

        return true;
    }

    private boolean isSeparatorAtTheEnd(String numbers, String delimiter) {
        if (Pattern.compile(".*" + delimiter + "$").matcher(numbers).find()) {
            errorMsg += "Separator cannot be at the end of string";
            return true;
        }

        return false;
    }

    private boolean foundInvalidDelimiter(String numbers, String delimiter) {


        //regexp allow to find out if numbers string consists of digits and delimiter
        String regex = "^(\\d+|" + delimiter + ")+$";

        if (numbers.matches(regex)) {
            return false;
        }

        //find the portion of numbers string that did not match previous regexp
        regex = "(?:(?!" + delimiter + "|\\d).)+";
        Matcher matcher = Pattern.compile(regex).matcher(numbers);

        if (matcher.find()) {
            errorMsg += delimiter.replace("\\Q", "").replace("\\E", "")
                    + " expected but " + matcher.group()
                    + " found at position "
                    + matcher.start();
        }

        return true;
    }

    private boolean foundNegativeNumber(String numbers, String delimiter) {
        Pattern pattern = Pattern.compile("-\\d+");
        Matcher matcher;
        String negativeNums = "";
        String[] numberArray = NumberExtractorImpl.splitStringUsingDelimiter(numbers, delimiter);

        for (String numAsStr : numberArray) {
            matcher = pattern.matcher(numAsStr);
            if (matcher.find()) {
                negativeNums += negativeNums.isEmpty() ? matcher.group() : ", " + matcher.group();
            }
        }

        if (!negativeNums.isEmpty()) {
            errorMsg += "Negative number(s) not allowed: " + negativeNums;
            return true;
        }

        return false;
    }
}
