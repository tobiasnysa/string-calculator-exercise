package org.string.calculator.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberExtractorImplTest {
    private NumberExtractor numberExtractor;

    @BeforeEach
    public void setUp() {
        numberExtractor = new NumberExtractorImpl();
    }

    @Test
    public void testExtractIntegersFromString_WithProperNumberString_ReturnsIntArray() {
        int[] extractionResult = numberExtractor.extractIntegersFromString("1,2,3");
        assertEquals(1, extractionResult[0]);
        assertEquals(2, extractionResult[1]);
        assertEquals(3, extractionResult[2]);
    }


    @Test
    public void testExtractIntegersFromString_WithIncorectNumberString_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> numberExtractor.extractIntegersFromString("a,2,3"));
        assertThrows(IllegalArgumentException.class, () -> numberExtractor.extractIntegersFromString("a,2\nv,"));
    }
}