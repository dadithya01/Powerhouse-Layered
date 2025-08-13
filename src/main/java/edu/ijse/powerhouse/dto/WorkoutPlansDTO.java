package edu.ijse.powerhouse.dto;

public class WorkoutPlansDTO {

    private String workout_plan_id;
    private String name;
    private String description;
    private String difficulty_level;
    private String created_by;
    private String  created_date;
    private String duration_weeks;

    public WorkoutPlansDTO() {
    }

    public WorkoutPlansDTO(String workout_plan_id, String name, String description, String difficulty_level, String created_by, String created_date, String duration_weeks) {
        this.workout_plan_id = workout_plan_id;
        this.name = name;
        this.description = description;
        this.difficulty_level = difficulty_level;
        this.created_by = created_by;
        this.created_date = created_date;
        this.duration_weeks = duration_weeks;
    }

    public String getWorkout_plan_id() {
        return workout_plan_id;
    }

    public void setWorkout_plan_id(String workout_plan_id) {
        this.workout_plan_id = workout_plan_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(String difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getDuration_weeks() {
        return duration_weeks;
    }

    public void setDuration_weeks(String duration_weeks) {
        this.duration_weeks = duration_weeks;
    }
}
