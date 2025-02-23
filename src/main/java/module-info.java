module com.example.cutesycalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cutesycalculator to javafx.fxml;
    exports com.example.cutesycalculator;
}