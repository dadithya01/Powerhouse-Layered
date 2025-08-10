package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.bo.BOFactory;
import edu.ijse.powerhouse.bo.custom.UserTypeBO;
import edu.ijse.powerhouse.dto.UserTypeDTO;
import edu.ijse.powerhouse.view.tdm.UserTypeTM;
import edu.ijse.powerhouse.util.AnimationsUtil;

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

public class UserTypesController implements Initializable {

    public Label lblUserTypeId;
    public TextField txtType;
    public Label lblMain;
    public TableView<UserTypeTM> tblUserTypes;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;

    UserTypeBO userTypeBO = (UserTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.USERTYPE);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblUserTypes.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("user_Type_Id"));
        tblUserTypes.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
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
            showErrorAlert("Something went wrong : " + e.getMessage());
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblUserTypes.getItems().clear();
        ArrayList<UserTypeDTO> allUserTypes = userTypeBO.getAllUserTypes();
        for (UserTypeDTO userTypeDTO : allUserTypes) {
            tblUserTypes.getItems().add(
                    new UserTypeTM(
                            userTypeDTO.getUser_Type_Id(),
                            userTypeDTO.getUserTypeName()));
        }

    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtType.setText(null);

        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Something went wrong : " + e.getMessage());
        }
    }

    private boolean isValidInput() {

        if (txtType == null || txtType.getText().isBlank()) {
            showWarnerAlert(" Type cant be empty!");
            return false;
        }

        if (!txtType.getText().matches("[A-Za-z ]+")) {
            showWarnerAlert("User type must contain only letters and spaces!");
            return false;
        }

        if (txtType.getLength() > 30) {
            showWarnerAlert("User type is too long!");
            return false;
        }

        return true;
    }

    private void showWarnerAlert(String message) {
        new Alert(Alert.AlertType.WARNING, message).show();
    }

    private void showErrorAlert(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.setTitle("Success!");
        alert.setHeaderText("Success!");
        alert.show();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        String userTypeId = lblUserTypeId.getText();
        String userTypeName = txtType.getText();

        if (!isValidInput())
            return;

        try {
            if (userTypeBO.isDuplicateUserType(userTypeName)) {
                showWarnerAlert("User type already exists!");
                return;
            }
            userTypeBO.saveUserType(new UserTypeDTO(
                    userTypeId,
                    userTypeName));
            showSuccessAlert("User type saved successfully!");
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Failed to save the User type : " + e.getMessage());
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (!isValidInput())
            return;

        String userTypeId = lblUserTypeId.getText();
        String userTypeName = txtType.getText();

        try {
            if (userTypeBO.isDuplicateUserTypeForUpdate(userTypeId, userTypeName)) {
                showWarnerAlert("User type already exists!");
                return;
            }
            userTypeBO.updateUserType(new UserTypeDTO(
                    userTypeId,
                    userTypeName));
            showSuccessAlert("User type updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showSuccessAlert("Fail to update User type : " + e.getMessage());
        }
    }

    boolean existUserType(String userTypeId) throws SQLException, ClassNotFoundException {
        return userTypeBO.existUserType(userTypeId);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are You Sure ? ",
                ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String userTypeId = lblUserTypeId.getText();
            try {
                if (!existUserType(userTypeId)) {
                    showWarnerAlert("User type with ID : " + userTypeId + " does not exist.");
                }
                userTypeBO.deleteUserType(userTypeId);
                showSuccessAlert("User type deleted successfully!");
                resetPage();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorAlert("Fail to delete User type : " + e.getMessage());
            }
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private String loadNextId() throws SQLException, ClassNotFoundException {
        try {
            String newId = userTypeBO.generateNewUserTypeId();
            lblUserTypeId.setText(newId);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            showErrorAlert("Failed to generate new User Type ID : " + e.getMessage());
            throw new RuntimeException(e);
        }
        return "";
    }

    public void getData(MouseEvent mouseEvent) {
        UserTypeTM selectedItem = tblUserTypes.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblUserTypeId.setText(selectedItem.getUser_Type_Id());
            txtType.setText(selectedItem.getType());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
