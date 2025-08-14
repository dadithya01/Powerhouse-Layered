package edu.ijse.powerhouse.view.tdm;

public class AssignDietPlansTM {

    private String member_id;
    private String diet_plan_id;
    private String assigned_date;
    private String end_date;
    private String notes;
    private String assigned_by;

    public AssignDietPlansTM() {
    }

    public AssignDietPlansTM(String member_id, String diet_plan_id, String assigned_date, String end_date, String notes, String assigned_by) {
        this.member_id = member_id;
        this.diet_plan_id = diet_plan_id;
        this.assigned_date = assigned_date;
        this.end_date = end_date;
        this.notes = notes;
        this.assigned_by = assigned_by;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getDiet_plan_id() {
        return diet_plan_id;
    }

    public void setDiet_plan_id(String workout_plan_id) {
        this.diet_plan_id = workout_plan_id;
    }

    public String getAssigned_date() {
        return assigned_date;
    }

    public void setAssigned_date(String assigned_date) {
        this.assigned_date = assigned_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAssigned_by() {
        return assigned_by;
    }

    public void setAssigned_by(String assigned_by) {
        this.assigned_by = assigned_by;
    }
}
