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
        int[] extractionResult = numberExtractor.extractIntegersFromString("1,2\n3");
        assertEquals(1, extractionResult[0]);
        assertEquals(2, extractionResult[1]);
        assertEquals(3, extractionResult[2]);
    }

    @Test
    public void testExtractIntegersFromString_IncorectNumberString_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> numberExtractor.extractIntegersFromString("a,2,3"));
        assertThrows(IllegalArgumentException.class, () -> numberExtractor.extractIntegersFromString("a,2\nv,"));
    }

    @Test
    public void testExtractIntegersFromString_SemicolonAsDelimiter_ReturnIntArray() {
        int[] extractionResult = numberExtractor.extractIntegersFromString("//;\n1;3");
        assertEquals(1, extractionResult[0]);
        assertEquals(3, extractionResult[1]);
    }

    @Test
    public void testExtractIntegersFromString_PipeAsDelimiter_ReturnIntArray() {
        int[] extractionResult = numberExtractor.extractIntegersFromString("//|\n1|2|3");
        assertEquals(1, extractionResult[0]);
        assertEquals(2, extractionResult[1]);
        assertEquals(3, extractionResult[2]);
    }

    @Test
    public void testExtractIntegersFromString_StringAsDelimiter_ReturnIntArray() {
        int[] extractionResult = numberExtractor.extractIntegersFromString("//sep\n2sep5");
        assertEquals(2, extractionResult[0]);
        assertEquals(5, extractionResult[1]);
    }

    @Test
    public void testDetermineDelimiter_StandardDelimiters_ReturnRegexp() {
        assertEquals("[,\n]", NumberExtractorImpl.determineDelimiter("1,2\n3"));
        assertEquals("[,\n]", NumberExtractorImpl.determineDelimiter("1,2,3"));
        assertEquals("[,\n]", NumberExtractorImpl.determineDelimiter("1\n2\n3"));
    }

    @Test
    public void testDetermineDelimiter_CustomDelimiters_ReturnRegexp() {
        assertEquals("\\Q;\\E", NumberExtractorImpl.determineDelimiter("//;\n1;3"));
        assertEquals("\\Q|\\E", NumberExtractorImpl.determineDelimiter("//|\n1|2|3"));
        assertEquals("\\Qsep\\E", NumberExtractorImpl.determineDelimiter("//sep\n2sep5"));
    }
}