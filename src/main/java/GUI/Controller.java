package GUI;

import BusinessLogic.Operations;
import DataModels.Polynomial;
import Helpers.PolynomialParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private TextField firstPolynomial;
    @FXML
    private TextField secondPolynomial;
    @FXML
    private RadioButton rb1;
    @FXML
    private RadioButton rb2;
    @FXML
    private Label errorMessage;

    @FXML
    public void handleButtonAction(ActionEvent event) {
        errorMessage.setText("");
        Button buttonPressed = (Button) event.getSource();
        if (rb1.isSelected()) {
            firstPolynomial.setText(firstPolynomial.getText() + buttonPressed.getText());
        } else if (rb2.isSelected()) {
            secondPolynomial.setText(secondPolynomial.getText() + buttonPressed.getText());
        } else {
            errorMessage.setText("Select which input you want to update!!");
            errorMessage.setTextFill(Color.RED);
        }
    }
    public boolean validateInputController() {
        if (firstPolynomial.getText().isEmpty() && secondPolynomial.getText().isEmpty()) {
            errorMessage.setText("Both fields are empty");
            errorMessage.setTextFill(Color.RED);
            return false;
        }
        else if (firstPolynomial.getText().isEmpty()) {
            errorMessage.setText("First polynomial is empty");
            errorMessage.setTextFill(Color.RED);
            return false;
        }
        else if (secondPolynomial.getText().isEmpty()) {
            errorMessage.setText("Second polynomial is empty");
            errorMessage.setTextFill(Color.RED);
            return false;
        }
        else if (!PolynomialParser.validateInput(firstPolynomial.getText()) && !PolynomialParser.validateInput(secondPolynomial.getText())) {
            firstPolynomial.setText("");
            secondPolynomial.setText("");
            errorMessage.setText("Both polynomails are wrong");
            errorMessage.setTextFill(Color.RED);
            return false;
        } else if (!PolynomialParser.validateInput(firstPolynomial.getText())) {
            firstPolynomial.setText("");
            errorMessage.setText("First polynomial is wrong");
            errorMessage.setTextFill(Color.RED);
            return false;
        } else if (!PolynomialParser.validateInput(secondPolynomial.getText())) {
            secondPolynomial.setText("");
            errorMessage.setText("Second polynomial is wrong");
            errorMessage.setTextFill(Color.RED);
            return false;
        }
        else {
            errorMessage.setText("");
        }
        return true;
    }
    public Integer selectWithRadioButton() {
        if (rb1.isSelected()) {
            return 1;
        } else if (rb2.isSelected()) {
            return 2;
        }
        return 0;
    }
    public void setResultText(Polynomial result, String operation, int order) {
        if (operation != "Derivative" && operation != "Integrate") {
            errorMessage.setText(result.listPolynomialAsString());
            errorMessage.setTextFill(Color.BLACK);
        }
        else {
            if (order == 1) {
                errorMessage.setText(operation + " of first: " + result.listPolynomialAsString());
                errorMessage.setTextFill(Color.BLACK);
            } else {
                errorMessage.setText(operation + " of second: " + result.listPolynomialAsString());
                errorMessage.setTextFill(Color.BLACK);
            }
        }
    }
    public void setErrorMessage(String input) {
        errorMessage.setText(input);
        errorMessage.setTextFill(Color.RED);
    }
    public void setResult (ActionEvent event) {
        if (validateInputController()) {
            Button pressedButton = (Button) event.getSource();
            Polynomial p1 = new Polynomial(PolynomialParser.getMonomials(firstPolynomial.getText()));
            Polynomial p2 = new Polynomial(PolynomialParser.getMonomials(secondPolynomial.getText()));
            switch (pressedButton.getText()) {
                case "Add":
                    Polynomial resultAdd = Operations.add(p2, p1);
                    setResultText(resultAdd, "Addition", 0);
                    break;
                case "Subtract":
                    Polynomial resultSubtract = Operations.subtract(p1, p2);
                    setResultText(resultSubtract, "Subtraction", 0);
                    break;
                case "Multiply":
                    Polynomial resultMultiply = Operations.multiply(p2, p1);
                    setResultText(resultMultiply, "Multiplication", 0);
                    break;
                case "Divide":
                    if (secondPolynomial.getText().equals("0")) {
                        setErrorMessage("Divison by 0 is not allowed");
                    } else {
                        Polynomial quotient = Operations.divide(p2, p1, 1);
                        Polynomial reminder = Operations.divide(p2, p1, 0);
                        errorMessage.setText("Quotient: " + quotient.listPolynomialAsString() + " Reminder: " + reminder.listPolynomialAsString());
                        errorMessage.setTextFill(Color.BLACK);
                        break;
                    }
            }
        }
    }

    public void derivativeOrIntegrate(ActionEvent event) {
        Button buttonPressed = (Button) event.getSource();
        if (!validateInputController()) {
            setErrorMessage("One or more inputs are empty or not polynomials");
        } else {
            Polynomial p1 = new Polynomial(PolynomialParser.getMonomials(firstPolynomial.getText()));
            Polynomial p2 = new Polynomial(PolynomialParser.getMonomials(secondPolynomial.getText()));
            switch (buttonPressed.getText()) {
                case "f'":
                    if (selectWithRadioButton() == 0) {
                        setErrorMessage("Select which input you want to derivate");
                    } else if (selectWithRadioButton() == 1) {
                        if (firstPolynomial.getText().isEmpty()) {
                            setErrorMessage("First input is empty");
                        } else {
                            Polynomial derivativeOfFirst = Operations.derivative(p1);
                            setResultText(derivativeOfFirst, "Derivative", 1);
                        }
                    } else if (selectWithRadioButton() == 2) {
                        if (secondPolynomial.getText().isEmpty()) {
                            setErrorMessage("Second input is empty");
                        } else {
                            Polynomial derivativeOfSecond = Operations.derivative(p2);
                            setResultText(derivativeOfSecond, "Derivative", 2);
                        }
                    }
                    break;
                case "∫":
                    if (selectWithRadioButton() == 0) {
                        setErrorMessage("Select which input you want to integrate");
                    } else if (selectWithRadioButton() == 1) {
                        if (firstPolynomial.getText().isEmpty()) {
                            setErrorMessage("First input is empty");
                        } else {
                            Polynomial integrateOfFirst = Operations.integrate(p1);
                            setResultText(integrateOfFirst, "Integrate", 1);
                        }
                    } else if (selectWithRadioButton() == 2) {
                        if (secondPolynomial.getText().isEmpty()) {
                            setErrorMessage("Second input is empty");
                        } else {
                            Polynomial integrateOfSecond = Operations.integrate(p2);
                            setResultText(integrateOfSecond, "Integrate", 2);
                        }
                    }
                    break;
            }
        }
    }
    public void delete() {
        if (selectWithRadioButton() == 0) {
            setErrorMessage("Input is already empty");
        } else if (selectWithRadioButton() == 1) {
            if (firstPolynomial.getText().isEmpty()) {
                setErrorMessage("Input is already empty");
            } else {
                firstPolynomial.setText("");
                errorMessage.setText("");
            }
        } else if (selectWithRadioButton() == 2) {
            if (secondPolynomial.getText().isEmpty()) {
                setErrorMessage("Input is already empty");
            } else {
                secondPolynomial.setText("");
                errorMessage.setText("");
            }
        }
    }
}