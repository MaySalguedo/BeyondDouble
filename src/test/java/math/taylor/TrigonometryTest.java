package math.taylor;

import math.core.Digit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class TrigonometryTest {

    private static final Digit PI = Trigonometry.PI();
    private static final Digit ZERO = new Digit("0");
    private static final Digit ONE = new Digit("1");
    private static final Digit HALF = new Digit("0.5");

    // ==================== CONSTANTE PI ====================

	@Test
    @DisplayName("Empty constructor")
    void testEmptyConstructor() {
        
        assertDoesNotThrow(() -> new Trigonometry());

    }

    @Test
    @DisplayName("PI constante tiene valor correcto")
    void testPIConstant() {
        // Verificar que PI está en el rango esperado
        assertTrue(PI.compareTo(new Digit("3.14")) > 0);
        assertTrue(PI.compareTo(new Digit("3.15")) < 0);
        
        // Verificar que no es cero
        assertTrue(PI.compareTo(ZERO) > 0);
    }

    // ==================== FUNCIONES BÁSICAS ====================

    @Test
    @DisplayName("sin(0) = 0")
    void testSinZero() {
        Digit result = Trigonometry.sin(ZERO);
        assertEquals(0, result.compareTo(ZERO));
    }

    @Test
    @DisplayName("sin(π/2) ≈ 1")
    void testSinPiOver2() {
        Digit angle = PI.divide(new Digit("2"), 10);
        Digit result = Trigonometry.sin(angle);
        // Usar tolerancia para la aproximación
        assertTrue(result.compareTo(new Digit("0.99")) > 0);
        assertTrue(result.compareTo(new Digit("1.01")) < 0);
    }

    @Test
    @DisplayName("cos(0) = 1")
    void testCosZero() {
        Digit result = Trigonometry.cos(ZERO);
        assertEquals(0, result.compareTo(ONE));
    }

    @Test
    @DisplayName("cos(π) ≈ -1")
    void testCosPi() {
        Digit result = Trigonometry.cos(PI);
        assertTrue(result.compareTo(new Digit("-1.01")) > 0);
        assertTrue(result.compareTo(new Digit("-0.99")) < 0);
    }

    @Test
    @DisplayName("tan(0) = 0")
    void testTanZero() {
        Digit result = Trigonometry.tan(ZERO);
        assertEquals(0, result.compareTo(ZERO));
    }

    @Test
    @DisplayName("tan(π/4) ≈ 1")
    void testTanPiOver4() {
        Digit angle = PI.divide(new Digit("4"), 10);
        Digit result = Trigonometry.tan(angle);
        assertTrue(result.compareTo(new Digit("0.98")) > 0);
        assertTrue(result.compareTo(new Digit("1.02")) < 0);
    }

    // ==================== FUNCIONES RECÍPROCAS ====================

    @Test
    @DisplayName("csc(π/2) ≈ 1")
    void testCscPiOver2() {
        Digit angle = PI.divide(new Digit("2"), 10);
        Digit result = Trigonometry.csc(angle);
        assertTrue(result.compareTo(new Digit("0.98")) > 0);
        assertTrue(result.compareTo(new Digit("1.02")) < 0);
    }

    @Test
    @DisplayName("sec(0) = 1")
    void testSecZero() {
        Digit result = Trigonometry.sec(ZERO);
        assertEquals(0, result.compareTo(ONE));
    }

    @Test
    @DisplayName("cot(π/2) ≈ 0")
    void testCotPiOver2() {
        Digit angle = PI.divide(new Digit("2"), 10);
        Digit result = Trigonometry.cot(angle);
        assertTrue(result.compareTo(new Digit("0.02")) < 0);
        assertTrue(result.compareTo(new Digit("-0.02")) > 0);
    }

    // ==================== FUNCIONES INVERSAS ====================

    @Test
    @DisplayName("arcsin(0) = 0")
    void testArcsinZero() {
        Digit result = Trigonometry.arcsin(ZERO);
        assertEquals(0, result.compareTo(ZERO));
    }

    @Test
    @DisplayName("arcsin(0.5) ≈ π/6")
    void testArcsinHalf() {
        Digit result = Trigonometry.arcsin(HALF);
        Digit expected = PI.divide(new Digit("6"), 10);
        
        // Verificar que está cerca de π/6 ≈ 0.5236
        assertTrue(result.compareTo(new Digit("0.5")) > 0);
        assertTrue(result.compareTo(new Digit("0.55")) < 0);
    }

    @Test
    @DisplayName("arccos(0) ≈ π/2")
    void testArccosZero() {
        Digit result = Trigonometry.arccos(ZERO);
        Digit halfPi = PI.divide(new Digit("2"), 10);
        
        assertTrue(result.compareTo(new Digit("1.5")) > 0);
        assertTrue(result.compareTo(new Digit("1.7")) < 0);
    }

    @Test
    @DisplayName("arctan(0) = 0")
    void testArctanZero() {
        Digit result = Trigonometry.arctan(ZERO);
        assertEquals(0, result.compareTo(ZERO));
    }

    @Test
    @DisplayName("arctan(-2) ≈ -1.107148...")
    void testArctanOne() {

        Digit result = Trigonometry.arctan(new Digit(-2));

        assertTrue(result.compareTo(new Digit("-2")) > 0);
        assertTrue(result.compareTo(new Digit("-1")) < 0);

    }

}