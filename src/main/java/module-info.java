module ru.knyazevvb.networkchat {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.knyazevvb.networkchat to javafx.fxml;
    exports ru.knyazevvb.networkchat;
}