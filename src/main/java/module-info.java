module edu.ijse.powerhouse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;

    opens edu.ijse.powerhouse.controller to javafx.fxml;
    opens edu.ijse.powerhouse.view.tdm to javafx.base;
    opens edu.ijse.powerhouse.dto to javafx.base;

    exports edu.ijse.powerhouse.controller;
    exports edu.ijse.powerhouse.view.tdm;
    exports edu.ijse.powerhouse.dto;

    opens edu.ijse.powerhouse to javafx.fxml;

    exports edu.ijse.powerhouse;
    exports edu.ijse.powerhouse.util;
    opens edu.ijse.powerhouse.util to javafx.fxml;
}