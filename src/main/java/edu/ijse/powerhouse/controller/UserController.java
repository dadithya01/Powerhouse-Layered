package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.bo.BOFactory;
import edu.ijse.powerhouse.bo.custom.UserBO;
import edu.ijse.powerhouse.dao.custom.impl.UserDAOImpl;
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
    public TextField txtStatus;
    public Label lblMain;
    public TableView<UserTM> tblUsers;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);
    UserDAOImpl userDAOImpl = new UserDAOImpl();

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
        tblUsers.getItems().clear();
            ArrayList<UserDTO> allUsers=userBO.getAllUsers();
            for (UserDTO userDTO : allUsers){
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
                                userDTO.getStatus()
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
            txtPhone.setText(null);
            txtEmail.setText(null);
            txtUserName.setText(null);
            txtPassword.setText(null);
            txtUserTypeId.setText(null);
            txtRegistrationDate.setText(null);
            txtStatus.setText(null);


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private boolean isValidInput() {
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String userName = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();
        String userTypeId = txtUserTypeId.getText().trim();
        String registrationDate = txtRegistrationDate.getText().trim();
        String status = txtStatus.getText().trim();

        if (name.isEmpty() || name.length() > 50) {
            new Alert(Alert.AlertType.WARNING, "Name is required and must be under 50 characters.").show();
            return false;
        }

        if (!phone.matches("\\d{10}")) {
            new Alert(Alert.AlertType.WARNING, "Phone number must be 10 digits.").show();
            return false;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            new Alert(Alert.AlertType.WARNING, "Invalid email format.").show();
            return false;
        }

        if (userName.isEmpty() || userName.length() > 30) {
            new Alert(Alert.AlertType.WARNING, "Username is required and must be under 30 characters.").show();
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            new Alert(Alert.AlertType.WARNING, "Password must be at least 6 characters long.").show();
            return false;
        }

        if (userTypeId.isEmpty() || userTypeId.length() > 10) {
            new Alert(Alert.AlertType.WARNING, "User Type ID must be under 10 characters.").show();
            return false;
        }

        if (!registrationDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            new Alert(Alert.AlertType.WARNING, "Registration date must be in YYYY-MM-DD format.").show();
            return false;
        }

        if (status.isEmpty() || (!status.equalsIgnoreCase("Active") && !status.equalsIgnoreCase("Inactive"))) {
            new Alert(Alert.AlertType.WARNING, "Status must be either 'Active' or 'Inactive'.").show();
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
        String status = txtStatus.getText();

        if (!isValidInput()) return;

        try {
            if (userDAOImpl.isDuplicateUser(email, userName, phone)) {
                new Alert(Alert.AlertType.WARNING, "Duplicate user detected (Email, Username or Phone already exists).").show();
                return;
            }
            userBO.saveUser(new UserDTO(
                    userId, name, phone, email, userName,
                    password, userTypeId, registrationDate, status
            ));
            new Alert(Alert.AlertType.INFORMATION, "User saved successfully!").show();
            resetPage();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save the User " + e.getMessage()).show();
        }
    }

        public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {

        if (!isValidInput()) return;

        String userId = lblUserId.getText();
        String userName = txtName.getText();
        String userContact = txtPhone.getText();
        String userEmail = txtEmail.getText();
        String userUserName = txtUserName.getText();
        String userPassword = txtPassword.getText();
        String userTypeId = txtUserTypeId.getText();
        String userRegistrationDate = txtRegistrationDate.getText();
        String userStatus = txtStatus.getText();

        try {
            if (userDAOImpl.isDuplicateUserForUpdate(userId, userEmail, userName, userContact)) {
                new Alert(Alert.AlertType.WARNING, "Duplicate email, username, or phone number found for another user.").show();
                return;
            }

            userBO.updateUser(new UserDTO(
                    userId, userName, userContact, userEmail, userUserName,
                    userPassword, userTypeId, userRegistrationDate, userStatus
            ));
            new Alert(Alert.AlertType.INFORMATION, "User updated successfully!").show();
            resetPage();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update User").show();
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
                ButtonType.NO
        );
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            String userId = tblUsers.getSelectionModel().getSelectedItem().getUserId();
            try {
                if (!existUser(userId)){
                    new Alert(Alert.AlertType.ERROR,"There is no such user associated with the id " + userId).show();
                }
                userBO.deleteUser(userId);
                new Alert(Alert.AlertType.INFORMATION,"User deleted successfully").show();
                resetPage();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to delete User").show();
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
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new user ID: " + e.getMessage()).show();
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
            txtStatus.setText(selectedItem.getStatus());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
