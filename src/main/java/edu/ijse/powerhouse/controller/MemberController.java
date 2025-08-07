package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.dto.MemberListDto;
import edu.ijse.powerhouse.dto.tm.MemberListTM;
import edu.ijse.powerhouse.model.MemberListModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MemberController implements Initializable {

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

    public TableView<MemberListTM> tblMemberList;
    public TableColumn<MemberListTM, String> colId;
    public TableColumn<MemberListTM, String> colName;
    public TableColumn<MemberListTM, String> colWeight;
    public TableColumn<MemberListTM, String> colHeight;
    public TableColumn<MemberListTM, String> colAge;
    public TableColumn<MemberListTM, String> colPhone;
    public TableColumn<MemberListTM, String> colEmergencyContact;
    public TableColumn<MemberListTM, String> colMedicalConditions;
    public TableColumn<MemberListTM, String> colFitnessGoals;
    public TableColumn<MemberListTM, String> colRegistrationDate;
    public TableColumn<MemberListTM, String> colMembershipStatus;
    public TableColumn<MemberListTM, String> colAddedBy;

    private final MemberListModel memberListModel = new MemberListModel();
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("member_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmergencyContact.setCellValueFactory(new PropertyValueFactory<>("emergency_contact"));
        colMedicalConditions.setCellValueFactory(new PropertyValueFactory<>("medical_conditions"));
        colFitnessGoals.setCellValueFactory(new PropertyValueFactory<>("fitness_goals"));
        colRegistrationDate.setCellValueFactory(new PropertyValueFactory<>("register_date"));
        colMembershipStatus.setCellValueFactory(new PropertyValueFactory<>("membership_status"));
        colAddedBy.setCellValueFactory(new PropertyValueFactory<>("added_by"));

        try {
            resetPage();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblMemberList.setItems(FXCollections.observableArrayList(
                memberListModel.getAllMembers()
                        .stream()
                        .map(memberListDto -> new MemberListTM(
                                memberListDto.getMember_id(),
                                memberListDto.getName(),
                                memberListDto.getWeight(),
                                memberListDto.getHeight(),
                                memberListDto.getAge(),
                                memberListDto.getContact(),
                                memberListDto.getEmergency_contact(),
                                memberListDto.getMedical_conditions(),
                                memberListDto.getFitness_goals(),
                                memberListDto.getRegister_date(),
                                memberListDto.getMembership_status(),
                                memberListDto.getAdded_by()
                        )).toList()
        ));
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
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private boolean isValidInput() {
        if (txtName.getText().isBlank()) {
            showAlert("Name is required.");
            return false;
        }
        if (!txtWeight.getText().matches("\\d+(\\.\\d+)?")) {
            showAlert("Enter a valid weight (e.g., 70.5).");
            return false;
        }
        if (!txtHeight.getText().matches("\\d+(\\.\\d+)?")) {
            showAlert("Enter a valid height (e.g., 175.0).");
            return false;
        }
        if (!txtAge.getText().matches("\\d{1,3}")) {
            showAlert("Enter a valid age (e.g., 25).");
            return false;
        }
        if (!txtPhone.getText().matches("\\d{10,15}")) {
            showAlert("Enter a valid phone number (10-15 digits).");
            return false;
        }
        if (!txtEmergencyContact.getText().matches("\\d{10,15}")) {
            showAlert("Enter a valid emergency contact number.");
            return false;
        }
        if (txtRegistrationDate.getText().isBlank()) {
            showAlert("Registration date is required.");
            return false;
        }
        if (txtMembershipStatus.getText().isBlank()) {
            showAlert("Membership status is required.");
            return false;
        }
        if (txtAddedBy.getText().isBlank()) {
            showAlert("Added By field is required.");
            return false;
        }

        return true;
    }

    private void showAlert(String message) {
        new Alert(Alert.AlertType.WARNING, message).show();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        if (!isValidInput()) return;

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

        MemberListDto memberListDto = new MemberListDto(
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
                addedBy
        );
        try {
            boolean isSaved = memberListModel.saveMember(memberListDto);

            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Saved").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        if (!isValidInput()) return;

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

        MemberListDto memberListDto = new MemberListDto(
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
                addedBy
        );
        try {
            boolean isUpdated = memberListModel.updateMember(memberListDto);
            if(isUpdated){
                resetPage();
                new Alert(Alert.AlertType.INFORMATION,"Updated").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail").show();
            }
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
            String memberId = lblMemberId.getText();
            try {
                boolean isDeleted = memberListModel.deleteMember(memberId);
                if(isDeleted){
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Fail").show();
                }
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
        String nextId = memberListModel.getNextMemberId();
        lblMemberId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        MemberListTM selectedItem = tblMemberList.getSelectionModel().getSelectedItem();

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
