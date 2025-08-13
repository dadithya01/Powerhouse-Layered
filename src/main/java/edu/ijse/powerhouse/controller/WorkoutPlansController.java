package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.bo.BOFactory;
import edu.ijse.powerhouse.bo.custom.UserTypeBO;
import edu.ijse.powerhouse.bo.custom.WorkoutPlansBO;
import edu.ijse.powerhouse.dto.UserTypeDTO;
import edu.ijse.powerhouse.dto.WorkoutPlansDTO;
import edu.ijse.powerhouse.util.AlertsUtil;
import edu.ijse.powerhouse.util.AnimationsUtil;
import edu.ijse.powerhouse.view.tdm.UserTypeTM;
import edu.ijse.powerhouse.view.tdm.WorkoutPlansTM;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class WorkoutPlansController implements Initializable {

    public Label lblMain;
    public Label lblWorkoutPlanId;
    public TextField txtName;
    public TextField txtDescription;
    public TextField txtDifficultyLevel;
    public TextField txtCreatedBy;
    public TextField txtCreatedDate;
    public TextField txtDurationWeeks;
    public TableView<WorkoutPlansTM> tblWorkoutPlanList;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;

    WorkoutPlansBO workoutPlansBO= (WorkoutPlansBO) BOFactory.getInstance().getBO(BOFactory.BOType.WORKOUTPLANS);
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblWorkoutPlanList.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("workout_plan_id"));
        tblWorkoutPlanList.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblWorkoutPlanList.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblWorkoutPlanList.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("difficulty_level"));
        tblWorkoutPlanList.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("created_by"));
        tblWorkoutPlanList.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("created_date"));
        tblWorkoutPlanList.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("duration_weeks"));
        AnimationsUtil.AnimateLabelSlideIn(lblMain);
        AnimationsUtil.AddFancyHoverAnimation(btnSave, "#27ae60", "#353b48");
        AnimationsUtil.AddFancyHoverAnimation(btnUpdate, "#2980b9", "#353b48");
        AnimationsUtil.AddFancyHoverAnimation(btnDelete, "#e74c3c", "#353b48");
        AnimationsUtil.AddFancyHoverAnimation(btnClear, "#130f40", "#353b48");
        try {
            resetPage();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblWorkoutPlanList.getItems().clear();
        ArrayList<WorkoutPlansDTO> allWorkoutPlans = workoutPlansBO.getAllWorkoutPlans();
        for (WorkoutPlansDTO workoutPlansDTO : allWorkoutPlans) {
            tblWorkoutPlanList.getItems().add(
                    new WorkoutPlansTM(
                            workoutPlansDTO.getWorkout_plan_id(),
                            workoutPlansDTO.getName(),
                            workoutPlansDTO.getDescription(),
                            workoutPlansDTO.getDifficulty_level(),
                            workoutPlansDTO.getCreated_by(),
                            workoutPlansDTO.getCreated_date(),
                            workoutPlansDTO.getDuration_weeks()
                    )
            );
        }
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtName.setText(null);
            txtDescription.setText(null);
            txtDifficultyLevel.setText(null);
            txtCreatedBy.setText(null);
            txtCreatedDate.setText(null);
            txtDurationWeeks.setText(null);


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String workoutPlanId = lblWorkoutPlanId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        String difficultyLevel = txtDifficultyLevel.getText();
        String createdBy = txtCreatedBy.getText();
        String createdDate = txtCreatedDate.getText();
        String durationWeeks = txtDurationWeeks.getText();

        try {
            workoutPlansBO.saveWorkoutPlans(new WorkoutPlansDTO(
                    workoutPlanId,
                    name,
                    description,
                    difficultyLevel,
                    createdBy,
                    createdDate,
                    durationWeeks
            ));
            AlertsUtil.showSuccessAlert("Workout plan saved successfully!");
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String workoutPlanId = lblWorkoutPlanId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        String difficultyLevel = txtDifficultyLevel.getText();
        String createdBy = txtCreatedBy.getText();
        String createdDate = txtCreatedDate.getText();
        String durationWeeks = txtDurationWeeks.getText();

        try {
            workoutPlansBO.updateWorkoutPlans(new WorkoutPlansDTO(
                    workoutPlanId,
                    name,
                    description,
                    difficultyLevel,
                    createdBy,
                    createdDate,
                    durationWeeks
            ));
            AlertsUtil.showSuccessAlert("Workout plan updated successfully!");
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are You Sure ? ",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            String workoutPlanId = lblWorkoutPlanId.getText();
            try {
                workoutPlansBO.deleteWorkoutPlans(workoutPlanId);
                AlertsUtil.showSuccessAlert("Workout plan deleted successfully!");
                resetPage();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
            }
        }
    }


    public void btnClearOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = workoutPlansBO.generateNewWorkoutPlanId();
        lblWorkoutPlanId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        WorkoutPlansTM selectedItem = tblWorkoutPlanList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblWorkoutPlanId.setText(selectedItem.getWorkout_plan_id());
            txtName.setText(selectedItem.getName());
            txtDescription.setText(selectedItem.getDescription());
            txtDifficultyLevel.setText(selectedItem.getDifficulty_level());
            txtCreatedBy.setText(selectedItem.getCreated_by());
            txtCreatedDate.setText(selectedItem.getCreated_date());
            txtDurationWeeks.setText(selectedItem.getDuration_weeks());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
