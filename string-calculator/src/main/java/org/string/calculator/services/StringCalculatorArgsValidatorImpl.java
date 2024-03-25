package org.string.calculator.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculatorArgsValidatorImpl implements StringCalculatorArgsValidator {

    String errorMsg = "";

    public boolean isValid(String numbers) {
        String delimiter = NumberExtractorImpl.determineDelimiter(numbers);

        if (
                isSeparatorAtTheEnd(numbers, delimiter) || foundInvalidDelimiter(numbers, delimiter)
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

        numbers = numbers.substring(numbers.indexOf("\n") + "\n".length());

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
}
