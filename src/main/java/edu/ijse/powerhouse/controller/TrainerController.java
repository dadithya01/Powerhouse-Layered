package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.bo.BOFactory;
import edu.ijse.powerhouse.bo.custom.TrainerBO;
import edu.ijse.powerhouse.dto.TrainerDTO;
import edu.ijse.powerhouse.util.AlertsUtil;
import edu.ijse.powerhouse.util.AnimationsUtil;
import edu.ijse.powerhouse.view.tdm.MemberTM;
import edu.ijse.powerhouse.view.tdm.TrainerTM;

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

public class TrainerController implements Initializable {

    public Label lblTrainerId;
    public TextField txtUserId;
    public TextField txtName;
    public TextField txtPhone;
    public TextField txtAddress;
    public TextField txtAge;
    public TextField txtSpecialization;
    public TextField txtCertification;
    public TextField txtHireDate;
    public TextField txtBio;
    public TextField txtRating;
    public Label lblMain;
    public TableView<TrainerTM> tblTrainerList;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;

    TrainerBO trainerBO = (TrainerBO) BOFactory.getInstance().getBO(BOFactory.BOType.TRAINER);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblTrainerList.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("trainer_id"));
        tblTrainerList.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("user_id"));
        tblTrainerList.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblTrainerList.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblTrainerList.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblTrainerList.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("age"));
        tblTrainerList.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("specialization"));
        tblTrainerList.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("certification"));
        tblTrainerList.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("hire_date"));
        tblTrainerList.getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("bio"));
        tblTrainerList.getColumns().get(10).setCellValueFactory(new PropertyValueFactory<>("rating"));
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
            AlertsUtil.showErrorAlert("Something went wrong!");
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblTrainerList.getItems().clear();
        ArrayList<TrainerDTO> allTrainers = trainerBO.getAllTrainers();
        for (TrainerDTO trainerDTO : allTrainers){
            tblTrainerList.getItems().add(
                    new TrainerTM(
                            trainerDTO.getTrainer_id(),
                            trainerDTO.getUser_id(),
                            trainerDTO.getName(),
                            trainerDTO.getContact(),
                            trainerDTO.getAddress(),
                            trainerDTO.getAge(),
                            trainerDTO.getSpecialization(),
                            trainerDTO.getCertification(),
                            trainerDTO.getHire_date(),
                            trainerDTO.getBio(),
                            trainerDTO.getRating()
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

            txtUserId.setText(null);
            txtName.setText(null);
            txtPhone.setText(null);
            txtAddress.setText(null);
            txtAge.setText(null);
            txtSpecialization.setText(null);
            txtCertification.setText(null);
            txtHireDate.setText(null);
            txtBio.setText(null);
            txtRating.setText(null);

        } catch (Exception e) {
            e.printStackTrace();
            AlertsUtil.showErrorAlert("Something went wrong!");
        }
    }

    private boolean isValidInput() {
        String userId = txtUserId.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String ageText = txtAge.getText();
        String specialization = txtSpecialization.getText();
        String certification = txtCertification.getText();
        String hireDate = txtHireDate.getText();
        String bio = txtBio.getText();
        String ratingText = txtRating.getText();

        if (userId.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()
                || ageText.isEmpty() || specialization.isEmpty() || certification.isEmpty()
                || hireDate.isEmpty() || bio.isEmpty() || ratingText.isEmpty()) {
            AlertsUtil.showWarnerAlert("All fields must be filled!");
            return false;
        }

        if (!phone.matches("\\d{10}")) {
            AlertsUtil.showWarnerAlert("Phone number must be 10 digits!");
            return false;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
            if (age <= 0 || age > 120) {
                AlertsUtil.showWarnerAlert("Age must be between 1 and 120!");
                return false;
            }
        } catch (NumberFormatException e) {
            AlertsUtil.showWarnerAlert("Invalid age!");
            return false;
        }

        double rating;
        try {
            rating = Double.parseDouble(ratingText);
            if (rating < 0 || rating > 5) {
                AlertsUtil.showWarnerAlert("Rating must be between 0 and 5!");
                return false;
            }
        } catch (NumberFormatException e) {
            AlertsUtil.showWarnerAlert(" Invalid rating!");
            return false;
        }

        if (!hireDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            AlertsUtil.showWarnerAlert("Hire date must be in the format YYYY-MM-DD!");
            return false;
        }

        return true;
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        if (!isValidInput())
            return;

        String trainerId = lblTrainerId.getText();
        String userId = txtUserId.getText();
        String name = txtName.getText();
        String contact = txtPhone.getText();
        String address = txtAddress.getText();
        int age = Integer.parseInt(txtAge.getText());
        String specialization = txtSpecialization.getText();
        String certification = txtCertification.getText();
        String hireDate = txtHireDate.getText();
        String bio = txtBio.getText();
        Double rating = Double.valueOf(txtRating.getText());

        try {
            if (trainerBO.isDuplicateTrainer(contact)){
                AlertsUtil.showWarnerAlert("Trainer with this contact already exists!");
                return;
            }
            trainerBO.saveTrainer(new TrainerDTO(
                    trainerId,
                    userId,
                    name,
                    contact,
                    address,
                    age,
                    specialization,
                    certification,
                    hireDate,
                    bio,
                    rating
            ));
            AlertsUtil.showSuccessAlert("Trainer added successfully!");
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            AlertsUtil.showErrorAlert("Something went wrong!");
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        if (!isValidInput())
            return;

        String trainerId = lblTrainerId.getText();
        String userId = txtUserId.getText();
        String name = txtName.getText();
        String contact = txtPhone.getText();
        String address = txtAddress.getText();
        int age = Integer.parseInt(txtAge.getText());
        String specialization = txtSpecialization.getText();
        String certification = txtCertification.getText();
        String hireDate = txtHireDate.getText();
        String bio = txtBio.getText();
        Double rating = Double.valueOf(txtRating.getText());
        try {
            if (trainerBO.isDuplicateTrainerForUpdate( trainerId, contact)) {
                AlertsUtil.showSuccessAlert(" Trainer with this contact already exists!");
                return;
            }

            trainerBO.updateTrainer(new TrainerDTO(
                    trainerId,
                    userId,
                    name,
                    contact,
                    address,
                    age,
                    specialization,
                    certification,
                    hireDate,
                    bio,
                    rating
            ));
            AlertsUtil.showSuccessAlert("Trainer updated successfully!");
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            AlertsUtil.showErrorAlert("Something went wrong!");
        }
    }

    public boolean existMember(String trainerId) throws SQLException, ClassNotFoundException {
        return trainerBO.existTrainer(trainerId);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are You Sure ? ",
                ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String trainerId = lblTrainerId.getText();
            try {

                if (!existMember(trainerId)) {
                    AlertsUtil.showWarnerAlert("Trainer does not exist!");
                    return;
                }

                trainerBO.deleteTrainer(trainerId);
                AlertsUtil.showSuccessAlert("Trainer deleted successfully!");
                resetPage();
            } catch (Exception e) {
                e.printStackTrace();
                AlertsUtil.showErrorAlert("Something went wrong!");
            }
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        try{
            String nextId = trainerBO.generateNewTrainerId();
            lblTrainerId.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
            AlertsUtil.showErrorAlert("Failed to delete trainer : " + e.getMessage());
        }

    }

    public void getData(MouseEvent mouseEvent) {

        TrainerTM selectedItem = tblTrainerList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblTrainerId.setText(selectedItem.getTrainer_id());
            txtUserId.setText(selectedItem.getUser_id());
            txtName.setText(selectedItem.getName());
            txtPhone.setText(selectedItem.getContact());
            txtAddress.setText(selectedItem.getAddress());
            txtAge.setText(String.valueOf(selectedItem.getAge()));
            txtSpecialization.setText(selectedItem.getSpecialization());
            txtCertification.setText(selectedItem.getCertification());
            txtHireDate.setText(selectedItem.getHire_date());
            txtBio.setText(selectedItem.getBio());
            txtRating.setText(String.valueOf(selectedItem.getRating()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
