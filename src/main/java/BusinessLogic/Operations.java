package BusinessLogic;

import DataModels.Monomial;
import DataModels.Polynomial;

import java.util.Map;
import java.util.TreeMap;

public class Operations {
    public static Polynomial add(Polynomial p1, Polynomial p2) {
        TreeMap<Integer, Monomial> result = new TreeMap<>();
        result.putAll(p1.getPolynomial()); //put p1 into result
        for (Map.Entry<Integer, Monomial> entry : p2.getPolynomial().entrySet()) {
            int exponent = entry.getKey();
            if (result.containsKey(exponent)) {
                Number first = entry.getValue().getCoefficient();
                Number second = result.get(exponent).getCoefficient();
                Number newCoefficient = first.doubleValue() + second.doubleValue();
                Monomial added = new Monomial(newCoefficient, exponent);
                if (newCoefficient.doubleValue() == 0) {
                    result.remove(exponent);
                } else {
                    result.put(exponent, added);
                }
            } else {
                result.put(exponent, new Monomial(entry.getValue().getCoefficient(), exponent));
            }
        }
        return new Polynomial(result);
    }

    public static Polynomial subtract(Polynomial p1, Polynomial p2) {
        TreeMap<Integer, Monomial> result = new TreeMap<>();
        result.putAll(p1.getPolynomial()); //put p1 into result
        for (Map.Entry<Integer, Monomial> entry : p2.getPolynomial().entrySet()) {
            int exponent = entry.getKey();
            if (result.containsKey(exponent)) {
                Number first = entry.getValue().getCoefficient().doubleValue();
                Number second = result.get(exponent).getCoefficient().doubleValue();
                Number newCoefficient = second.doubleValue() - first.doubleValue();
                Monomial subtracted = new Monomial(newCoefficient, exponent);
                if (newCoefficient.doubleValue() == 0) {
                    result.remove(exponent);
                } else {
                    result.put(exponent, subtracted);
                }
            } else {
                result.put(exponent, new Monomial(-entry.getValue().getCoefficient().doubleValue(), exponent));
            }
        }
        Polynomial resultPolynomial = new Polynomial(result);
        return resultPolynomial;
    }

    public static Polynomial multiply(Polynomial p1, Polynomial p2) {
        TreeMap<Integer, Monomial> result = new TreeMap<>();
        for (Map.Entry<Integer, Monomial> entry1 : p1.getPolynomial().entrySet()) {
            for (Map.Entry<Integer, Monomial> entry2 : p2.getPolynomial().entrySet()) {
                Number coefficient1 = entry1.getValue().getCoefficient();
                Number coefficient2 = entry2.getValue().getCoefficient();
                int exponent1 = entry1.getKey();
                int exponent2 = entry2.getKey();
                Number newCoefficient = coefficient1.doubleValue() * coefficient2.doubleValue();
                int newExponent = exponent1 + exponent2;

                if (newCoefficient.doubleValue() != 0) {
                    if (result.containsKey(newExponent)) { //if a term with new exponent already exists, we add the newCoefficient to it
                        Monomial existingMonomial = result.get(newExponent);
                        Number updatedCoefficient = existingMonomial.getCoefficient().doubleValue() + newCoefficient.doubleValue(); //if updatedCoefficient is 0, remove the monomial
                        if (updatedCoefficient.doubleValue() == 0) {
                            result.remove(newExponent);
                        } else {
                            result.put(newExponent, new Monomial(updatedCoefficient, newExponent)); //otherwise update with the updateCoefficient
                        }
                    } else {
                        Monomial m = new Monomial(newCoefficient, newExponent);
                        result.put(newExponent, m);
                    }
                }
            }
        }
        return new Polynomial(result);
    }


    public static Polynomial derivative(Polynomial p1) {
        TreeMap<Integer, Monomial> result = new TreeMap<>();
        for (Map.Entry<Integer, Monomial> entry : p1.getPolynomial().entrySet()) {
            int oldExponent = entry.getKey();
            Number oldCoefficient = entry.getValue().getCoefficient();
            if (oldExponent > 0) {
                int newExponent = oldExponent - 1;
                Number newCoefficient = oldCoefficient.doubleValue() * oldExponent;
                Monomial m = new Monomial(newCoefficient, newExponent);
                result.put(newExponent, m);
            }
        }
        return new Polynomial(result);
    }

    public static Polynomial integrate(Polynomial p1) {
        TreeMap<Integer, Monomial> result = new TreeMap<>();
        for (Map.Entry<Integer, Monomial> entry : p1.getPolynomial().entrySet()) {
            Number oldCoefficient = entry.getValue().getCoefficient();
            int oldExponent = entry.getKey();
            int newExponent = oldExponent + 1;
            Number newCoefficient = oldCoefficient.doubleValue() / (newExponent);
            Monomial m = new Monomial(newCoefficient, newExponent);
            result.put(newExponent, m);
        }
        Polynomial polynomialResult = new Polynomial(result);
        return polynomialResult;
    }

    public static Polynomial divide(Polynomial divisor, Polynomial dividend, int needed) {
        TreeMap<Integer, Monomial> quotient = new TreeMap<>();
        Polynomial reminder = new Polynomial(new TreeMap<>(dividend.getPolynomial()));
        while (!reminder.getPolynomial().isEmpty() && reminder.getPolynomial().lastKey() >= divisor.getPolynomial().lastKey()) {
            int dividendExponent = reminder.getPolynomial().lastKey();
            int divisorExponent = divisor.getPolynomial().lastKey();
            Number dividendCoefficient = reminder.getPolynomial().get(dividendExponent).getCoefficient();
            Number divisorCoefficient = divisor.getPolynomial().get(divisorExponent).getCoefficient();

            int newExponent = dividendExponent - divisorExponent;
            Double newCoefficient = dividendCoefficient.doubleValue() / divisorCoefficient.doubleValue();

            Monomial quotientTerm = new Monomial(newCoefficient, newExponent);
            Polynomial quotientTermAsPolynomial = new Polynomial(new TreeMap<>());
            quotientTermAsPolynomial.getPolynomial().put(newExponent, quotientTerm);
            Polynomial product = multiply(quotientTermAsPolynomial, divisor);

            reminder = subtract(reminder, product);
            quotient.putAll(quotientTermAsPolynomial.getPolynomial());
        }
        if (needed == 0) {
            return reminder;
        }
        return new Polynomial(quotient);
    }
}

