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

import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class DigitTest {

	// Mock values
	private Digit zero;
	private Digit one;
	private Digit minusOne;
	private Digit pi;
	private Digit half;

	@BeforeEach void setUp() {

		this.zero = new Digit("0");
		this.one = new Digit("1");
		this.minusOne = new Digit("-1");
		this.pi = new Digit("3.1415926535");
		this.half = new Digit("0.5");

	}

	@Test @DisplayName("Constructor test using differents parameters")
	public void testConstructors() {

		// String Constructor
		assertDoesNotThrow(() -> new Digit("123.456"));

		// Double Constructor
		assertDoesNotThrow(() -> new Digit(123.456));

		// Split string values Constructor
		assertDoesNotThrow(() -> new Digit("123", "456"));

		// Split string values Constructor
		assertDoesNotThrow(() -> new Digit(123L, 456L));

		// Notationed Constructor
		assertDoesNotThrow(() -> new Digit("123.456", false));

	}

	@Test @DisplayName("Constructor test with ivalid parameters")
	public void testInvalidConstructor() {

		assertThrows(IllegalNumberFormatException.class, () -> new Digit("abc"));
		assertThrows(IllegalNumberFormatException.class, () -> new Digit(""));
		assertThrows(IllegalNumberFormatException.class, () -> new Digit(null));

	}

	@Test @DisplayName("Test toString() method")
	public void testToString() {

		assertEquals("0", zero.toString());
		assertEquals("1", one.toString());
		assertEquals("-1", minusOne.toString());
		assertEquals("3.1415926535", pi.toString());
		assertEquals("0.5", half.toString());

	}

	@Test @DisplayName("Convertion test methods")
	public void testConversionMethods() {

		assertEquals(1, one.intValue());
		assertEquals(1L, one.longValue());
		assertEquals(1.0f, one.floatValue(), 0.0001f);
		assertEquals(1.0, one.doubleValue(), 0.0001);
		assertEquals(1, one.byteValue());
		assertEquals(1, one.shortValue());
		assertEquals("1", one.stringValue());

	}

	@Test @DisplayName("Comparation test methods")
	public void testComparations() {

		// Compare to zero
		assertEquals(0, zero.compareToZero());
		assertEquals(1, one.compareToZero());
		assertEquals(-1, minusOne.compareToZero());

		// Compare to one
		assertEquals(0, one.compareToOne());
		assertEquals(1, new Digit("2").compareToOne());
		assertEquals(-1, half.compareToOne());

		// Compare to minus one
		assertEquals(0, minusOne.compareToMinusOne());
		assertEquals(1, zero.compareToMinusOne());
		assertEquals(-1, new Digit("-2").compareToMinusOne());

		// Compare between instances
		assertTrue(one.compareTo(zero) > 0);
		assertTrue(zero.compareTo(one) < 0);
		assertEquals(0, one.compareTo(one));

	}

	@Test @DisplayName("Basic arithmatic operations test")
	public void testArithmeticOperations() {

		// Addition
		assertEquals(new Digit("2"), one.add(one));
		assertEquals(new Digit("0"), one.add(minusOne));
		
		// Subtraction
		assertEquals(new Digit("0"), one.subtract(one));
		assertEquals(new Digit("2"), one.subtract(minusOne));
		
		// Multiplication
		assertEquals(new Digit("4"), new Digit("2").multiply(new Digit("2")));
		assertEquals(new Digit("-4"), new Digit("2").multiply(new Digit("-2")));
		
		// Division
		assertEquals(new Digit("2"), new Digit("4").divide(new Digit("2")));
		assertEquals(new Digit("0.5"), one.divide(new Digit("2")));

	}

	@Test @DisplayName("Division by zero test")
	public void testDivisionByZero() {

		assertThrows(UndeterminedException.class, () -> one.divide(zero));

	}

	@Test @DisplayName("Enhanced operations test")
	public void testEnhancedOperations() {

		// Absolute value
		assertEquals(one, minusOne.abs());

		// Negation
		assertEquals(minusOne, one.negate());

		// Increment by one
		assertEquals(new Digit("1.5"), half.increase());

		// Decrement by one
		assertEquals(new Digit("-0.5"), half.decrease());

		// inverse
		assertEquals(new Digit("2"), one.divide(half));

		// Module
		assertEquals(zero, new Digit("4").module(new Digit("2")));

	}

	@Test @DisplayName("Rounding test")
	public void testRounding() {

		Digit number = new Digit("3.14159");

		// 2 decimal roundind
		Digit rounded = number.setScale(2, RoundingMode.HALF_UP);
		assertEquals(new Digit("3.14"), rounded);

		// 0 decimals rounding
		rounded = number.setScale(0, RoundingMode.HALF_UP);
		assertEquals(new Digit("3"), rounded);

	}

	@ParameterizedTest @CsvSource({

		"123.456, 123.456",
		"-789.123, -789.123",
		"0, 0",
		"0.0001, 0.0001"

	}) @DisplayName("Parametric test for creation & number representation")
	void testParametricNumberCreation(String input, String expected) {

		Digit digit = new Digit(input);
		assertEquals(expected, digit.toString());

	}

}