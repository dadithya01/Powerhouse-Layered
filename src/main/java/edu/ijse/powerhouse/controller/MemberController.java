package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.util.AlertsUtil;
import edu.ijse.powerhouse.bo.BOFactory;
import edu.ijse.powerhouse.bo.custom.MemberBO;
import edu.ijse.powerhouse.dto.MemberDTO;
import edu.ijse.powerhouse.view.tdm.MemberTM;
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

public class MemberController implements Initializable {

    public Label lblMain;
    public Label lblMemberId;
    public TextField txtName;
    public TextField txtWeight;
    public TextField txtHeight;
    public TextField txtAge;
    public TextField txtPhone;
    public TextField txtEmergencyContact;
    public TextField txtMedicalConditions;
    public TextField txtFitnessGoals;
    public TextField txtRegistrationDate;
    public TextField txtMembershipStatus;
    public TextField txtAddedBy;
    public TableView<MemberTM> tblMember;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;

    MemberBO memberBO = (MemberBO) BOFactory.getInstance().getBO(BOFactory.BOType.MEMBER);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblMember.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("member_id"));
        tblMember.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblMember.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("weight"));
        tblMember.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("height"));
        tblMember.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("age"));
        tblMember.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblMember.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("emergency_contact"));
        tblMember.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("medical_conditions"));
        tblMember.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("fitness_goals"));
        tblMember.getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("register_date"));
        tblMember.getColumns().get(10).setCellValueFactory(new PropertyValueFactory<>("membership_status"));
        tblMember.getColumns().get(11).setCellValueFactory(new PropertyValueFactory<>("added_by"));
        AnimationsUtil.AnimateLabelSlideIn(lblMain);
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
        tblMember.getItems().clear();
        ArrayList<MemberDTO> allMembers = memberBO.getAllMembers();
        for (MemberDTO memberDTO : allMembers) {
            tblMember.getItems().add(
                    new MemberTM(
                            memberDTO.getMember_id(),
                            memberDTO.getName(),
                            memberDTO.getWeight(),
                            memberDTO.getHeight(),
                            memberDTO.getAge(),
                            memberDTO.getContact(),
                            memberDTO.getEmergency_contact(),
                            memberDTO.getMedical_conditions(),
                            memberDTO.getFitness_goals(),
                            memberDTO.getRegister_date(),
                            memberDTO.getMembership_status(),
                            memberDTO.getAdded_by()));
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
            txtWeight.setText(null);
            txtHeight.setText(null);
            txtAge.setText(null);
            txtPhone.setText(null);
            txtEmergencyContact.setText(null);
            txtMedicalConditions.setText(null);
            txtFitnessGoals.setText(null);
            txtRegistrationDate.setText(null);
            txtMembershipStatus.setText(null);
            txtAddedBy.setText(null);

        } catch (Exception e) {
            e.printStackTrace();
            AlertsUtil.showErrorAlert("Something went wrong : " + e.getMessage());
        }
    }

    private boolean isValidInput() {
        if (txtName.getText().isBlank()) {
            AlertsUtil.showWarnerAlert("Name is required.");
            return false;
        }
        if (!txtWeight.getText().matches("\\d+(\\.\\d+)?")) {
            AlertsUtil.showWarnerAlert("Enter a valid weight (e.g., 70.5).");
            return false;
        }
        if (!txtHeight.getText().matches("\\d+(\\.\\d+)?")) {
            AlertsUtil.showWarnerAlert("Enter a valid height (e.g., 175.0).");
            return false;
        }
        if (!txtAge.getText().matches("\\d{1,3}")) {
            AlertsUtil.showWarnerAlert("Enter a valid age (e.g., 25).");
            return false;
        }
        if (!txtPhone.getText().matches("\\d{10,15}")) {
            AlertsUtil.showWarnerAlert("Enter a valid phone number (10-15 digits).");
            return false;
        }
        if (!txtEmergencyContact.getText().matches("\\d{10,15}")) {
            AlertsUtil.showWarnerAlert("Enter a valid emergency contact number.");
            return false;
        }
        if (txtRegistrationDate.getText().isBlank()) {
            AlertsUtil.showWarnerAlert("Registration date is required.");
            return false;
        }
        if (txtMembershipStatus.getText().isBlank()) {
            AlertsUtil.showWarnerAlert("Membership status is required.");
            return false;
        }
        if (txtAddedBy.getText().isBlank()) {
            AlertsUtil.showWarnerAlert("Added By field is required.");
            return false;
        }

        return true;
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        String memberId = lblMemberId.getText();
        String name = txtName.getText();
        Double weight = Double.valueOf(txtWeight.getText());
        Double height = Double.valueOf(txtHeight.getText());
        int age = Integer.parseInt(txtAge.getText());
        String contact = txtPhone.getText();
        String emergencyContact = txtEmergencyContact.getText();
        String medicalConditions = txtMedicalConditions.getText();
        String fitnessGoals = txtFitnessGoals.getText();
        String registrationDate = txtRegistrationDate.getText();
        String membershipStatus = txtMembershipStatus.getText();
        String addedBy = txtAddedBy.getText();

        if (!isValidInput())
            return;

        try {
            if (memberBO.isDuplicateMember(contact)) {
                AlertsUtil.showWarnerAlert("This contact number is already registered.");
                return;
            }
            memberBO.saveMember(new MemberDTO(
                    memberId,
                    name,
                    weight,
                    height,
                    age,
                    contact,
                    emergencyContact,
                    medicalConditions,
                    fitnessGoals,
                    registrationDate,
                    membershipStatus,
                    addedBy));
            AlertsUtil.showSuccessAlert("Member saved successfully!");
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            AlertsUtil.showErrorAlert("Failed to save member : " + e.getMessage());
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        String memberId = lblMemberId.getText();
        String name = txtName.getText();
        Double weight = Double.valueOf(txtWeight.getText());
        Double height = Double.valueOf(txtHeight.getText());
        int age = Integer.parseInt(txtAge.getText());
        String contact = txtPhone.getText();
        String emergencyContact = txtEmergencyContact.getText();
        String medicalConditions = txtMedicalConditions.getText();
        String fitnessGoals = txtFitnessGoals.getText();
        String registrationDate = txtRegistrationDate.getText();
        String membershipStatus = txtMembershipStatus.getText();
        String addedBy = txtAddedBy.getText();

        if (!isValidInput())
            return;

        try {
            if (memberBO.isDuplicateMemberForUpdate(memberId, contact)) {
                AlertsUtil.showWarnerAlert("This contact number is already registered for another member.");
                return;
            }
            memberBO.updateMember(new MemberDTO(
                    memberId,
                    name,
                    weight,
                    height,
                    age,
                    contact,
                    emergencyContact,
                    medicalConditions,
                    fitnessGoals,
                    registrationDate,
                    membershipStatus,
                    addedBy));
            AlertsUtil.showSuccessAlert("Member updated successfully!");
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            AlertsUtil.showErrorAlert("Failed to update member : " + e.getMessage());
        }
    }

    public boolean existMember(String memberId) throws SQLException, ClassNotFoundException {
        return memberBO.existMember(memberId);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are You Sure ? ",
                ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String memberId = lblMemberId.getText();
            try {
                if (!existMember(memberId)) {
                    AlertsUtil.showWarnerAlert("Member with ID : " + memberId + " does not exist.");
                    return;
                }
                memberBO.deleteMember(memberId);
                AlertsUtil.showSuccessAlert("Member deleted successfully!");
                resetPage();
            } catch (Exception e) {
                e.printStackTrace();
                AlertsUtil.showErrorAlert("Failed to delete member : " + e.getMessage());
            }
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private String loadNextId() throws SQLException, ClassNotFoundException {
        try {
            String newId = memberBO.generateNewMemberId();
            lblMemberId.setText(newId);
        } catch (SQLException | ClassNotFoundException e) {
            AlertsUtil.showErrorAlert("Failed to generate a new user ID : " + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    public void getData(MouseEvent mouseEvent) {
        MemberTM selectedItem = tblMember.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblMemberId.setText(selectedItem.getMember_id());
            txtName.setText(selectedItem.getName());
            txtWeight.setText(String.valueOf(selectedItem.getWeight()));
            txtHeight.setText(String.valueOf(selectedItem.getHeight()));
            txtAge.setText(String.valueOf(selectedItem.getAge()));
            txtPhone.setText(selectedItem.getContact());
            txtEmergencyContact.setText(selectedItem.getEmergency_contact());
            txtMedicalConditions.setText(selectedItem.getMedical_conditions());
            txtFitnessGoals.setText(selectedItem.getFitness_goals());
            txtRegistrationDate.setText(selectedItem.getRegister_date());
            txtMembershipStatus.setText(selectedItem.getMembership_status());
            txtAddedBy.setText(selectedItem.getAdded_by());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
