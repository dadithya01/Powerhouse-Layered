package edu.ijse.powerhouse.view.tdm;

public class AttendanceTM {

    private String attendance_id;
    private String  member_id;
    private String check_in;
    private String check_out;
    private String recorded_by;

    public AttendanceTM() {
    }
    public AttendanceTM(String attendance_id, String member_id, String check_in, String check_out, String recorded_by) {
        this.attendance_id = attendance_id;
        this.member_id = member_id;
        this.check_in = check_in;
        this.check_out = check_out;
        this.recorded_by = recorded_by;
    }

    public String getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(String attendance_id) {
        this.attendance_id = attendance_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public String getRecorded_by() {
        return recorded_by;
    }

    public void setRecorded_by(String recorded_by) {
        this.recorded_by = recorded_by;
    }
}
