package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.bo.BOFactory;
import edu.ijse.powerhouse.bo.custom.AssignDietPlansBO;
import edu.ijse.powerhouse.dto.AssignDietPlansDTO;
import edu.ijse.powerhouse.util.AlertsUtil;
import edu.ijse.powerhouse.util.AnimationsUtil;
import edu.ijse.powerhouse.view.tdm.AssignDietPlansTM;
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

public class AssignDietPlansController implements Initializable {

    public Label lblMain;
    public Label lblDietPlanId;
    public TextField txtMemberId;
    public TextField txtAssignedDate;
    public TextField txtEndDate;
    public TextField txtNotes;
    public TextField txtAssignedBy;
    public TableView<AssignDietPlansTM> tblAssignDiet;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;

    AssignDietPlansBO assignDietPlansBO =(AssignDietPlansBO) BOFactory.getInstance().getBO(BOFactory.BOType.ASSIGNDIETPLANS);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblAssignDiet.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("member_id"));
        tblAssignDiet.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("diet_plan_id"));
        tblAssignDiet.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("assigned_date"));
        tblAssignDiet.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("end_date"));
        tblAssignDiet.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("notes"));
        tblAssignDiet.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("assigned_by"));
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
        tblAssignDiet.getItems().clear();
        ArrayList<AssignDietPlansDTO> allAssignmentDietPlans = assignDietPlansBO.getAllAssignedDietPlans();
        for (AssignDietPlansDTO assignDietPlansDTO : allAssignmentDietPlans) {
            tblAssignDiet.getItems().add(
                    new AssignDietPlansTM(
                            assignDietPlansDTO.getMember_id(),
                            assignDietPlansDTO.getDiet_plan_id(),
                            assignDietPlansDTO.getAssigned_date(),
                            assignDietPlansDTO.getEnd_date(),
                            assignDietPlansDTO.getNotes(),
                            assignDietPlansDTO.getAssigned_by()
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
            txtNotes.setText(null);
            txtAssignedBy.setText(null);


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String dietPlanId = lblDietPlanId.getText();
        String memberId = txtMemberId.getText();
        String assignedDate = txtAssignedDate.getText();
        String endDate = txtEndDate.getText();
        String notes = txtNotes.getText();
        String assignedBy = txtAssignedBy.getText();

        try {
            if (assignDietPlansBO.isDietPlanAssigned(memberId)){
                AlertsUtil.showWarnerAlert("This member already has a diet plan assigned.");
                return;
            }
            assignDietPlansBO.assignDietPlans(new AssignDietPlansDTO(
                    dietPlanId,
                    memberId,
                    assignedDate,
                    endDate,
                    notes,
                    assignedBy
            ));
            AlertsUtil.showSuccessAlert("Diet plan assigned successfully.");
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String dietPlanId = lblDietPlanId.getText();
        String memberId = txtMemberId.getText();
        String assignedDate = txtAssignedDate.getText();
        String endDate = txtEndDate.getText();
        String notes = txtNotes.getText();
        String assignedBy = txtAssignedBy.getText();
        try {
            if (assignDietPlansBO.isDietPlanAssignedForUpdate(dietPlanId, memberId)){
                AlertsUtil.showWarnerAlert("This member already has a diet plan assigned.");
                return;
            }
            assignDietPlansBO.updateAssignedDietPlans(new AssignDietPlansDTO(
                    dietPlanId,
                    memberId,
                    assignedDate,
                    endDate,
                    notes,
                    assignedBy
            ));
            new Alert(Alert.AlertType.INFORMATION,"Updated").show();
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    public boolean existAssignDietPlan(String dietPlanId) throws SQLException, ClassNotFoundException {
        return assignDietPlansBO.existAssignDietPlans(dietPlanId);
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
            String assignDietPlan = lblDietPlanId.getText();
            try {
                if (!existAssignDietPlan(assignDietPlan)) {
                    AlertsUtil.showWarnerAlert("This diet plan does not exist.");
                    return;
                }
                assignDietPlansBO.deleteAssignDietPlans(assignDietPlan);
                AlertsUtil.showSuccessAlert("Diet plan deleted successfully.");
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
        String nextId = assignDietPlansBO.generateNewAssignDietPlanId();
        lblDietPlanId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        AssignDietPlansTM selectedItem = tblAssignDiet.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblDietPlanId.setText(selectedItem.getDiet_plan_id());
            txtMemberId.setText(selectedItem.getMember_id());
            txtAssignedDate.setText(selectedItem.getAssigned_date());
            txtEndDate.setText(selectedItem.getEnd_date());
            txtNotes.setText(selectedItem.getNotes());
            txtAssignedBy.setText(selectedItem.getAssigned_by());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
