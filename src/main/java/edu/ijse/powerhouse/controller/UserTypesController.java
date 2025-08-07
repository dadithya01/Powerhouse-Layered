package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.bo.BOFactory;
import edu.ijse.powerhouse.bo.custom.UserTypeBO;
import edu.ijse.powerhouse.dao.custom.impl.UserTypeDAOImpl;
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
    UserTypeDAOImpl userTypeDAOImpl = new UserTypeDAOImpl();

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
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblUserTypes.getItems().clear();
        ArrayList<UserTypeDTO> allUserTypes = userTypeBO.getAllUserTypes();
        for (UserTypeDTO userTypeDTO: allUserTypes){
            tblUserTypes.getItems().add(
                    new UserTypeTM(
                            userTypeDTO.getUser_Type_Id(),
                            userTypeDTO.getUserTypeName()
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

            txtType.setText(null);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private boolean isValidInput() {
        String type = txtType.getText();

        if (type == null || type.trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "User type cannot be empty!").show();
            return false;
        }

        if (!type.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.WARNING, "User type must contain only letters and spaces!").show();
            return false;
        }

        if (type.length() > 30) {
            new Alert(Alert.AlertType.WARNING, "User type is too long!").show();
            return false;
        }

        return true;
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {

        String userTypeId = lblUserTypeId.getText();
        String userTypeName = txtType.getText();

        if (!isValidInput()) return;

        try {
            if (userTypeDAOImpl.isDuplicateUserType( userTypeName)) {
                new Alert(Alert.AlertType.ERROR, "User type already exists!").show();
                return;
            }
            userTypeBO.saveUserType(new UserTypeDTO(
                    userTypeId,
                    userTypeName
            ));
            new Alert(Alert.AlertType.INFORMATION, "User type saved successfully!").show();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save the User type " + e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (!isValidInput()) return;

        String userTypeId = lblUserTypeId.getText();
        String userTypeName = txtType.getText();


        try {
            if (userTypeDAOImpl.isDuplicateUserTypeForUpdate(userTypeId, userTypeName)) {
                new Alert(Alert.AlertType.ERROR, "User type already exists!").show();
                return;
            }
            userTypeBO.updateUserType(new UserTypeDTO(
                    userTypeId,
                    userTypeName
            ));
            new Alert(Alert.AlertType.INFORMATION, "User type updated successfully!").show();
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to update User type").show();
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
                ButtonType.NO
        );
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            String userTypeId = lblUserTypeId.getText();
            try {
                if (!existUserType(userTypeId)){
                    new Alert(Alert.AlertType.ERROR,"There is no such user type associated with the id " + userTypeId).show();
                }
                userTypeBO.deleteUserType(userTypeId);
                new Alert(Alert.AlertType.INFORMATION, "User type deleted successfully!").show();
                resetPage();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Fail to delete User type").show();
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
        } catch (SQLException| ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate new User Type ID: " +e.getMessage()).show();
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
