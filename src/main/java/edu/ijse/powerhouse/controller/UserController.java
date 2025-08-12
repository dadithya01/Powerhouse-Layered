package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.util.AlertsUtil;
import edu.ijse.powerhouse.bo.BOFactory;
import edu.ijse.powerhouse.bo.custom.UserBO;
import edu.ijse.powerhouse.dto.UserDTO;
import edu.ijse.powerhouse.view.tdm.UserTM;
import edu.ijse.powerhouse.util.AnimationsUtil;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class UserController implements Initializable {

    public Label lblUserId;
    public TextField txtName;
    public TextField txtPhone;
    public TextField txtEmail;
    public TextField txtUserName;
    public TextField txtPassword;
    public TextField txtUserTypeId;
    public TextField txtRegistrationDate;
    public ComboBox<String> cmbStatus;
    public Label lblMain;
    public TableView<UserTM> tblUsers;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblUsers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("userId"));
        tblUsers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblUsers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("phone"));
        tblUsers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("email"));
        tblUsers.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("userName"));
        tblUsers.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("password"));
        tblUsers.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("userTypeId"));
        tblUsers.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        tblUsers.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("status"));
        cmbStatus.getItems().clear();
        cmbStatus.getItems().addAll("Active", "Inactive");
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
            AlertsUtil.showErrorAlert("Something went wrong : " + e.getMessage());
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblUsers.getItems().clear();
        ArrayList<UserDTO> allUsers = userBO.getAllUsers();
        for (UserDTO userDTO : allUsers) {
            tblUsers.getItems().add(
                    new UserTM(
                            userDTO.getUserId(),
                            userDTO.getName(),
                            userDTO.getPhone(),
                            userDTO.getEmail(),
                            userDTO.getUserName(),
                            userDTO.getPassword(),
                            userDTO.getUserTypeId(),
                            userDTO.getRegistrationDate(),
                            userDTO.getStatus()));
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
            txtPhone.setText(null);
            txtEmail.setText(null);
            txtUserName.setText(null);
            txtPassword.setText(null);
            txtUserTypeId.setText(null);
            txtRegistrationDate.setText(null);
            cmbStatus.setValue(null);

        } catch (Exception e) {
            e.printStackTrace();
            AlertsUtil.showErrorAlert("Something went wrong : " + e.getMessage());
        }
    }

    private boolean isValidInput() {
        if (txtName.getText().isBlank() || txtName.getLength() > 50) {
            AlertsUtil.showWarnerAlert("Name is required and must be under 50 characters.");
            return false;
        }

        if (!txtPhone.getText().matches("\\d{10}")) {
            AlertsUtil.showWarnerAlert("Phone number must be 10 digits.");
            return false;
        }

        if (!txtEmail.getText().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            AlertsUtil.showWarnerAlert("Invalid email format.");
            return false;
        }

        if (txtUserName.getText().isBlank() || txtUserName.getLength() > 30) {
            AlertsUtil.showWarnerAlert("Username is required and must be under 30 characters.");
            return false;
        }

        if (txtPassword.getText().isBlank() || txtPassword.getLength() < 6) {
            AlertsUtil.showWarnerAlert("Password must be at least 6 characters long.");
            return false;
        }

        if (txtUserTypeId.getText().isBlank() || txtUserTypeId.getLength() > 10) {
            AlertsUtil.showWarnerAlert("User Type ID must be under 10 characters.");
            return false;
        }

        if (!txtRegistrationDate.getText().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            AlertsUtil.showWarnerAlert("Registration date must be in YYYY-MM-DD format.");
            return false;
        }

        return true;
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        String email = txtEmail.getText();
        String userName = txtUserName.getText();
        String phone = txtPhone.getText();
        String userId = lblUserId.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();
        String userTypeId = txtUserTypeId.getText();
        String registrationDate = txtRegistrationDate.getText();
        String status = cmbStatus.getValue();

        if (!isValidInput())
            return;

        try {
            if (userBO.isDuplicateUser(email, userName, phone)) {
                AlertsUtil.showWarnerAlert("Duplicate user detected (Email, Username or Phone already exists).");
                return;
            }
            userBO.saveUser(new UserDTO(
                    userId, name, phone, email, userName,
                    password, userTypeId, registrationDate, status));
            AlertsUtil.showSuccessAlert("User saved successfully!");
            resetPage();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            AlertsUtil.showErrorAlert("Failed to save the User : " + e.getMessage());
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {

        if (!isValidInput())
            return;

        String userId = lblUserId.getText();
        String userName = txtName.getText();
        String userContact = txtPhone.getText();
        String userEmail = txtEmail.getText();
        String userUserName = txtUserName.getText();
        String userPassword = txtPassword.getText();
        String userTypeId = txtUserTypeId.getText();
        String userRegistrationDate = txtRegistrationDate.getText();
        String userStatus = cmbStatus.getValue();

        try {
            if (userBO.isDuplicateUserForUpdate(userId, userEmail, userName, userContact)) {
                AlertsUtil.showWarnerAlert("Duplicate email, username, or phone number found for another user.");
                return;
            }

            userBO.updateUser(new UserDTO(
                    userId, userName, userContact, userEmail, userUserName,
                    userPassword, userTypeId, userRegistrationDate, userStatus));
            AlertsUtil.showSuccessAlert("User updated successfully!");
            resetPage();

        } catch (Exception e) {
            e.printStackTrace();
            AlertsUtil.showErrorAlert("Failed to update User : " + e.getMessage());
        }
    }

    boolean existUser(String userId) throws SQLException, ClassNotFoundException {
        return userBO.existUser(userId);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are You Sure ? ",
                ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String userId = tblUsers.getSelectionModel().getSelectedItem().getUserId();
            try {
                if (!existUser(userId)) {
                    AlertsUtil.showWarnerAlert("User with ID : " + userId + " does not exist.");
                }
                userBO.deleteUser(userId);
                AlertsUtil.showSuccessAlert("User deleted successfully");
                resetPage();
            } catch (Exception e) {
                e.printStackTrace();
                AlertsUtil.showErrorAlert("Failed to delete User : " + e.getMessage());
            }
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private String loadNextId() throws SQLException, ClassNotFoundException {
        try {
            String newId = userBO.generateNewUserId();
            lblUserId.setText(newId);
        } catch (SQLException | ClassNotFoundException e) {
            AlertsUtil.showErrorAlert("Failed to generate a new user ID : " + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    public void getData(MouseEvent mouseEvent) {
        UserTM selectedItem = tblUsers.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblUserId.setText(selectedItem.getUserId());
            txtName.setText(selectedItem.getName());
            txtPhone.setText(selectedItem.getPhone());
            txtEmail.setText(selectedItem.getEmail());
            txtUserName.setText(selectedItem.getUserName());
            txtPassword.setText(selectedItem.getPassword());
            txtUserTypeId.setText(selectedItem.getUserTypeId());
            txtRegistrationDate.setText(selectedItem.getRegistrationDate());
            cmbStatus.setValue(selectedItem.getStatus());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
