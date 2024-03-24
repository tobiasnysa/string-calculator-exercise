package org.string.calculator.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.string.calculator.services.StringCalculator;
import org.string.calculator.services.StringCalculatorImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringCalculatorImplTest {

    private StringCalculator stringCalculator;

    @BeforeEach
    public void setUp() {
        stringCalculator = new StringCalculatorImpl();
    }
    @Test
    public void testAdd_EmptyString_ReturnZero() {
        assertEquals(0, stringCalculator.add(""));
    }

    @Test
    public void testAdd_SingleNumber_ReturnsNumber() {
        assertEquals(1, stringCalculator.add("1"));
        assertEquals(99, stringCalculator.add("99"));
    }

    @Test
    public void testAdd_TwoNumber_ReturnsSum() {
        assertEquals(6, stringCalculator.add("1,5"));
        assertEquals(13, stringCalculator.add("9,4"));
    }

    @Test
    public void testAdd_MoreThanTwoNumbers_ShouldReturnIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> stringCalculator.add("1,2,3"));
    }

    @Test
    public void testAdd_NoNumberArg_ShouldReturnIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> stringCalculator.add("abc"));
        assertThrows(IllegalArgumentException.class, () -> stringCalculator.add("abc,2"));
    }

}