package DataModels;

import java.util.Map;
import java.util.TreeMap;

public class Polynomial {
    private TreeMap<Integer, Monomial> polynomial;

    public Polynomial(TreeMap<Integer, Monomial> polynomial) {
        this.polynomial = polynomial;
    }

    public TreeMap<Integer, Monomial> getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(TreeMap<Integer, Monomial> polynomial) {
        this.polynomial = polynomial;
    }

    public String listPolynomialAsString() {
        boolean isFirstTerm = true;
        String term = "";
        if (getPolynomial().isEmpty()) {
            return "0";
        }
        for (Map.Entry<Integer, Monomial> entry : getPolynomial().descendingMap().entrySet()) {
            int exponent = entry.getKey();
            Number coefficient = entry.getValue().getCoefficient();
            if (coefficient.doubleValue() > 0) {
                if (!isFirstTerm) {
                    term += "+";
                }
                if (coefficient.doubleValue() != 1 || exponent == 0) {
                    Number floatingPart = coefficient.doubleValue() - (int) coefficient.doubleValue();
                    if (floatingPart.doubleValue() == 0) {
                        term += coefficient.intValue();
                    } else {
                        term += String.format("%.2f", coefficient);
                    }

                }
            } else if (coefficient.doubleValue() < 0) {
                if (coefficient.doubleValue() == -1 && exponent != 0) {
                    term += "-";
                } else {
                    double floatingPart = coefficient.doubleValue() % 1;
                    if (floatingPart == 0) {
                        term += coefficient.intValue();
                    }
                    else {
                        term += String.format("%.2f", coefficient.doubleValue());
                    }
                }
            }
            if (exponent > 0) {
                term += "x";
                if (exponent > 1) {
                    term += "^" + exponent;
                }
            }
            isFirstTerm = false;
        }
        return term;
    }
}
