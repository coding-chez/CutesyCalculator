package com.example.cutesycalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import java.text.DecimalFormat;

public class CalcController {
    @FXML
    private TextField display;
    @FXML
    private GridPane rootPane;
    @FXML
    private ToggleButton themeToggle;

    private double num1 = 0;
    private String operator = "";
    private boolean start = true;
    private final DecimalFormat dfRepeating = new DecimalFormat("#.###");
    private final DecimalFormat dfNonRepeating = new DecimalFormat("#.#####");

    @FXML
    public void initialize() {
        display.setEditable(false);
        display.setText("0");
        themeToggle.setSelected(true);
    }

    @FXML
    public void processNumbers(String value) {
        if (start) {
            display.setText(value);
            start = false;
        } else {
            display.setText(display.getText() + value);
        }
    }

    @FXML
    public void processOperators(String operator) {
        if (!this.operator.isEmpty()) calculate();
        num1 = Double.parseDouble(display.getText());
        this.operator = operator.equals("×") ? "*" : operator.equals("÷") ? "/" : operator;
        start = true;
    }

    @FXML
    public void toggleSign() {
        if (display.getText().equals("0") || display.getText().equals("Error")) return;

        String currentText = display.getText();
        if (currentText.startsWith("-")) {
            display.setText(currentText.substring(1));
        } else {
            display.setText("-" + currentText);
        }
    }



    @FXML
    public void calculate() {
        if (operator.isEmpty()) return;

        double num2 = Double.parseDouble(display.getText());
        double result = 0;

        switch (operator) {
            case "+": result = num1 + num2; break;
            case "-": result = num1 - num2; break;
            case "*": result = num1 * num2; break;
            case "/":
                if (num2 == 0) {
                    display.setText("Error");
                    operator = "";
                    start = true;
                    return;
                }
                result = num1 / num2;
                String resultStr = String.valueOf(result);
                if (resultStr.length() > 7 && resultStr.substring(0, 7).matches(".*(\\.\\d+?)\\1.*")) {
                    display.setText(dfRepeating.format(result));
                } else {
                    display.setText(dfNonRepeating.format(result));
                }
                operator = "";
                start = true;
                return;
        }

        display.setText(dfNonRepeating.format(result));
        operator = "";
        start = true;
    }

    @FXML
    public void handleKeyPressed(KeyEvent event) {
        String key = event.getText();
        if (key.matches("[0-9.]")) {
            processNumbers(key);
        } else if (key.matches("[+\\-*/]")) {
            processOperators(key);
        } else if (event.getCode().toString().equals("ENTER")) {
            calculate();
        } else if (event.getCode().toString().equals("ESCAPE")) {
            clear();
        }else if(event.getCode().toString().equals("BACK_SPACE")){
            backspace();
        } else if (event.getCode().toString().equals("N")) {
            toggleSign();
        }
    }

    @FXML
    public void backspace() {
        String text = display.getText();
        if (!text.isEmpty() && !text.equals("0")) {
            display.setText(text.length() > 1 ? text.substring(0, text.length() - 1) : "0");
        }
    }

    @FXML
    public void clear() {
        display.setText("0");
        operator = "";
        start = true;
        num1 = 0;
    }

    @FXML
    public void onNumberButtonClick(ActionEvent event) {
        Button source = (Button) event.getSource();
        processNumbers(source.getText());
    }

    @FXML
    public void onOperatorButtonClick(ActionEvent event) {
        Button source = (Button) event.getSource();
        processOperators(source.getText());
    }

    @FXML
    public void processNumbers(ActionEvent event) {
        Button source = (Button) event.getSource();
        String value = source.getText();
        if (start) {
            display.setText(value);
            start = false;
        } else {
            display.setText(display.getText() + value);
        }
    }

    @FXML
    public void processOperators(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        String operator = source.getText();
        processOperators(operator);
    }

    @FXML
    public void toggleTheme() {
        if (themeToggle.isSelected()) {
            rootPane.getStyleClass().remove("light-theme");
            rootPane.getStyleClass().add("dark-theme");
            themeToggle.setText("☀");
        } else {
            rootPane.getStyleClass().remove("dark-theme");
            rootPane.getStyleClass().add("light-theme");
            themeToggle.setText("☾");
        }
    }
}