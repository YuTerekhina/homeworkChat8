module ru_gb {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru_gb to javafx.fxml;
    exports ru_gb;
}