package math.core;

import math.core.Digit;
import math.core.exceptions.IllegalNumberFormatException;
import math.core.exceptions.UndeterminedException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class DigitTest {

	private Digit zero;
	private Digit one;
	private Digit minusOne;
	private Digit pi;
	private Digit half;

	@BeforeEach public void setUp() {

		this.zero = new Digit("0");
		this.one = new Digit("1");
		this.minusOne = new Digit("-1");
		this.pi = new Digit("3.1415926535");
		this.half = new Digit("0.5");

	}

	// ==================== CONSTRUCTOR TESTS ====================

	@Test @DisplayName("Constructor with valid string parameters")
	public void testStringConstructor() {

		assertDoesNotThrow(() -> new Digit("123.456", true));
		assertDoesNotThrow(() -> new Digit("-789.123", true));
		assertDoesNotThrow(() -> new Digit("+123.456", false));
		assertDoesNotThrow(() -> new Digit("0000123.45600", false));

	}

	@Test @DisplayName("Constructor with double parameters")
	public void testDoubleConstructor() {

		assertEquals("123.456", new Digit(123.456, true).stringValue());
		assertEquals("-789.123", new Digit(-789.123, true).stringValue());
		assertEquals("0", new Digit(0.0).stringValue());

	}

	@Test @DisplayName("Constructor with long parameters")
	public void testLongConstructor() {

		Digit digit = new Digit(123L, 456L, false);
		assertEquals("123", digit.integerPart);
		assertEquals("456", digit.decimalPart);

	}

	@Test @DisplayName("Constructor with string parts")
	public void testStringPartsConstructor() {

		Digit digit = new Digit("123", "456", false);
		assertEquals("123", digit.integerPart);
		assertEquals("456", digit.decimalPart);

	}

	@Test@DisplayName("Constructor with positive exponent scientific notation")
	public void testPositiveExponentScientificNotation() {

		assertEquals("123400", new Digit("1.234E5").stringValue());
		assertEquals("1234.56", new Digit("1.23456E3").stringValue());
		assertEquals("12345600", new Digit("1.23456E7").stringValue());
		assertEquals("100", new Digit("1E2").stringValue());
		assertEquals("1200", new Digit("1.2E3").stringValue());

	}

	@Test @DisplayName("Constructor with negative exponent scientific notation")
	public void testNegativeExponentScientificNotation() {

		assertEquals("0.0001234", new Digit("1.234E-4").stringValue());
		assertEquals("0.00123456", new Digit("1.23456E-3").stringValue());
		assertEquals("0.000000123456", new Digit("1.23456E-7").stringValue());
		assertEquals("0.01", new Digit("1E-2").stringValue());
		assertEquals("0.0012", new Digit("1.2E-3").stringValue());

	}

	@Test @DisplayName("Constructor with scientific notation and no decimal part")
	public void testScientificNotationNoDecimal() {

		assertEquals("12300", new Digit("123E2").stringValue());
		assertEquals("0.0123", new Digit("123E-4").stringValue());
		assertEquals("1000000", new Digit("1E6").stringValue());
		assertEquals("0.000001", new Digit("1E-6").stringValue());

	}

	@Test @DisplayName("Constructor with scientific notation and negative numbers")
	public void testNegativeScientificNotation() {

		assertEquals("-123400", new Digit("-1.234E5").stringValue());
		assertEquals("-0.0001234", new Digit("-1.234E-4").stringValue());
		assertEquals("-1234.56", new Digit("-1.23456E3").stringValue());
		assertEquals("-0.00123456", new Digit("-1.23456E-3").stringValue());

	}

	@Test @DisplayName("Constructor with scientific notation and explicit positive sign")
	public void testPositiveSignScientificNotation() {

		assertEquals("123400", new Digit("+1.234E5").stringValue());
		assertEquals("0.0001234", new Digit("+1.234E-4").stringValue());
		assertEquals("1234.56", new Digit("+1.23456E3").stringValue());

	}

	@Test @DisplayName("Constructor with lowercase 'e' scientific notation")
	public void testLowercaseScientificNotation() {

		assertEquals("123400", new Digit("1.234e5").stringValue());
		assertEquals("0.0001234", new Digit("1.234e-4").stringValue());
		assertEquals("1234.56", new Digit("1.23456e3").stringValue());

	}

	@Test @DisplayName("Constructor with mixed case scientific notation")
	public void testMixedCaseScientificNotation() {

		assertEquals("123400", new Digit("1.234E5").stringValue());
		assertEquals("123400", new Digit("1.234e5").stringValue());
		assertEquals("123400", new Digit("1.234E5").stringValue());

	}

	@Test @DisplayName("Constructor with scientific notation and trailing zeros")
	public void testScientificNotationWithTrailingZeros() {

		assertEquals("1234.56", new Digit("1.2345600E3").stringValue());
		assertEquals("0.00123456", new Digit("1.2345600E-3").stringValue());
		assertEquals("12345600", new Digit("0001.2345600E7").stringValue());

	}

	@Test @DisplayName("Constructor with scientific notation and leading zeros")
	public void testScientificNotationWithLeadingZeros() {

		assertEquals("1234.56", new Digit("0001.23456E3").stringValue());
		assertEquals("0.00123456", new Digit("0001.23456E-3").stringValue());
		assertEquals("1234.56", new Digit("001.23456E3").stringValue());

	}

	@ParameterizedTest @CsvSource({

		"1.234E3, 1234",
		"1.234E-3, 0.001234",
		"1.23456E5, 123456",
		"1.23456E-5, 0.0000123456",
		"9.876E2, 987.6",
		"9.876E-2, 0.09876",
		"5E3, 5000",
		"5E-3, 0.005",
		"1.5E1, 15",
		"1.5E-1, 0.15"

	}) @DisplayName("Parameterized scientific notation conversion tests")
	public void testParameterizedScientificNotation(String scientific, String expected) {

		Digit digit = new Digit(scientific);
		assertEquals(expected, digit.stringValue());

	}

	@Test @DisplayName("Constructor with very large scientific notation")
	public void testVeryLargeScientificNotation() {

		assertEquals("1000000000000", new Digit("1E12").stringValue());
		assertEquals("123456789012345", new Digit("1.23456789012345E14").stringValue());

		assertEquals("0.00000000000123456789", new Digit("1.23456789E-12").stringValue());

	}

	@Test @DisplayName("Constructor with edge case scientific notation")
	public void testEdgeCaseScientificNotation() {
		// Exponente cero
		assertEquals("1.234", new Digit("1.234E0").stringValue());
		assertEquals("1.234", new Digit("1.234e0").stringValue());

		// Número cero
		assertEquals("0", new Digit("0E10").stringValue());
		assertEquals("0", new Digit("0E-10").stringValue());

		// Uno con exponente
		assertEquals("1", new Digit("1E0").stringValue());
		assertEquals("10", new Digit("1E1").stringValue());
		assertEquals("0.1", new Digit("1E-1").stringValue());

	}

	@Test @DisplayName("Constructor with scientific notation and notation parameter")
	void testScientificNotationWithNotationParameter() {
		// Con notación de punto decimal
		Digit digitDPN = new Digit("1.234E3", true);
		assertEquals("1,234", digitDPN.toString());

		// Con notación de coma decimal
		Digit digitDDN = new Digit("1.234E3", false);
		assertEquals("1.234", digitDDN.toString());

		// Números negativos con notación
		Digit negativeDPN = new Digit("-1.234E3", true);
		assertEquals("-1,234", negativeDPN.toString());

		Digit negativeDDN = new Digit("-1.234E3", false);
		assertEquals("-1.234", negativeDDN.toString());

	}

	@Test
	@DisplayName("Constructor with double using scientific notation range")
	void testDoubleConstructorWithScientificRange() {


		assertEquals("1234567.89", new Digit(1234567.89).stringValue());
		assertEquals("0.000012345", new Digit(0.000012345).stringValue());
		assertEquals("-1234567.89", new Digit(-1234567.89).stringValue());
		assertEquals("-0.000012345", new Digit(-0.000012345).stringValue());

	}

	@Test @DisplayName("Scientific notation with arithmetic operations")
	public void testScientificNotationWithArithmetic() {

		Digit sciNum1 = new Digit("1.5E3"); // 1500
		Digit sciNum2 = new Digit("2.5E2"); // 250

		// Operaciones aritméticas
		assertEquals(new Digit("1750"), sciNum1.add(sciNum2));
		assertEquals(new Digit("1250"), sciNum1.subtract(sciNum2));
		assertEquals(new Digit("375000"), sciNum1.multiply(sciNum2));
		assertEquals(new Digit("6"), sciNum1.divide(sciNum2));

	}

	@Test @DisplayName("Scientific notation comparison tests")
	public void testScientificNotationComparisons() {

		Digit sciSmall = new Digit("1.234E-3"); // 0.001234
		Digit sciLarge = new Digit("1.234E3");  // 1234
		Digit regular = new Digit("1234");
		Digit smallRegular = new Digit("0.001234");

		// Comparaciones
		assertTrue(sciSmall.compareTo(sciLarge) < 0);
		assertTrue(sciLarge.compareTo(sciSmall) > 0);
		assertEquals(0, sciLarge.compareTo(regular));
		assertEquals(0, sciSmall.compareTo(smallRegular));

		// Comparaciones con primitivos
		assertTrue(sciLarge.compareTo(1000) > 0);
		assertTrue(sciSmall.compareTo(0.001) > 0);

	}

	@ParameterizedTest@ValueSource(strings = {

		"1.234E",	  // Sin exponente
		"1.234E+",	 // Sin valor de exponente
		"1.234E-",	 // Sin valor de exponente negativo
		"E10",		 // Sin base
		"1.234EE10",   // Doble E
		"1.234E1.5",   // Exponente decimal
		"1.234Eabc",   // Exponente no numérico

	}) @DisplayName("Invalid scientific notation should throw exception")
	public void testInvalidScientificNotation(String invalidScientific) {

		assertThrows(IllegalNumberFormatException.class, () -> new Digit(invalidScientific));

	}

	@Test @DisplayName("Scientific notation normalization preserves precision")
	public void testScientificNotationPrecision() {

		// Debe preservar la precisión de los decimales
		Digit precise = new Digit("1.234567890123456789E5");
		assertEquals("123456.7890123456789", precise.stringValue());
		
		Digit verySmall = new Digit("1.234567890123456789E-5");
		assertEquals("0.00001234567890123456789", verySmall.stringValue());

	}

	@Test @DisplayName("Scientific notation with different constructor types")
	public void testScientificNotationDifferentConstructors() {

		// Constructor con partes separadas
		Digit fromParts = new Digit("1", "234E2"); // 1.234E2 = 123.4
		assertEquals("123.4", fromParts.stringValue());
		
		// Constructor con longs
		Digit fromLongs = new Digit(123L, 4L);
		Digit sciFromLongs = new Digit("1.234E2");
		assertEquals(sciFromLongs.doubleValue(), fromLongs.doubleValue(), 0.0001);

	}

	@ParameterizedTest @NullAndEmptySource @ValueSource(strings = {

		" ", "abc", "123.abc", "..123", "--123", "++123"

	}) @DisplayName("Constructor with invalid parameters should throw exception")
	public void testInvalidConstructor(String invalidInput) {

		assertThrows(IllegalNumberFormatException.class, () -> new Digit(invalidInput));

	}

	// ==================== TO STRING TESTS ====================

	@Test @DisplayName("Test toString with different notations")
	public void testToStringWithNotations() {

		Digit digitDPN = new Digit("1234.56", true);
		Digit digitDDN = new Digit("1234.56", false);

		assertEquals("1,234.56", digitDPN.toString());
		assertEquals("1.234,56", digitDDN.toString());
	}

	@Test @DisplayName("Test toString with edge cases")
	public void testToStringEdgeCases() {

		assertEquals("0", new Digit("0.000").toString());
		assertEquals("-1", new Digit("-1.000").toString());
		assertEquals("0.001", new Digit("0.001").toString());

	}

	// ==================== CONVERSION METHODS TESTS ====================

	@Test @DisplayName("Test all conversion methods")
	public void testAllConversions() {

		final Digit digit1 = new Digit("127.5");

		assertEquals(127, digit1.intValue());
		assertEquals(127L, digit1.longValue());
		assertEquals(127.5f, digit1.floatValue(), 0.0001f);
		assertEquals(127.5, digit1.doubleValue(), 0.0001);
		assertEquals(127, digit1.byteValue());
		assertEquals(127, digit1.shortValue());
		assertEquals("127.5", digit1.stringValue());

		final Digit digit2 = new Digit("-127");

		assertEquals(-127, digit2.intValue());
		assertEquals(-127L, digit2.longValue());
		assertEquals(-127, digit2.floatValue(), 0.0001f);
		assertEquals(-127, digit2.doubleValue(), 0.0001);
		assertEquals(-127, digit2.byteValue());
		assertEquals(-127, digit2.shortValue());
		assertEquals("-127", digit2.stringValue());

	}

	@Test @DisplayName("Test conversion methods with edge values")
	public void testConversionEdgeCases() {

		Digit maxByte = new Digit("127");
		Digit minByte = new Digit("-128");
		Digit large = new Digit("999999999999999.999");

		assertEquals(127, maxByte.byteValue());
		assertEquals(-128, minByte.byteValue());
		assertDoesNotThrow(() -> large.doubleValue());
		assertDoesNotThrow(() -> large.floatValue());

	}

	// ==================== COMPARISON TESTS ====================

	@Test
	@DisplayName("Test compareTo with all scenarios")
	void testCompareToComprehensive() {
		Digit two = new Digit("2");
		Digit twoPointOne = new Digit("2.1");
		Digit negativeTwo = new Digit("-2");
		
		// Same values
		assertEquals(0, two.compareTo(new Digit("2")));
		
		// Different integer parts
		assertTrue(one.compareTo(two) < 0);
		assertTrue(two.compareTo(one) > 0);
		
		// Same integer, different decimal
		assertTrue(two.compareTo(twoPointOne) < 0);
		assertTrue(twoPointOne.compareTo(two) > 0);
		
		// Negative comparisons
		assertTrue(negativeTwo.compareTo(two) < 0);
		assertTrue(two.compareTo(negativeTwo) > 0);
		
		// With primitives
		assertTrue(two.compareTo(1) > 0);
		assertTrue(two.compareTo(3) < 0);
		assertTrue(two.compareTo(2.0) == 0);
	}

	@Test
	@DisplayName("Test comparison to special values")
	void testSpecialComparisons() {
		Digit pointFive = new Digit("0.5");
		Digit negativePointFive = new Digit("-0.5");
		Digit two = new Digit("2");
		
		// Compare to zero
		assertEquals(0, zero.compareToZero());
		assertEquals(1, pointFive.compareToZero());
		assertEquals(-1, negativePointFive.compareToZero());
		
		// Compare to one
		assertEquals(0, one.compareToOne());
		assertEquals(1, two.compareToOne());
		assertEquals(-1, pointFive.compareToOne());
		
		// Compare to minus one
		assertEquals(0, minusOne.compareToMinusOne());
		assertEquals(1, zero.compareToMinusOne());
		assertEquals(-1, new Digit("-2").compareToMinusOne());
	}

	// ==================== ARITHMETIC OPERATIONS TESTS ====================

	@Test
	@DisplayName("Test addition comprehensively")
	void testAddition() {
		// Basic addition
		assertEquals(new Digit("3"), new Digit("1").add(new Digit("2")));
		assertEquals(new Digit("0"), new Digit("1").add(new Digit("-1")));
		
		// Decimal addition
		assertEquals(new Digit("2.5"), new Digit("1.25").add(new Digit("1.25")));
		
		// With zeros
		assertEquals(one, zero.add(one));
		assertEquals(one, one.add(zero));
		
		// Large numbers
		assertEquals(new Digit("2000000000"), 
					new Digit("1000000000").add(new Digit("1000000000")));
		
		// With primitives
		assertEquals(new Digit("3"), one.add(2));
		assertEquals(new Digit("3"), one.add(2.0));
	}

	@Test
	@DisplayName("Test subtraction comprehensively")
	void testSubtraction() {
		assertEquals(new Digit("1"), new Digit("2").subtract(new Digit("1")));
		assertEquals(new Digit("3"), new Digit("2").subtract(new Digit("-1")));
		assertEquals(new Digit("-1.5"), new Digit("1").subtract(new Digit("2.5")));
		
		// With primitives
		assertEquals(new Digit("1"), new Digit("2").subtract(1));
		assertEquals(new Digit("1"), new Digit("2").subtract(1.0));
	}

	@Test
	@DisplayName("Test multiplication comprehensively")
	void testMultiplication() {
		assertEquals(new Digit("6"), new Digit("2").multiply(new Digit("3")));
		assertEquals(new Digit("-6"), new Digit("2").multiply(new Digit("-3")));
		assertEquals(new Digit("0.25"), new Digit("0.5").multiply(new Digit("0.5")));
		
		// Identity and zero
		assertEquals(one, one.multiply(one));
		assertEquals(zero, zero.multiply(one));
		assertEquals(zero, one.multiply(zero));
		
		// With primitives
		assertEquals(new Digit("6"), new Digit("2").multiply(3));
		assertEquals(new Digit("6"), new Digit("2").multiply(3.0));
	}

	@Test
	@DisplayName("Test division comprehensively")
	void testDivision() {
		assertEquals(new Digit("2"), new Digit("4").divide(2));
		assertEquals(new Digit("0.5"), new Digit("1").divide(new Digit("2")));
		assertEquals(new Digit("-2"), new Digit("4").divide(new Digit("-2")));
		
		// Different precisions
		assertEquals(new Digit("0.3333333333"), 
					new Digit("1").divide(new Digit("3"), 10));
		
		// With primitives
		assertEquals(new Digit("2"), new Digit("4").divide(2));
		assertEquals(new Digit("2"), new Digit("4").divide(2.0));
	}

	@Test
	@DisplayName("Test division by zero")
	void testDivisionByZero() {
		assertThrows(UndeterminedException.class, () -> one.divide(zero));
		assertThrows(UndeterminedException.class, () -> one.divide(0));
		assertThrows(UndeterminedException.class, () -> one.divide(0.0));
	}

	// ==================== ENHANCED OPERATIONS TESTS ====================

	@Test
	@DisplayName("Test enhanced operations")
	void testEnhancedOperations() {
		// Negation
		assertEquals(minusOne, one.negate());
		assertEquals(one, minusOne.negate());
		assertEquals(zero, zero.negate());
		
		// Absolute value
		assertEquals(one, minusOne.abs());
		assertEquals(one, one.abs());
		assertEquals(zero, zero.abs());
		
		// Inverse
		assertEquals(new Digit("2"), half.inverse());
		assertEquals(new Digit("0.01"), new Digit("100").inverse());
		
		// Unity
		assertEquals(one, one.unity());
		assertEquals(one, zero.unity());
		
		// Module
		assertEquals(zero, new Digit("4").module(2));
		assertEquals(new Digit("1"), new Digit("5").module(new Digit("2")));
		assertEquals(new Digit("-1"), new Digit("-5").module(new Digit("2")));
	}

	@Test
	@DisplayName("Test increase and decrease operations")
	void testIncreaseDecrease() {
		// Increase
		assertEquals(new Digit("1.5"), half.increase());
		assertEquals(new Digit("0.5"), new Digit("-0.5").increase());
		assertEquals(new Digit("0"), minusOne.increase());
		assertEquals(new Digit("2"), one.increase());
		
		// Decrease
		assertEquals(new Digit("-0.5"), half.decrease());
		assertEquals(new Digit("-1.5"), new Digit("-0.5").decrease());
		assertEquals(new Digit("0"), one.decrease());
		assertEquals(new Digit("-2"), minusOne.decrease());
	}

	// ==================== ROUNDING TESTS ====================

	@Test
	@DisplayName("Test all rounding modes")
	void testAllRoundingModes() {
		Digit number = new Digit("3.14159");
		Digit number2 = new Digit("3.15");
		Digit number3 = new Digit("3.25");
		Digit number4 = new Digit("3.35");
		
		// CEILING
		assertEquals(new Digit("4"), number.setScale(0, RoundingMode.CEILING));
		
		// FLOOR
		assertEquals(new Digit("3"), number.setScale(0, RoundingMode.FLOOR));
		
		// DOWN
		assertEquals(new Digit("3"), number.setScale(0, RoundingMode.DOWN));
		
		// UP
		assertEquals(new Digit("3"), number.setScale(0, RoundingMode.UP));
		
		// HALF_UP
		assertEquals(new Digit("3"), number.setScale(0, RoundingMode.HALF_UP));
		
		// HALF_DOWN
		assertEquals(new Digit("3"), number.setScale(0, RoundingMode.HALF_DOWN));
		
		// HALF_EVEN
		assertEquals(new Digit("3"), number.setScale(0, RoundingMode.HALF_EVEN));
	}

	@Test
	@DisplayName("Test rounding edge cases")
	void testRoundingEdgeCases() {
		// Exact zeros
		assertEquals(zero, zero.setScale(2, RoundingMode.HALF_UP));
		
		// No decimal part
		assertEquals(one, one.setScale(3, RoundingMode.HALF_UP));
		
		// Negative numbers rounding
		Digit negative = new Digit("-3.14159");
		assertEquals(new Digit("-3"), negative.setScale(0, RoundingMode.HALF_UP));
		assertEquals(new Digit("-3.1"), negative.setScale(1, RoundingMode.HALF_UP));
	}

	@Test
	@DisplayName("Test rounding with unnecessary mode should throw exception")
	void testRoundingUnnecessary() {

		assertThrows(ArithmeticException.class, () -> pi.setScale(0, RoundingMode.UNNECESSARY));

	}

	// ==================== EQUALS AND HASHCODE TESTS ====================

	@Test
	@DisplayName("Test equals")
	void testEqualsAndHashCode() {
		Digit digit1 = new Digit("123.45");
		Digit digit2 = new Digit("123.45");
		Digit digit3 = new Digit("123.46");
		Digit digit4 = new Digit("123.45", false); // Different notation
		
		// Equality
		assertEquals(digit1, digit2);
		assertNotEquals(digit1, digit3);
		assertEquals(digit1, digit4); // Same value, different notation
		
		// Null and different class
		assertNotEquals(null, digit1);
		assertNotEquals("123.45", digit1);

	}

	// ==================== PARAMETERIZED TESTS ====================

	@ParameterizedTest
	@CsvSource({
		"123.456, 123.456",
		"-789.123, -789.123", 
		"0, 0",
		"0.0001, 0.0001",
		"+123.456, 123.456",
		"0000123.45600, 123.456"
	})
	@DisplayName("Parameterized number creation and representation")
	void testParameterizedNumberCreation(String input, String expected) {

		Digit digit = new Digit(input);
		assertEquals(expected, digit.stringValue());

	}

	@ParameterizedTest
	@CsvSource({
		"1, 2, 3",
		"0.5, 0.5, 1.0", 
		"-1, 1, 0",
		"100, 50, 150"
	})
	@DisplayName("Parameterized addition tests")
	void testParameterizedAddition(String a, String b, String expected) {
		Digit digitA = new Digit(a);
		Digit digitB = new Digit(b);
		assertEquals(new Digit(expected), digitA.add(digitB));
	}

	@ParameterizedTest
	@CsvSource({
		"1, 1, 1",
		"2, 3, 6", 
		"0.5, 0.5, 0.25",
		"-2, 3, -6",
		"562335, 21553156, 12120093979260",
		"625, 625, 390625"
	})
	@DisplayName("Parameterized multiplication tests")
	void testParameterizedMultiplication(String a, String b, String expected) {

		Digit digitA = new Digit(a);
		Digit digitB = new Digit(b);
		assertEquals(new Digit(expected), digitA.multiply(digitB));
	}

	// ==================== EDGE CASE TESTS ====================

	@Test
	@DisplayName("Test very large and very small numbers")
	void testExtremeNumbers() {
		// Very large number
		String largeInt = "123456789012345678901234567890";
		Digit large = new Digit(largeInt + ".1234567890123456789");
		assertEquals(largeInt, large.integerPart);
		assertEquals("1234567890123456789", large.decimalPart);
		
		// Very small number
		Digit small = new Digit("0.000000000000000000001");
		assertEquals("0", small.integerPart);
		assertEquals("000000000000000000001", small.decimalPart);
	}

	@Test
	@DisplayName("Test number normalization")
	void testNumberNormalization() {
		assertEquals("123", new Digit("0000123").integerPart);
		assertEquals("000456", new Digit("123.000456000").decimalPart);
		assertEquals("0", new Digit("0000").integerPart);
		assertEquals("", new Digit("0.000").decimalPart);
	}

	@Test
	@DisplayName("Test sign handling")
	void testSignHandling() {
		// Positive sign explicit
		Digit explicitPositive = new Digit("+123.456");
		assertFalse(explicitPositive.isNegative);
		
		// Negative sign
		Digit negative = new Digit("-123.456");
		assertTrue(negative.isNegative);
		
		// Zero with sign
		Digit zeroWithSign = new Digit("-0.000");
		assertFalse(zeroWithSign.isNegative); // Should normalize to positive zero
	}

	// ==================== PERFORMANCE TESTS ====================

	@Test
	@DisplayName("Test performance with multiple operations")
	void testPerformance() {
		Digit result = new Digit("1");

		// Chain multiple operations
		for (int i = 0; i < 10; i++) {
			result = result.add(one).multiply(new Digit("2")).divide(new Digit("2"), 10);
		}
		
		assertEquals(new Digit("11"), result);
	}
}