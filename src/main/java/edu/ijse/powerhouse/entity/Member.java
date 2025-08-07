package edu.ijse.powerhouse.entity;

public class Member {

    private String member_id;
    private String name;
    private Double weight;
    private Double height;
    private int age;
    private String contact;
    private String emergency_contact;
    private String medical_conditions;
    private String fitness_goals;
    private String register_date;
    private String membership_status;
    private String added_by;

    public Member() {
    }

    public Member(String member_id, String name, Double weight, Double height, int age, String contact, String emergency_contact, String medical_conditions, String fitness_goals, String  register_date, String membership_status, String added_by) {
        this.member_id = member_id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.contact = contact;
        this.emergency_contact = emergency_contact;
        this.medical_conditions = medical_conditions;
        this.fitness_goals = fitness_goals;
        this.register_date = register_date;
        this.membership_status = membership_status;
        this.added_by = added_by;
    }
    public String getMember_id() {
        return member_id;
    }
    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getWeight() {
        return weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    public Double getHeight() {
        return height;
    }
    public void setHeight(Double height) {
        this.height = height;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getEmergency_contact() {
        return emergency_contact;
    }
    public void setEmergency_contact(String emergency_contact) {
        this.emergency_contact = emergency_contact;
    }
    public String getMedical_conditions() {
        return medical_conditions;
    }
    public void setMedical_conditions(String medical_conditions) {
        this.medical_conditions = medical_conditions;
    }
    public String getFitness_goals() {
        return fitness_goals;
    }
    public void setFitness_goals(String fitness_goals) {
        this.fitness_goals = fitness_goals;
    }
    public String  getRegister_date() {
        return register_date;
    }
    public void setRegister_date(String  register_date) {
        this.register_date = register_date;
    }
    public String getMembership_status() {
        return membership_status;
    }
    public void setMembership_status(String membership_status) {
        this.membership_status = membership_status;
    }
    public String getAdded_by() {
        return added_by;
    }
    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }
}
