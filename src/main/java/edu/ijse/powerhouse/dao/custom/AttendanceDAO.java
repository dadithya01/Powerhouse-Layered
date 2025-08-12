package edu.ijse.powerhouse.dao.custom;

import edu.ijse.powerhouse.dao.CrudDAO;
import edu.ijse.powerhouse.entity.Attendance;

public interface AttendanceDAO extends CrudDAO<Attendance> {
    public boolean isDuplicateAttendance(String member_id) throws Exception;

    public boolean isDuplicateAttendanceForUpdate(String attendanceId, String member_id) throws Exception;
}
