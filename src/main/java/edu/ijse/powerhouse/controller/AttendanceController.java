package edu.ijse.powerhouse.controller;

import edu.ijse.powerhouse.bo.BOFactory;
import edu.ijse.powerhouse.bo.custom.AttendanceBO;
import edu.ijse.powerhouse.dto.AttendanceDTO;
import edu.ijse.powerhouse.dto.MemberDTO;
import edu.ijse.powerhouse.util.AlertsUtil;
import edu.ijse.powerhouse.util.AnimationsUtil;
import edu.ijse.powerhouse.view.tdm.AttendanceTM;
import edu.ijse.powerhouse.view.tdm.AttendanceTM;
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

public class AttendanceController implements Initializable {

    public Label lblMain;
    public Label lblAttendanceId;
    public TextField txtMemberId;
    public TextField txtCheckIn;
    public TextField txtCheckOut;
    public TextField txtRecordedBy;
    public TableView<AttendanceTM> tblAttendance;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;

    AttendanceBO attendanceBO= (AttendanceBO) BOFactory.getInstance().getBO(BOFactory.BOType.ATTENDANCE);
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblAttendance.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("attendance_id"));
        tblAttendance.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("member_id"));
        tblAttendance.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("check_in"));
        tblAttendance.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("check_out"));
        tblAttendance.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("recorded_by"));
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
        tblAttendance.getItems().clear();
        ArrayList<AttendanceDTO> allAttendance = attendanceBO.getAllAttendance();
        for (AttendanceDTO attendanceDTO : allAttendance) {
            tblAttendance.getItems().add(
                    new AttendanceTM(
                            attendanceDTO.getAttendance_id(),
                            attendanceDTO.getMember_id(),
                            attendanceDTO.getCheck_in(),
                            attendanceDTO.getCheck_out(),
                            attendanceDTO.getRecorded_by()
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
            txtCheckIn.setText(null);
            txtCheckOut.setText(null);
            txtRecordedBy.setText(null);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private String validateAttendanceInput(
            String attendanceId, String memberId, String checkIn, String checkOut, String recordedBy) {

        if (attendanceId.isEmpty() || memberId.isEmpty() || checkIn.isEmpty() || recordedBy.isEmpty()) {
            return "Please fill all required fields (Attendance ID, Member ID, Check-In, Recorded By)";
        }

        String dateTimePattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
        String TimePattern = "\\d{2}:\\d{2}:\\d{2}";
        if (!checkIn.matches(dateTimePattern)) {
            return "Check-In must be in 'yyyy-MM-dd HH:mm:ss' format";
        }
        if (!checkOut.isEmpty() && !checkOut.matches(TimePattern)) {
            return "Check-Out must be in 'HH:mm:ss' format or left empty";
        }

        if (recordedBy.length() < 3) {
            return "Recorded By must be at least 3 characters";
        }

        return null;
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {

        String attendanceId = lblAttendanceId.getText().trim();
        String memberId = txtMemberId.getText().trim();
        String checkIn = txtCheckIn.getText().trim();
        String checkOut = txtCheckOut.getText().trim();
        String recordedBy = txtRecordedBy.getText().trim();

        String validationError = validateAttendanceInput(attendanceId, memberId, checkIn, checkOut, recordedBy);
        if (validationError != null) {
            new Alert(Alert.AlertType.WARNING, validationError).show();
            return;
        }

        try {
            if (attendanceBO.isDuplicateAttendance(memberId)){
                AlertsUtil.showWarnerAlert("Duplicate attendance for this member");
                return;
            }
            attendanceBO.saveAttendance(new AttendanceDTO(
                    attendanceId,
                    memberId,
                    checkIn,
                    checkOut,
                    recordedBy
            ));
            AlertsUtil.showSuccessAlert("Attendance saved successfully");
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        String attendanceId = lblAttendanceId.getText().trim();
        String memberId = txtMemberId.getText().trim();
        String checkIn = txtCheckIn.getText().trim();
        String checkOut = txtCheckOut.getText().trim();
        String recordedBy = txtRecordedBy.getText().trim();

        String validationError = validateAttendanceInput(attendanceId, memberId, checkIn, checkOut, recordedBy);
        if (validationError != null) {
            new Alert(Alert.AlertType.WARNING, validationError).show();
            return;
        }
        try {
            if (attendanceBO.isDuplicateAttendanceForUpdate(attendanceId,memberId)){
                AlertsUtil.showWarnerAlert("Duplicate attendance for this member");
                return;
            }
            attendanceBO.updateAttendance(new AttendanceDTO(
                    attendanceId,
                    memberId,
                    checkIn,
                    checkOut,
                    recordedBy
            ));
            AlertsUtil.showSuccessAlert("Attendance updated successfully");
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public boolean existAttendance(String attendanceId) throws SQLException, ClassNotFoundException {
        return attendanceBO.existAttendance(attendanceId);
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
            String attendanceId = lblAttendanceId.getText();
            try {
                if (!existAttendance(attendanceId)){
                    AlertsUtil.showWarnerAlert("Attendance ID does not exist");
                    return;
                }
                attendanceBO.deleteAttendance(attendanceId);
                AlertsUtil.showSuccessAlert("Attendance deleted successfully");
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
        String nextId = attendanceBO.generateNewAttendanceId();
        lblAttendanceId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        AttendanceTM selectedItem = tblAttendance.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblAttendanceId.setText(selectedItem.getAttendance_id());
            txtMemberId.setText(selectedItem.getMember_id());
            txtCheckIn.setText(selectedItem.getCheck_in());
            txtCheckOut.setText(selectedItem.getCheck_out());
            txtRecordedBy.setText(selectedItem.getRecorded_by());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
