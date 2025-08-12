package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.bo.BOFactory;
import edu.ijse.powerhouse.bo.custom.AssignWorkoutPlanBO;
import edu.ijse.powerhouse.dto.AssignWorkoutPlansDTO;
import edu.ijse.powerhouse.dto.AttendanceDTO;
import edu.ijse.powerhouse.util.AlertsUtil;
import edu.ijse.powerhouse.util.AnimationsUtil;
import edu.ijse.powerhouse.view.tdm.AssignWorkoutPlansTM;
import edu.ijse.powerhouse.view.tdm.AttendanceTM;
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

public class AssignWorkoutPlansController implements Initializable {

    public Label lblMain;
    public Label lblWorkoutPlanId;
    public TextField txtMemberId;
    public TextField txtAssignedDate;
    public TextField txtEndDate;
    public TextField txtProgress;
    public TextField txtNotes;
    public TextField txtAssignedBy;
    public TableView<AssignWorkoutPlansTM> tblAssignWorkout;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;

    AssignWorkoutPlanBO assignWorkoutPlanBO = (AssignWorkoutPlanBO) BOFactory.getInstance().getBO(BOFactory.BOType.ASSIGNWORKOUTPLANS);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblAssignWorkout.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("member_id"));
        tblAssignWorkout.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("workout_plan_id"));
        tblAssignWorkout.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("assigned_date"));
        tblAssignWorkout.getColumns().get(3).setCellValueFactory(new  PropertyValueFactory<>("end_date"));
        tblAssignWorkout.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("progress"));
        tblAssignWorkout.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("notes"));
        tblAssignWorkout.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("assigned_by"));
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
        tblAssignWorkout.getItems().clear();
        ArrayList<AssignWorkoutPlansDTO> allAssignmentWorkoutPlans = assignWorkoutPlanBO.getAllAssignWorkoutPlans();
        for (AssignWorkoutPlansDTO assignWorkoutPlansDTO : allAssignmentWorkoutPlans) {
            tblAssignWorkout.getItems().add(
                    new AssignWorkoutPlansTM(
                            assignWorkoutPlansDTO.getMember_id(),
                            assignWorkoutPlansDTO.getWorkout_plan_id(),
                            assignWorkoutPlansDTO.getAssigned_date(),
                            assignWorkoutPlansDTO.getEnd_date(),
                            assignWorkoutPlansDTO.getProgress(),
                            assignWorkoutPlansDTO.getNotes(),
                            assignWorkoutPlansDTO.getAssigned_by()
                    ));
        }
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtMemberId.setText(null);
            txtAssignedDate.setText(null);
            txtEndDate.setText(null);
            txtProgress.setText(null);
            txtNotes.setText(null);
            txtAssignedBy.setText(null);


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String workoutPlanId = lblWorkoutPlanId.getText();
        String memberId = txtMemberId.getText();
        String assignedDate = txtAssignedDate.getText();
        String endDate = txtEndDate.getText();
        String progress = txtProgress.getText();
        String notes = txtNotes.getText();
        String assignedBy = txtAssignedBy.getText();

        try {
            if (assignWorkoutPlanBO.isDuplicateAssignWorkoutPlans(memberId)){
                AlertsUtil.showWarnerAlert("Duplicate Assigned workout  plans for this member");
                return;
            }
            assignWorkoutPlanBO.saveAssignWorkoutPlans(new AssignWorkoutPlansDTO(
                    memberId,
                    workoutPlanId,
                    assignedDate,
                    endDate,
                    progress,
                    notes,
                    assignedBy
            ));
            AlertsUtil.showSuccessAlert("assigned workout plans saved successfully");
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String workoutPlanId = lblWorkoutPlanId.getText();
        String memberId = txtMemberId.getText();
        String assignedDate = txtAssignedDate.getText();
        String endDate = txtEndDate.getText();
        String progress = txtProgress.getText();
        String notes = txtNotes.getText();
        String assignedBy = txtAssignedBy.getText();

        try {
            if (assignWorkoutPlanBO.isDuplicateAssignWorkoutPlansForUpdate(memberId, workoutPlanId)){
                AlertsUtil.showWarnerAlert("Duplicate Assigned workout plans for this member");
                return;
            }
            assignWorkoutPlanBO.updateAssignWorkoutPlans(new AssignWorkoutPlansDTO(
                    memberId,
                    workoutPlanId,
                    assignedDate,
                    endDate,
                    progress,
                    notes,
                    assignedBy
            ));
            AlertsUtil.showSuccessAlert("Assigned workout plans updated successfully");
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }
    public boolean existAttendance(String assignedWorkoutPlanId) throws SQLException, ClassNotFoundException {
        return assignWorkoutPlanBO.existAssignWorkoutPlans(assignedWorkoutPlanId);
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
            String assignWorkoutPlan = lblWorkoutPlanId.getText();
            try {
                if (!existAttendance( assignWorkoutPlan)) {
                    AlertsUtil.showWarnerAlert("No such assigned workout plan exists");
                    return;
                }
                assignWorkoutPlanBO.deleteAssignWorkoutPlans(assignWorkoutPlan);
                AlertsUtil.showSuccessAlert("Assigned workout plan deleted successfully");
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
        String nextId = assignWorkoutPlanBO.generateNewAssignWorkoutPlansId();
        lblWorkoutPlanId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        AssignWorkoutPlansTM selectedItem = tblAssignWorkout.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblWorkoutPlanId.setText(selectedItem.getWorkout_plan_id());
            txtMemberId.setText(selectedItem.getMember_id());
            txtAssignedDate.setText(selectedItem.getAssigned_date());
            txtEndDate.setText(selectedItem.getEnd_date());
            txtProgress.setText(selectedItem.getProgress());
            txtNotes.setText(selectedItem.getNotes());
            txtAssignedBy.setText(selectedItem.getAssigned_by());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
