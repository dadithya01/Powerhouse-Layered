package edu.ijse.powerhouse.bo.custom;

import edu.ijse.powerhouse.bo.SuperBO;
import edu.ijse.powerhouse.dto.AttendanceDTO;
import edu.ijse.powerhouse.dto.MemberDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AttendanceBO extends SuperBO {
    public ArrayList<AttendanceDTO> getAllAttendance() throws SQLException, ClassNotFoundException;

    public boolean saveAttendance(AttendanceDTO attendanceDTO) throws SQLException, ClassNotFoundException;

    public boolean updateAttendance(AttendanceDTO attendanceDTO) throws SQLException, ClassNotFoundException;

    public boolean deleteAttendance(String attendanceId) throws SQLException, ClassNotFoundException;

    public boolean existAttendance(String attendanceId) throws SQLException, ClassNotFoundException;

    public String generateNewAttendanceId() throws SQLException, ClassNotFoundException;

    public boolean isDuplicateAttendance(String memberId) throws Exception;

    public boolean isDuplicateAttendanceForUpdate(String attendanceId, String memberId) throws Exception;
}
