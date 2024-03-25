package org.string.calculator.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberExtractorImpl implements NumberExtractor {
    @Override
    public int[] extractIntegersFromString(String numbers) throws IllegalArgumentException {
        String delimiter = determineDelimiter(numbers);
        String[] stringNumbers = splitStringUsingDelimiter(numbers, delimiter);
        int[] intNumbers = new int[stringNumbers.length];

        for (int i = 0; i < intNumbers.length; i++) {
            intNumbers[i] = Integer.parseInt(stringNumbers[i]);
        }

        return intNumbers;
    }

    public static String[] splitStringUsingDelimiter(String numbers, String delimiter) {
        return retrieveNumbersWithoutDelimiter(numbers)
                .split(delimiter);
    }

    public static String retrieveNumbersWithoutDelimiter(String numbers) {
        //if custom delimiter used start from first newline
        if (numbers.startsWith("//")) {
            return numbers.substring(numbers.indexOf("\n") + "\n".length());
        }

        return numbers;
    }

    public static String determineDelimiter(String numbers) {
        String pattern = "\\/\\/(\s*)(.*?)\\n";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(numbers);
        if (m.find()) {
            return Pattern.quote(m.group(2));
        }

        //assumed without specified custom delimiter pattern from requirement #3 with coma and newline is still relevant
        return "[,\n]";
    }
}
