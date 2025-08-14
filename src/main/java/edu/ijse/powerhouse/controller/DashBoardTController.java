package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.util.AnimationsUtil;
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

public class DashBoardTController implements Initializable {

    @FXML
    private AnchorPane ancDashBoardT;

    @FXML
    private Label lblIcon;

    @FXML
    private Pane sidePane;

    @FXML
    private Button btnLogout;

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
            ancDashBoardT.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
            ancDashBoardT.getChildren().add(load);
        }
    }

    @FXML
    void btnMemberListOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/MemberList.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnAttendanceOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Attendance.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnAssignedworkoutOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/AssignWorkoutPlans.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnAssignedDietPlansOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/AssignDietPlans.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnWorkoutPlanListOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/WorkoutPlanList.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnExercisesLibraryOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/ExercisesLibrary.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnDietPlansOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/DietPlan.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnMealListOnAction() throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/MealList.fxml"));
        sidePane.getChildren().add(load);
    }
}
