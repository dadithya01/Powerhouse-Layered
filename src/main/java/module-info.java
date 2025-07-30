module edu.ijse.powerhouse {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.ijse.powerhouse to javafx.fxml;
    exports edu.ijse.powerhouse;
}