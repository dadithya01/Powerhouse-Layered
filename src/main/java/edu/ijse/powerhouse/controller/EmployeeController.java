package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.bo.BOFactory;
import edu.ijse.powerhouse.bo.custom.EmployeeBO;
import edu.ijse.powerhouse.dto.EmployeeDTO;
import edu.ijse.powerhouse.dto.MemberDTO;
import edu.ijse.powerhouse.util.AlertsUtil;
import edu.ijse.powerhouse.view.tdm.EmployeeTM;
import edu.ijse.powerhouse.util.AnimationsUtil;
import edu.ijse.powerhouse.view.tdm.EmployeeTM;
import edu.ijse.powerhouse.view.tdm.MemberTM;
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

public class EmployeeController implements Initializable {

    public Label lblEmployeeId;
    public TextField txtName;
    public TextField txtPhone;
    public TextField txtAddress;
    public TextField txtAge;
    public TextField txtHireDate;
    public TextField txtPosition;
    public TextField txtSalary;
    public TextField txtEmergencyContact;
    public Label lblMain;
    public TableView<EmployeeTM> tblEmployeeList;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;

    EmployeeBO employeeBO=(EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblEmployeeList.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        tblEmployeeList.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblEmployeeList.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblEmployeeList.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblEmployeeList.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("age"));
        tblEmployeeList.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("hire_date"));
        tblEmployeeList.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("position"));
        tblEmployeeList.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("salary"));
        tblEmployeeList.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("emergency_contact"));
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
        tblEmployeeList.getItems().clear();
        ArrayList<EmployeeDTO> allEmployees = employeeBO.getAllEmployees();
        for (EmployeeDTO employeeDTO : allEmployees) {
            tblEmployeeList.getItems().add(
                    new EmployeeTM(
                            employeeDTO.getEmployee_id(),
                            employeeDTO.getName(),
                            employeeDTO.getContact(),
                            employeeDTO.getAddress(),
                            employeeDTO.getAge(),
                            employeeDTO.getHire_date(),
                            employeeDTO.getPosition(),
                            employeeDTO.getSalary(),
                            employeeDTO.getEmergency_contact()
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
            txtPhone.setText(null);
            txtAddress.setText(null);
            txtAge.setText(null);
            txtHireDate.setText(null);
            txtPosition.setText(null);
            txtSalary.setText(null);
            txtEmergencyContact.setText(null);


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private String validateInput(
            String employeeId, String name, String contact, String address, String ageText,
            String hireDate, String position, String salaryText, String emergencyContact
    ) {
        // Check required fields
        if (employeeId.isEmpty() || name.isEmpty() || contact.isEmpty() || address.isEmpty() ||
                ageText.isEmpty() || hireDate.isEmpty() || position.isEmpty() || salaryText.isEmpty() || emergencyContact.isEmpty()) {
            return "Please fill all the fields";
        }

        // Validate age
        try {
            int age = Integer.parseInt(ageText);
            if (age <= 0) return "Age must be a positive number";
        } catch (NumberFormatException e) {
            return "Invalid age format";
        }

        // Validate salary
        try {
            double salary = Double.parseDouble(salaryText);
            if (salary <= 0) return "Salary must be a positive number";
        } catch (NumberFormatException e) {
            return "Invalid salary format";
        }

        // Validate phone numbers (digits only, length 10-15)
        if (!contact.matches("\\d{10,15}")) return "Invalid phone number format";
        if (!emergencyContact.matches("\\d{10,15}")) return "Invalid emergency contact format";

        // Validate hire date format YYYY-MM-DD
        if (!hireDate.matches("\\d{4}-\\d{2}-\\d{2}")) return "Hire Date must be in YYYY-MM-DD format";

        // All validations passed
        return null;
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {

        String employeeId = lblEmployeeId.getText().trim();
        String name = txtName.getText().trim();
        String contact = txtPhone.getText().trim();
        String address = txtAddress.getText().trim();
        String ageText = txtAge.getText().trim();
        String hireDate = txtHireDate.getText().trim();
        String position = txtPosition.getText().trim();
        String salaryText = txtSalary.getText().trim();
        String emergencyContact = txtEmergencyContact.getText().trim();

        String validationError = validateInput(employeeId, name, contact, address, ageText, hireDate, position, salaryText, emergencyContact);
        if (validationError != null) {
            new Alert(Alert.AlertType.WARNING, validationError).show();
            return;
        }

        int age = Integer.parseInt(ageText);
        double salary = Double.parseDouble(salaryText);

        try {
            if (employeeBO.isDuplicateEmployee(contact)){
                AlertsUtil.showWarnerAlert("Duplicate Contact An employee with this contact number already exists.");
                return;
            }
            employeeBO.saveEmployee(new EmployeeDTO(
                    employeeId, name, contact, address, age, hireDate, position, salary, emergencyContact
            ));
            AlertsUtil.showSuccessAlert("Employee saved successfully!");
            resetPage();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        String employeeId = lblEmployeeId.getText().trim();
        String name = txtName.getText().trim();
        String contact = txtPhone.getText().trim();
        String address = txtAddress.getText().trim();
        String ageText = txtAge.getText().trim();
        String hireDate = txtHireDate.getText().trim();
        String position = txtPosition.getText().trim();
        String salaryText = txtSalary.getText().trim();
        String emergencyContact = txtEmergencyContact.getText().trim();

        String validationError = validateInput(employeeId, name, contact, address, ageText, hireDate, position, salaryText, emergencyContact);
        if (validationError != null) {
            new Alert(Alert.AlertType.WARNING, validationError).show();
            return;
        }

        int age = Integer.parseInt(ageText);
        double salary = Double.parseDouble(salaryText);

        try {
            if (employeeBO.isDuplicateEmployeeForUpdate(employeeId,contact)){
                AlertsUtil.showWarnerAlert("Duplicate Contact An employee with this contact number already exists.");
                return;
            };
            employeeBO.updateEmployee(new EmployeeDTO(
                    employeeId, name, contact, address, age, hireDate, position, salary, emergencyContact
            ));
            AlertsUtil.showSuccessAlert("Employee updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
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
            String userId = lblEmployeeId.getText();
            try {
                if (!employeeBO.existEmployee(userId)) {
                    AlertsUtil.showWarnerAlert("No such employee exists");
                    return;
                }
                employeeBO.deleteEmployee(userId);
                AlertsUtil.showSuccessAlert("Employee deleted successfully!");
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
        String nextId = employeeBO.generateNewEmployeeId();
        lblEmployeeId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        EmployeeTM selectedItem = tblEmployeeList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblEmployeeId.setText(selectedItem.getEmployee_id());
            txtName.setText(selectedItem.getName());
            txtPhone.setText(selectedItem.getContact());
            txtAddress.setText(selectedItem.getAddress());
            txtAge.setText(String.valueOf(selectedItem.getAge()));
            txtHireDate.setText(selectedItem.getHire_date());
            txtPosition.setText(selectedItem.getPosition());
            txtSalary.setText(String.valueOf(selectedItem.getSalary()));
            txtEmergencyContact.setText(selectedItem.getEmergency_contact());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

}
