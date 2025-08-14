package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.bo.custom.DietPlansBO;
import edu.ijse.powerhouse.dto.DietPlansDTO;
import edu.ijse.powerhouse.dto.EmployeeDTO;
import edu.ijse.powerhouse.util.AlertsUtil;
import edu.ijse.powerhouse.util.AnimationsUtil;
import edu.ijse.powerhouse.view.tdm.DietPlansTM;
import edu.ijse.powerhouse.view.tdm.EmployeeTM;
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

public class DietPlansController implements Initializable {

    public Label lblMain;
    public Label lblDietPlanId;
    public TextField txtName;
    public TextField txtDescription;
    public TextField txtCreatedBy;
    public TextField txtCreatedDate;
    public TextField txtCalorieTarget;
    public TextField txtProteinTarget;
    public TextField txtCarbsTarget;
    public TextField txtFatTarget;
    public TextField txtNote;
    public TableView<DietPlansTM> tblDietPlan;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;

    DietPlansBO dietPlansBO= (DietPlansBO) edu.ijse.powerhouse.bo.BOFactory.getInstance().getBO(edu.ijse.powerhouse.bo.BOFactory.BOType.DIETPLANS);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblDietPlan.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("diet_plan_id"));
        tblDietPlan.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblDietPlan.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblDietPlan.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("created_by"));
        tblDietPlan.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("created_date"));
        tblDietPlan.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("calorie_target"));
        tblDietPlan.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("protein_target"));
        tblDietPlan.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("carbs_target"));
        tblDietPlan.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("fat_target"));
        tblDietPlan.getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("notes"));
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
        tblDietPlan.getItems().clear();
        ArrayList<DietPlansDTO> allDietPlans = dietPlansBO.getAllDietPlans();
        for (DietPlansDTO dietPlansDTO : allDietPlans) {
            tblDietPlan.getItems().add(
                    new DietPlansTM(
                            dietPlansDTO.getDiet_plan_id(),
                            dietPlansDTO.getName(),
                            dietPlansDTO.getDescription(),
                            dietPlansDTO.getCreated_by(),
                            dietPlansDTO.getCreated_date(),
                            dietPlansDTO.getCalorie_target(),
                            dietPlansDTO.getProtein_target(),
                            dietPlansDTO.getCarbs_target(),
                            dietPlansDTO.getFat_target(),
                            dietPlansDTO.getNotes()
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

            txtName.setText(null);
            txtDescription.setText(null);
            txtCreatedBy.setText(null);
            txtCreatedDate.setText(null);
            txtCalorieTarget.setText(null);
            txtProteinTarget.setText(null);
            txtCarbsTarget.setText(null);
            txtFatTarget.setText(null);
            txtNote.setText(null);


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private boolean isValidInput() {
        StringBuilder errors = new StringBuilder();

        if (txtName.getText().isEmpty()) errors.append("Name is required.\n");
        if (txtDescription.getText().isEmpty()) errors.append("Description is required.\n");
        if (txtCreatedBy.getText().isEmpty()) errors.append("Created By is required.\n");
        if (txtCreatedDate.getText().isEmpty()) {
            errors.append("Created Date is required.\n");
        } else if (!txtCreatedDate.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
            errors.append("Created Date must be in YYYY-MM-DD format.\n");
        }

        if (!isValidNumber(txtCalorieTarget.getText())) errors.append("Calorie Target must be a valid number.\n");
        if (!isValidNumber(txtProteinTarget.getText())) errors.append("Protein Target must be a valid number.\n");
        if (!isValidNumber(txtCarbsTarget.getText())) errors.append("Carbs Target must be a valid number.\n");
        if (!isValidNumber(txtFatTarget.getText())) errors.append("Fat Target must be a valid number.\n");

        if (errors.length() > 0) {
            new Alert(Alert.AlertType.WARNING, errors.toString()).show();
            return false;
        }

        return true;
    }

    private boolean isValidNumber(String text) {
        if (text == null || text.isEmpty()) return false;
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {

        if (!isValidInput()) return;
        String dietPlanId = lblDietPlanId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        String createdBy = txtCreatedBy.getText();
        String createdDate = txtCreatedDate.getText();
        String calorieTarget = txtCalorieTarget.getText();
        String proteinTarget = txtProteinTarget.getText();
        String carbsTarget = txtCarbsTarget.getText();
        String fatTarget = txtFatTarget.getText();
        String notes = txtNote.getText();

        try {
            if (dietPlansBO.isDuplicateDietPlan(name)){
                AlertsUtil.showWarnerAlert("Duplicate Diet plan with this name already exists.");
                return;
            }
            dietPlansBO.saveDietPlans(new DietPlansDTO(
                    dietPlanId,
                    name,
                    description,
                    createdBy,
                    createdDate,
                    calorieTarget,
                    proteinTarget,
                    carbsTarget,
                    fatTarget,
                    notes
            ));
            AlertsUtil.showSuccessAlert("Diet plan saved successfully.");
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        if (!isValidInput()) return;
        String dietPlanId = lblDietPlanId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        String createdBy = txtCreatedBy.getText();
        String createdDate = txtCreatedDate.getText();
        String calorieTarget = txtCalorieTarget.getText();
        String proteinTarget = txtProteinTarget.getText();
        String carbsTarget = txtCarbsTarget.getText();
        String fatTarget = txtFatTarget.getText();
        String notes = txtNote.getText();

        try {
            if (dietPlansBO.isDuplicateDietPlanForUpdate(dietPlanId, name)){
                AlertsUtil.showWarnerAlert("Duplicate Diet plan with this name already exists.");
                return;
            }
            dietPlansBO.updateDietPlans(new DietPlansDTO(
                    dietPlanId,
                    name,
                    description,
                    createdBy,
                    createdDate,
                    calorieTarget,
                    proteinTarget,
                    carbsTarget,
                    fatTarget,
                    notes
            ));
            AlertsUtil.showSuccessAlert("Diet plan updated successfully.");
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
            String dietPanId = lblDietPlanId.getText();
            try {
                if (!dietPlansBO.existDietPlans(dietPanId)) {
                    AlertsUtil.showWarnerAlert("No Diet plan found with this ID.");
                    return;
                }
                dietPlansBO.deleteDietPlans(dietPanId);
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
        String nextId = dietPlansBO.generateNewDietPlanId();
        lblDietPlanId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        DietPlansTM selectedItem = tblDietPlan.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblDietPlanId.setText(selectedItem.getDiet_plan_id());
            txtName.setText(selectedItem.getName());
            txtDescription.setText(selectedItem.getDescription());
            txtCreatedBy.setText(selectedItem.getCreated_by());
            txtCreatedDate.setText(selectedItem.getCreated_date());
            txtCalorieTarget.setText(selectedItem.getCalorie_target());
            txtProteinTarget.setText(selectedItem.getProtein_target());
            txtCarbsTarget.setText(selectedItem.getCarbs_target());
            txtFatTarget.setText(selectedItem.getFat_target());
            txtNote.setText(selectedItem.getNotes());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
