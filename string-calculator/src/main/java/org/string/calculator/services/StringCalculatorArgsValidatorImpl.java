package org.string.calculator.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculatorArgsValidatorImpl implements StringCalculatorArgsValidator {

    String errorMsg = "";
    String negativeNums = "";

    public boolean isValid(String numbers) {
        String delimiter = NumberExtractorImpl.determineDelimiter(numbers);
        numbers = NumberExtractorImpl.retrieveNumbersWithoutDelimiter(numbers);
        Boolean isValid = true;


        if (isSeparatorAtTheEnd(numbers, delimiter)) {
            isValid = false;
        }

        if (foundNegativeNumber(numbers, delimiter)) {
            isValid = false;
        }

        if (foundInvalidDelimiter(numbers, delimiter)) {
            isValid = false;
        }

        if (!isValid) {
            throw new IllegalArgumentException(errorMsg);
        }

        return true;
    }

    private boolean isSeparatorAtTheEnd(String numbers, String delimiter) {
        if (Pattern.compile(".*" + delimiter + "$").matcher(numbers).find()) {
            errorMsg += "Separator cannot be at the end of string\n";
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
            if (!(matcher.group().equals("-") && !negativeNums.isEmpty())) {
                errorMsg += delimiter.replace("\\Q", "").replace("\\E", "")
                        + " expected but " + matcher.group()
                        + " found at position "
                        + matcher.start()
                        + "\n";
            }
        }

        return true;
    }

    private boolean foundNegativeNumber(String numbers, String delimiter) {
        Pattern pattern = Pattern.compile("-\\d+");
        Matcher matcher;
        String[] numberArray = NumberExtractorImpl.splitStringUsingDelimiter(numbers, delimiter);

        for (String numAsStr : numberArray) {
            matcher = pattern.matcher(numAsStr);
            if (matcher.find()) {
                negativeNums += negativeNums.isEmpty() ? matcher.group() : ", " + matcher.group();
            }
        }

        if (!negativeNums.isEmpty()) {
            errorMsg += "Negative number(s) not allowed: " + negativeNums + "\n";
            return true;
        }

        return false;
    }
}
