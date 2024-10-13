package Helpers;

import DataModels.Monomial;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolynomialParser {
    public static Boolean validateInput(String input) {
        String polynomialRegex = "^-?(\\d*x|\\d+)(\\^\\d+)?((\\+|-)(\\d*x|\\d+)(\\^\\d+)?)*$";
        Pattern pattern = Pattern.compile(polynomialRegex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static TreeMap<Integer, Monomial> getMonomials(String input) {
        TreeMap<Integer, Monomial> monomials = new TreeMap<>();
        String monomialRegex = "-?\\d*x(\\^\\d+)?|-?\\d+";
        Pattern pattern = Pattern.compile(monomialRegex);
        Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                String monomial = matcher.group();
                Integer coefficient = 1; //default coefficient
                Integer exponent = 0; //default exponent
                if (monomial.contains("x")) { //term with x
                    int coIndex = monomial.indexOf("x");
                    String stringCoefficient = monomial.substring(0, coIndex);
                    if (!stringCoefficient.isEmpty() && !stringCoefficient.equals("+") && !stringCoefficient.equals("-")) {
                        coefficient = Integer.parseInt(stringCoefficient);
                    } else if (stringCoefficient.equals("-")) {
                        coefficient = -1;
                    }
                    int powIndex = monomial.indexOf("^");
                    String stringExponent = monomial.substring(powIndex + 1);
                    if (powIndex != -1) {
                        exponent = Integer.parseInt(stringExponent);
                    } else {
                        exponent = 1; //exponent 1 if no ^
                    }
                } else { //free term
                    coefficient = Integer.parseInt(monomial);
                }
                Number castCoefficient = coefficient;
                if (monomials.containsKey(exponent)) {
                    Number existingCoefficient = monomials.get(exponent).getCoefficient();
                    monomials.put(exponent, new Monomial(existingCoefficient.doubleValue() + castCoefficient.doubleValue(), exponent));
                } else {
                    monomials.put(exponent, new Monomial(castCoefficient, exponent));
                }
            }
        return monomials;
    }
}
