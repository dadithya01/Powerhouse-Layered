package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.util.AnimationsUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardOAController implements Initializable {

    @FXML
    private Pane sidePane;

    @FXML
    private Label lblIcon;

    @FXML
    private Button btnLogout;
    @FXML
    private AnchorPane ancDashBoardO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnimationsUtil.AddFancyHoverAnimation(btnLogout, "#2f3640", "#353b48");
        AnimationsUtil.AnimateLabelSlideIn(lblIcon);
    }

    @FXML
    void btnLogoutOnAction() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            ancDashBoardO.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
            ancDashBoardO.getChildren().add(load);
        }
    }

    @FXML
    void btnUserListOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/User.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnUserTypesOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/UserTypes.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnEmployeeListOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Employee.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnTrainerListOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Trainers.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnMemberListOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Member.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnAttendanceOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Attendance.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnDietPlansOnAction(ActionEvent event) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/DietPlans.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnMealListOnAction(ActionEvent event) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/MealList.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnPaymentRecordManageOnAction(ActionEvent event) throws IOException {
        // sidePane.getChildren().clear();
        // AnchorPane load =
        // FXMLLoader.load(getClass().getResource("/view/Settings.fxml"));
        // sidePane.getChildren().add(load);
    }

    @FXML
    void btnPaymentPlan(ActionEvent event) throws IOException {
        // sidePane.getChildren().clear();
        // AnchorPane load =
        // FXMLLoader.load(getClass().getResource("/view/Settings.fxml"));
        // sidePane.getChildren().add(load);
    }

    @FXML
    void btnEquipmentManagementOnAction(ActionEvent event) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/EquipmentManagement.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnAssignedDietPlansOnAction(ActionEvent event) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/AssignDietPlans.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnAssignedworkoutOnAction(ActionEvent event) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/AssignWorkoutPlans.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnWorkoutPlanListOnAction(ActionEvent event) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/WorkoutPlans.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnExercisesLibraryOnAction(ActionEvent event) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/ExercisesLibrary.fxml"));
        sidePane.getChildren().add(load);
    }
}