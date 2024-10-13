package ro.tuc.tp;
import BusinessLogic.Operations;
import DataModels.Polynomial;
import Helpers.PolynomialParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class OperationsTest {
    @Test
    public void addTest() {
        Polynomial p1 = new Polynomial(PolynomialParser.getMonomials("4x^3+3x^2-2x+1"));
        Polynomial p2 = new Polynomial(PolynomialParser.getMonomials("-x^3+x^2+4x-5"));
        Polynomial result = new Polynomial(PolynomialParser.getMonomials("3x^3+4x^2+2x-4"));
        assertEquals(result.listPolynomialAsString(), Operations.add(p1, p2).listPolynomialAsString());
    }

    @Test
    public void subtractTest() {
        Polynomial p1 = new Polynomial(PolynomialParser.getMonomials("5x^4-x^3+2x^2-7"));
        Polynomial p2 = new Polynomial(PolynomialParser.getMonomials("3x^3-2x+1"));
        Polynomial expectedResult = new Polynomial(PolynomialParser.getMonomials("5x^4-4x^3+2x^2+2x-8"));
        assertEquals(expectedResult.listPolynomialAsString(), Operations.subtract(p1, p2).listPolynomialAsString());
    }

    @Test
    public void multiplyTest() {
        Polynomial p1 = new Polynomial(PolynomialParser.getMonomials("x^2-2x+1"));
        Polynomial p2 = new Polynomial(PolynomialParser.getMonomials("2x^2+3"));
        Polynomial expectedResult = new Polynomial(PolynomialParser.getMonomials("2x^4-4x^3+5x^2-6x+3"));
        assertEquals(expectedResult.listPolynomialAsString(), Operations.multiply(p1, p2).listPolynomialAsString());
    }

    @Test
    public void divideTest() {
        Polynomial p1 = new Polynomial(PolynomialParser.getMonomials("x^3-3x+2"));
        Polynomial p2 = new Polynomial(PolynomialParser.getMonomials("x-1"));
        Polynomial quotientExpected = new Polynomial(PolynomialParser.getMonomials("x^2+x-2"));
        // Assuming divide method returns quotient for '1' and remainder for '0'
        assertEquals(quotientExpected.listPolynomialAsString(), Operations.divide(p2, p1, 1).listPolynomialAsString());
    }

    @Test
    public void derivativeTest() {
        Polynomial p = new Polynomial(PolynomialParser.getMonomials("6x^5-4x^3+x^2-8x+7"));
        Polynomial expectedResult = new Polynomial(PolynomialParser.getMonomials("30x^4-12x^2+2x-8"));
        assertEquals(expectedResult.listPolynomialAsString(), Operations.derivative(p).listPolynomialAsString());
    }
    @Test
    public void integrateTest() {
        Polynomial p3 = new Polynomial(PolynomialParser.getMonomials("5x^4-3x^2+2x-5"));
        Polynomial expectedResult = new Polynomial(PolynomialParser.getMonomials("x^5-x^3+x^2-5x"));
        assertEquals(Operations.integrate(p3).listPolynomialAsString(), expectedResult.listPolynomialAsString());
    }
}