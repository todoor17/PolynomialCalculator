package DataModels;

public class Monomial {
    Number coefficient;
    Integer exponent;
    public Monomial(Number coefficient, Integer exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    public Number getCoefficient() {
        return coefficient;
    }

    public Integer getExponent() {
        return exponent;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public void setExponent(Integer exponent) {
        this.exponent = exponent;
    }
}
