import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void testSin() {
        Calculator calculator = new Calculator("sin(0)");
        assertEquals("0", calculator.calculate());
    }

    @Test
    void testCos() {
        Calculator calculator = new Calculator("cos(0)");
        assertEquals("1", calculator.calculate());
    }

    @Test
    void testTan() {
        Calculator calculator = new Calculator("tan(0)");
        assertEquals("0", calculator.calculate());
    }

    @Test
    void testSqrt() {
        Calculator calculator = new Calculator("sqrt(4)");
        assertEquals("2", calculator.calculate());
    }

    @Test
    void testLn() {
        Calculator calculator = new Calculator("ln(1)");
        assertEquals("0", calculator.calculate());
    }

    @Test
    void testLog() {
        Calculator calculator = new Calculator("log₁₀(1)");
        assertEquals("0", calculator.calculate());
    }

    @Test
    void testPower() {
        Calculator calculator = new Calculator("2^3");
        assertEquals("8", calculator.calculate());
    }

    @Test
    void testComplexExpression1() {
        Calculator calculator = new Calculator("sin(0)+cos(0)");
        assertEquals("1", calculator.calculate());
    }

    @Test
    void testComplexExpression2() {
        Calculator calculator = new Calculator("sqrt(4)^2");
        assertEquals("4", calculator.calculate());
    }

    @Test
    void testComplexExpression3() {
        Calculator calculator = new Calculator("ln(1+1)");
        assertEquals("0.6931471805599453", calculator.calculate());
    }

    @Test
    void testLongExpression1() {
        Calculator calculator = new Calculator("sin(0)+cos(0)+tan(0)+sqrt(4)+ln(1)+log(1)+2^3");
        assertEquals("10", calculator.calculate());
    }

    @Test
    void testLongExpression2() {
        Calculator calculator = new Calculator("sin(0)×cos(0)×tan(0)*sqrt(4)*ln(1)×log(1)×2^3");
        assertEquals("0", calculator.calculate());
    }

    @Test
    void testLongExpression3() {
        Calculator calculator = new Calculator("sin(0)-cos(0)-tan(0)-sqrt(4)-ln(1)-log(1)-2^3");
        assertEquals("-10", calculator.calculate());
    }

    @Test
    void testLongExpression4() {
        Calculator calculator = new Calculator("sin(0)/cos(0)/tan(0)/sqrt(4)/ln(1)/log(1)/2^3");
        assertEquals("Math Error , [AC] : Cancel", calculator.calculate());
    }

    @Test
    void testLongExpression5() {
        Calculator calculator = new Calculator("sin(0)+cos(0)×tan(0)-sqrt(4)/ln(1)+log(1)-2^3");
        assertEquals("-6", calculator.calculate());
    }

    @Test
    void testComplexExpression4() {
        Calculator calculator = new Calculator("(sin(0)+cos(0))/2");
        assertEquals("0.5", calculator.calculate());
    }

    @Test
    void testComplexExpression5() {
        Calculator calculator = new Calculator("sqrt((4+16)/2)");
        assertEquals("2.8284271247461903", calculator.calculate());
    }

    @Test
    void testComplexExpression6() {
        Calculator calculator = new Calculator("ln((1+1)/(1+1-1))");
        assertEquals("0.6931471805599453", calculator.calculate());
    }

    @Test
    void testComplexExpression7() {
        Calculator calculator = new Calculator("log(100/(10+10))");
        assertEquals("1", calculator.calculate());
    }

    @Test
    void testComplexExpression8() {
        Calculator calculator = new Calculator("(2^3)/(4-2)");
        assertEquals("4", calculator.calculate());
    }

    @Test
    void testComplexExpression9() {
        Calculator calculator = new Calculator("((sin(0)+cos(0))×tan(0))/sqrt(4)");
        assertEquals("0", calculator.calculate());
    }

    @Test
    void testComplexExpression10() {
        Calculator calculator = new Calculator("ln(1/((1+1)-1))");
        assertEquals("Math Error , [AC] : Cancel", calculator.calculate());
    }
}