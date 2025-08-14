package edu.ijse.powerhouse.entity;

public class DietPlans {

    private String diet_plan_id;
    private String name;
    private String description;
    private String created_by;
    private String created_date;
    private String calorie_target;
    private String protein_target;
    private String carbs_target;
    private String fat_target;
    private String notes;

    public DietPlans() {
    }

    public DietPlans(String diet_plan_id, String name, String description, String created_by, String created_date, String calorie_target, String protein_target, String carbs_target, String fat_target, String notes) {
        this.diet_plan_id = diet_plan_id;
        this.name = name;
        this.description = description;
        this.created_by = created_by;
        this.created_date = created_date;
        this.calorie_target = calorie_target;
        this.protein_target = protein_target;
        this.carbs_target = carbs_target;
        this.fat_target = fat_target;
        this.notes = notes;
    }

    public String getDiet_plan_id() {
        return diet_plan_id;
    }

    public void setDiet_plan_id(String diet_plan_id) {
        this.diet_plan_id = diet_plan_id;
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

    public String getCalorie_target() {
        return calorie_target;
    }

    public void setCalorie_target(String calorie_target) {
        this.calorie_target = calorie_target;
    }

    public String getProtein_target() {
        return protein_target;
    }

    public void setProtein_target(String protein_target) {
        this.protein_target = protein_target;
    }

    public String getCarbs_target() {
        return carbs_target;
    }

    public void setCarbs_target(String carbs_target) {
        this.carbs_target = carbs_target;
    }

    public String getFat_target() {
        return fat_target;
    }

    public void setFat_target(String fat_target) {
        this.fat_target = fat_target;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
