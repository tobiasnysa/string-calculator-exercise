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
    public void testValidate_StringEndsWithSeparator_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> stringCalculatorArgsValidator.isValid("1,2,3,"));
        assertThrows(IllegalArgumentException.class, () -> stringCalculatorArgsValidator.isValid("1\n2,3\n"));
    }

    @Test
    public void testValidate_StringDoesNotEndsWithSeparator_ReturnsTrue() {
        assertTrue(stringCalculatorArgsValidator.isValid("1,2,3"));
    }
}