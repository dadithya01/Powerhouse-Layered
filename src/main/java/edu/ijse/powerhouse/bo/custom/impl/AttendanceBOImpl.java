package edu.ijse.powerhouse.bo.custom.impl;

import edu.ijse.powerhouse.bo.custom.AttendanceBO;
import edu.ijse.powerhouse.dao.DAOFactory;
import edu.ijse.powerhouse.dao.custom.AttendanceDAO;
import edu.ijse.powerhouse.dto.AttendanceDTO;
import edu.ijse.powerhouse.dto.MemberDTO;
import edu.ijse.powerhouse.entity.Attendance;
import edu.ijse.powerhouse.entity.Member;
import edu.ijse.powerhouse.util.SQLUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceBOImpl implements AttendanceBO {

    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Attendance);
    @Override
    public ArrayList<AttendanceDTO> getAllAttendance() throws SQLException, ClassNotFoundException {
        ArrayList<Attendance> entity = attendanceDAO.getAll();
        ArrayList<AttendanceDTO> attendanceDTOS = new ArrayList<>();
        for (Attendance attendance : entity) {
            attendanceDTOS.add(new AttendanceDTO(
                    attendance.getAttendance_id(),
                    attendance.getMember_id(),
                    attendance.getCheck_in(),
                    attendance.getCheck_out(),
                    attendance.getRecorded_by()
            ));
        }
        return attendanceDTOS;
    }

    @Override
    public boolean saveAttendance(AttendanceDTO attendanceDTO) throws SQLException, ClassNotFoundException {
        return attendanceDAO.save(new Attendance(
                attendanceDTO.getAttendance_id(),
                attendanceDTO.getMember_id(),
                attendanceDTO.getCheck_in(),
                attendanceDTO.getCheck_out(),
                attendanceDTO.getRecorded_by()
        ));
    }

    @Override
    public boolean updateAttendance(AttendanceDTO attendanceDTO) throws SQLException, ClassNotFoundException {
        return attendanceDAO.update(new Attendance(
                attendanceDTO.getAttendance_id(),
                attendanceDTO.getMember_id(),
                attendanceDTO.getCheck_in(),
                attendanceDTO.getCheck_out(),
                attendanceDTO.getRecorded_by()
        ));
    }

    @Override
    public boolean deleteAttendance(String attendanceId) throws SQLException, ClassNotFoundException {
        return attendanceDAO.delete(attendanceId);
    }

    @Override
    public boolean existAttendance(String attendanceId) throws SQLException, ClassNotFoundException {
        return attendanceDAO.exist(attendanceId);
    }

    @Override
    public String generateNewAttendanceId() throws SQLException, ClassNotFoundException {
        return attendanceDAO.generateNewId();
    }

    @Override
    public boolean isDuplicateAttendance(String memberId) throws Exception {
        return attendanceDAO.isDuplicateAttendance(memberId);
    }

    @Override
    public boolean isDuplicateAttendanceForUpdate(String attendanceId, String memberId) throws Exception {
        return attendanceDAO.isDuplicateAttendanceForUpdate(attendanceId,memberId);
    }
}
