package org.string.calculator.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringCalculatorImplTest {
    private StringCalculator stringCalculator;
    private NumberExtractor numberExtractor;

    private StringCalculatorArgsValidator stringCalculatorArgsValidator;

    @BeforeEach
    public void setUp() {
        numberExtractor = mock(NumberExtractorImpl.class);
        stringCalculatorArgsValidator = mock(StringCalculatorArgsValidator.class);
        stringCalculator = new StringCalculatorImpl(numberExtractor, stringCalculatorArgsValidator);
    }

    @Test
    public void testAdd_EmptyString_ReturnZero() {
        assertEquals(0, stringCalculator.add(""));
    }

    @Test
    public void testAdd_SingleNumber_ReturnsNumber() {
        int[] firstExtractionResult = {1};
        when(numberExtractor.extractIntegersFromString("1")).thenReturn(firstExtractionResult);
        when(stringCalculatorArgsValidator.isValid("1")).thenReturn(true);

        int[] secondExtractionResult = {99};
        when(numberExtractor.extractIntegersFromString("99")).thenReturn(secondExtractionResult);
        when(stringCalculatorArgsValidator.isValid("99")).thenReturn(true);

        assertEquals(1, stringCalculator.add("1"));
        assertEquals(99, stringCalculator.add("99"));
    }

    @Test
    public void testAdd_TwoNumber_ReturnsSum() {
        int[] firstExtractionResult = {1, 5};
        when(numberExtractor.extractIntegersFromString("1,5")).thenReturn(firstExtractionResult);
        when(stringCalculatorArgsValidator.isValid("1,5")).thenReturn(true);

        int[] secondExtractionResult = {9, 4};
        when(numberExtractor.extractIntegersFromString("9,4")).thenReturn(secondExtractionResult);
        when(stringCalculatorArgsValidator.isValid("9,4")).thenReturn(true);

        assertEquals(6, stringCalculator.add("1,5"));
        assertEquals(13, stringCalculator.add("9,4"));
    }

    @Test
    public void testAdd_MoreThanTwoNumbers_ShouldReturnSum() {
        int[] firstExtractionResult = {1, 2, 3};
        when(numberExtractor.extractIntegersFromString("1,2,3")).thenReturn(firstExtractionResult);
        when(stringCalculatorArgsValidator.isValid("1,2,3")).thenReturn(true);

        int[] secondExtractionResult = {15, 12, 23, 1, 2, 4};
        when(numberExtractor.extractIntegersFromString("15,12,23,1,2,4")).thenReturn(secondExtractionResult);
        when(stringCalculatorArgsValidator.isValid("15,12,23,1,2,4")).thenReturn(true);

        assertEquals(6, stringCalculator.add("1,2,3"));
        assertEquals(57, stringCalculator.add("15,12,23,1,2,4"));
    }

    @Test
    public void testAdd_NoNumberArg_ShouldReturnIllegalArgumentException() {
        when(numberExtractor.extractIntegersFromString("abc")).thenThrow(IllegalArgumentException.class);
        when(stringCalculatorArgsValidator.isValid("abc")).thenReturn(true);
        when(numberExtractor.extractIntegersFromString("abc,2")).thenThrow(IllegalArgumentException.class);
        when(stringCalculatorArgsValidator.isValid("abc,2")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> stringCalculator.add("abc"));
        assertThrows(IllegalArgumentException.class, () -> stringCalculator.add("abc,2"));
    }

    @Test
    public void testAdd_NumbersWithTwoDifferentProperSeparators_ShouldReturnSum() {
        int[] firstExtractionResult = {2, 3, 1, 4};
        when(numberExtractor.extractIntegersFromString("2,3\n1,4")).thenReturn(firstExtractionResult);
        when(stringCalculatorArgsValidator.isValid("2,3\n1,4")).thenReturn(true);

        int[] secondExtractionResult = {2, 13, 1, 4};
        when(numberExtractor.extractIntegersFromString("2\n13\n1,4")).thenReturn(secondExtractionResult);
        when(stringCalculatorArgsValidator.isValid("2\n13\n1,4")).thenReturn(true);

        assertEquals(10, stringCalculator.add("2,3\n1,4"));
        assertEquals(20, stringCalculator.add("2\n13\n1,4"));
    }

    @Test
    public void testAdd_NumbersWithUnsupportedSeparator_ShouldReturnSum() {
        when(numberExtractor.extractIntegersFromString("2,3\b1,4")).thenThrow(IllegalArgumentException.class);
        when(stringCalculatorArgsValidator.isValid("2,3\b1,4")).thenReturn(true);
        when(numberExtractor.extractIntegersFromString("2,3\n1$4")).thenThrow(IllegalArgumentException.class);
        when(stringCalculatorArgsValidator.isValid("2,3\n1$4")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> stringCalculator.add("2,3\b1,4"));
        assertThrows(IllegalArgumentException.class, () -> stringCalculator.add("2,3\n1$4"));
    }


}