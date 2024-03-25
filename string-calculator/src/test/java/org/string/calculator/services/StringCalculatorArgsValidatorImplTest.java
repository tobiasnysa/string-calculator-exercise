package org.string.calculator.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorArgsValidatorImplTest {
    StringCalculatorArgsValidator stringCalculatorArgsValidator;

    @BeforeEach
    public void setUp() {
        stringCalculatorArgsValidator = new StringCalculatorArgsValidatorImpl();
    }

    @Test
    public void testIsValid_StringEndsWithSeparator_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> stringCalculatorArgsValidator.isValid("1,2,3,"));
        assertThrows(IllegalArgumentException.class, () -> stringCalculatorArgsValidator.isValid("1\n2,3\n"));
        assertThrows(IllegalArgumentException.class, () -> stringCalculatorArgsValidator.isValid("//|\n1|2|3|"));
        assertThrows(IllegalArgumentException.class, () -> stringCalculatorArgsValidator.isValid("//aa\n1aa2aa3aa"));
    }

    @Test
    public void testIsValid_ProperStringWithoutDelimiter_ReturnsTrue() {
        assertTrue(stringCalculatorArgsValidator.isValid("1\n2,3"));
        assertTrue(stringCalculatorArgsValidator.isValid("1,2,3"));
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
        assertThrows(IllegalArgumentException.class, () ->  stringCalculatorArgsValidator.isValid("//|\n1|2,3"));
        assertThrows(IllegalArgumentException.class, () ->  stringCalculatorArgsValidator.isValid("//aa|\n1aa|2aa|a3"));
        assertThrows(IllegalArgumentException.class, () ->  stringCalculatorArgsValidator.isValid("//aa|\n1aa|2d|3"));
        assertThrows(IllegalArgumentException.class, () ->  stringCalculatorArgsValidator.isValid("//aba|\n1aba|2ab|3"));
    }

    @Test
    public void testIsValid_WithNegativeNumber_ReturnTrue() {
        assertThrows(IllegalArgumentException.class, () ->  stringCalculatorArgsValidator.isValid("1,-2,-3"));
        assertThrows(IllegalArgumentException.class, () ->  stringCalculatorArgsValidator.isValid("1\n2,-3"));
    }
}