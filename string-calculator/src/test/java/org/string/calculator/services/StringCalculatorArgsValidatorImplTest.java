package org.string.calculator.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorArgsValidatorImplTest {
    StringCalculatorArgsValidator stringCalculatorArgsValidator;

    @BeforeEach
    public void setUp() {
        stringCalculatorArgsValidator = new StringCalculatorArgsValidatorImpl();
    }

    @Test
    public void testIsValid_StringEndsWithSeparator_ThrowsException() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> stringCalculatorArgsValidator.isValid("1,2,3,"));
        assertEquals("Separator cannot be at the end of string\n", ex1.getMessage());
    }

    @Test
    public void testIsValid_ProperStringWithoutCustomDelimiter_ReturnsTrue() {
        assertTrue(stringCalculatorArgsValidator.isValid("1,2\n3"));
        assertTrue(stringCalculatorArgsValidator.isValid("1\n2,3"));
        assertTrue(stringCalculatorArgsValidator.isValid("1,2,3"));
        assertTrue(stringCalculatorArgsValidator.isValid("1,2000,3"));
    }

    @Test
    public void testIsValid_ProperStringWithDelimiter_ReturnsTrue() {
        assertTrue(stringCalculatorArgsValidator.isValid("//|\n1|2|3"));
        assertTrue(stringCalculatorArgsValidator.isValid("//aa\n1aa2aa3"));
        assertTrue(stringCalculatorArgsValidator.isValid("//a\\a\n1a\\a2a\\a3"));
        assertTrue(stringCalculatorArgsValidator.isValid("//a|a\n1a|a2a|a3"));
    }

    @Test
    public void testIsValid_InvalidDelimiterUsed_ThrowException() {
        IllegalArgumentException ex1 =assertThrows(IllegalArgumentException.class, () -> stringCalculatorArgsValidator.isValid("//|\n1|2,3"));

        assertEquals("| expected but , found at position 3\n", ex1.getMessage());
    }

    @Test
    public void testIsValid_WithNegativeNumber_ReturnTrue() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> stringCalculatorArgsValidator.isValid("//|\n1|-2|-3"));
        assertEquals("Negative number(s) not allowed: -2, -3\n", ex1.getMessage());
    }

    @Test
    public void testAdd_MultipleErrors_ThrowIllegalArgumentException() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> stringCalculatorArgsValidator.isValid("//|\\n1|2,-3"));
        assertEquals("Negative number(s) not allowed: -3\n" +
                "[,\n" +
                "] expected but //|\\n found at position 0\n", ex1.getMessage());
    }

}