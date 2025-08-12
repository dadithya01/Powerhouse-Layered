package edu.ijse.powerhouse.dao.custom.impl;

import edu.ijse.powerhouse.dao.custom.AttendanceDAO;
import edu.ijse.powerhouse.entity.Attendance;
import edu.ijse.powerhouse.entity.Member;
import edu.ijse.powerhouse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceDAOImpl implements AttendanceDAO {
    @Override
    public boolean isDuplicateAttendance(String member_id) throws Exception {
        return SQLUtil.executeQuery("SELECT attendance_id FROM Attendance WHERE member_id = ?", member_id).next();
    }

    @Override
    public boolean isDuplicateAttendanceForUpdate(String attendanceId, String member_id) throws Exception {
        return SQLUtil.executeQuery("SELECT attendance_id FROM Attendance WHERE attendance_id != ? AND member_id = ?", attendanceId, member_id).next();
    }

    @Override
    public ArrayList<Attendance> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Attendance");
        ArrayList<Attendance> attendances = new ArrayList<>();
        while (rst.next()) {
            attendances.add(new Attendance(
                    rst.getString("attendance_id"),
                    rst.getString("member_id"),
                    rst.getString("check_in"),
                    rst.getString("check_out"),
                    rst.getString("recorded_by")
            ));
        }
        return attendances;
    }

    @Override
    public boolean save(Attendance entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Attendance (attendance_id, member_id, check_in, check_out, recorded_by) VALUES (?, ?, ?, ?, ?)",
                entity.getAttendance_id(),
                entity.getMember_id(),
                entity.getCheck_in(),
                entity.getCheck_out(),
                entity.getRecorded_by());
    }

    @Override
    public boolean update(Attendance entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Attendance SET member_id = ?, check_in = ?, check_out = ?, recorded_by = ? WHERE attendance_id = ?",
                entity.getMember_id(),
                entity.getCheck_in(),
                entity.getCheck_out(),
                entity.getRecorded_by(),
                entity.getAttendance_id());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT attendance_id FROM Attendance WHERE attendance_id = ?", id).next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Attendance WHERE attendance_id = ?", id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery(" SELECT attendance_id FROM Attendance ORDER BY attendance_id DESC LIMIT 1");
        String tableCharacter = "AT";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);

            return nextIdString;
        }
        return tableCharacter + "001";
    }
}
