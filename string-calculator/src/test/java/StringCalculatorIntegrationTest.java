import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.string.calculator.services.*;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringCalculatorIntegrationTest {
    private NumberExtractor numberExtractor;
    private StringCalculatorArgsValidator stringCalculatorArgsValidator;
    private StringCalculator calculator;

    @BeforeEach
    public void setUp() {
        numberExtractor = new NumberExtractorImpl();
        stringCalculatorArgsValidator = new StringCalculatorArgsValidatorImpl();
        calculator = new StringCalculatorImpl(numberExtractor, stringCalculatorArgsValidator);
    }

    @Test
    public void testAdd_EmptyString_ReturnSum() {
        assertEquals(0, calculator.add(""));
    }

    @Test
    public void testAdd_ProperComaOrNewLineSeparatedNumbers_ReturnSum() {
        assertEquals(1005, calculator.add("1000,2,3"));
        assertEquals(6, calculator.add("1,2\n3"));
    }

    @Test
    public void testAdd_ProperCustomDelimiter_ReturnSum() {
        assertEquals(4, calculator.add("//;\n1;3"));
        assertEquals(6, calculator.add("//|\n1|2|3"));
        assertEquals(7, calculator.add("//sep\n2sep5"));
    }

    @Test
    public void testAdd_InvalidDelimiter_ThrowIllegalArgumentException() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> calculator.add("//|\n1|2,3"));

        assertEquals("| expected but , found at position 3\n", ex1.getMessage());
    }

    @Test
    public void testAdd_DelimiterATTheEnd_ThrowIllegalArgumentException() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> calculator.add("//|\n1|2|3|"));
        assertEquals("Separator cannot be at the end of string\n", ex1.getMessage());
    }

    @Test
    public void testAdd_NegativeNumber_ThrowIllegalArgumentException() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> calculator.add("//|\n1|2|-3"));
        assertEquals("Negative number(s) not allowed: -3\n", ex1.getMessage());
    }

    @Test
    public void testAdd_MultipleErrors_ThrowIllegalArgumentException() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> calculator.add("//|\\n1|2,-3"));
        assertEquals("Negative number(s) not allowed: -3\n" +
                "[,\n" +
                "] expected but //|\\n found at position 0\n", ex1.getMessage());
    }

    @Test
    public void testAdd_NumberXGreaterThan1000_ShouldReturnSumWithoutX() {
        assertEquals(4, calculator.add("1,2000,3"));
        assertEquals(4, calculator.add("1,1001,3"));
        assertEquals(4, calculator.add("1,1001,3"));
    }
}
