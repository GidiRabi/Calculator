import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private static Calculator calculator;

    @BeforeAll
    static void initAll() {
        calculator = Calculator.getInstance("");
    }

    @Test
    void testSin() {
        calculator.setExpression("sin(0)");
        assertEquals("0", calculator.calculate());
    }

    @Test
    void testCos() {
        calculator.setExpression("cos(0)");
        assertEquals("1", calculator.calculate());
    }

    @Test
    void testTan() {
        calculator.setExpression("tan(0)");
        assertEquals("0", calculator.calculate());
    }

    @Test
    void testSqrt() {
        calculator.setExpression("sqrt(4)");
        assertEquals("2", calculator.calculate());
    }

    @Test
    void testLn() {
        calculator.setExpression("ln(1)");
        assertEquals("0", calculator.calculate());
    }

    @Test
    void testLog() {
        calculator.setExpression("log₁₀(1)");
        assertEquals("0", calculator.calculate());
    }

    @Test
    void testPower() {
        calculator.setExpression("2^3");
        assertEquals("8", calculator.calculate());
    }

    @Test
    void testComplexExpression1() {
        calculator.setExpression("sin(0)+cos(0)");
        assertEquals("1", calculator.calculate());
    }

    @Test
    void testComplexExpression2() {
        calculator.setExpression("sqrt(4)^2");
        assertEquals("4", calculator.calculate());
    }

    @Test
    void testComplexExpression3() {
        calculator.setExpression("ln(1+1)");
        assertEquals("0.6931471805599453", calculator.calculate());
    }

    @Test
    void testLongExpression1() {
        calculator.setExpression("sin(0)+cos(0)+tan(0)+sqrt(4)+ln(1)+log(1)+2^3");
        assertEquals("11", calculator.calculate());
    }

    @Test
    void testLongExpression2() {
        calculator.setExpression("sin(0)×cos(0)×tan(0)×sqrt(4)×ln(1)×log(1)×2^3");
        assertEquals("0", calculator.calculate());
    }

    @Test
    void testLongExpression3() {
        calculator.setExpression("sin(0)-cos(0)-tan(0)-sqrt(4)-ln(1)-log(1)-2^3");
        assertEquals("-11", calculator.calculate());
    }

    @Test
    void testLongExpression4() {
        calculator.setExpression("sin(0)/cos(0)/tan(0)/sqrt(4)/ln(1)/log(1)/2^3");
        assertEquals("Math Error , [AC] : Cancel", calculator.calculate());
    }

    @Test
    void testLongExpression5() {
        calculator.setExpression("sin(0)+cos(0)×tan(0)-sqrt(4)/ln(1)+log(1)-2^3");
        assertEquals("Math Error , [AC] : Cancel", calculator.calculate());
    }//we got -infinity instead of error /ln(1)

    @Test
    void testComplexExpression4() {
        calculator.setExpression("(sin(0)+cos(0))/2");
        assertEquals("0.5", calculator.calculate());
    }

    @Test
    void testComplexExpression5() {
        calculator.setExpression("sqrt((4+16)/2)");
        assertEquals("3.1622776601683795", calculator.calculate());
    }

    @Test
    void testComplexExpression6() {
        calculator.setExpression("ln((1+1)/(1+1-1))");
        assertEquals("0.6931471805599453", calculator.calculate());
    }

    @Test
    void testComplexExpression7() {
        calculator.setExpression("log(100/(10+10))");
        assertEquals("0.6989700043360189", calculator.calculate());
    }

    @Test
    void testComplexExpression8() {
        calculator.setExpression("(2^3)/(4-2)");
        assertEquals("4", calculator.calculate());
    }

    @Test
    void testComplexExpression9() {
        calculator.setExpression("((sin(0)+cos(0))×tan(0))/sqrt(4)");
        assertEquals("0", calculator.calculate());
    }

    @Test
    void testComplexExpression10() {
        calculator.setExpression("ln(1/((1+1)-1))");
        assertEquals("0", calculator.calculate());
    }

    @Test
    void testVeryComplexExpression1() {
        calculator.setExpression("tan(4 + sin(4)) + 2^(sin(3^2))");
        assertEquals("1.4325942606234885", calculator.calculate());
    }

    @Test
    void testVeryComplexExpression2() {
        calculator.setExpression("cos(27 + sqrt(5)) / ln(1 + 4 + sqrt(7))");
        assertEquals("-0.28124697664869", calculator.calculate());
    }

    @Test
    void testVeryComplexExpression3() {
        calculator.setExpression("sin(4 + cos(3)) / (1 + cos(3) + sqrt(3))");
        assertEquals("0.07531651081592326", calculator.calculate());
    }

    @Test
    void testVeryComplexExpression4() {
        calculator.setExpression("ln(sin(3) + tan(54)) × tan(7^(sqrt(2)))");
        assertEquals("0.007181043111533832", calculator.calculate());
    }

    @Test
    void testVeryComplexExpression5() {
        calculator.setExpression("cos(ln(3) + sin(4)) / tan(5 + sqrt(5))");
        assertEquals("0.6696460076786583", calculator.calculate());
    }

}