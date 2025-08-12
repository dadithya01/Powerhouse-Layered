package edu.ijse.powerhouse.dto;

public class EmployeeDTO {
    private String employee_id;
    private String name;
    private String contact;
    private String address;
    private int age;
    private String hire_date;
    private String position;
    private Double salary;
    private String emergency_contact;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String employee_id, String name, String contact, String address, int age, String hire_date, String position, Double salary, String emergency_contact) {
        this.employee_id = employee_id;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.age = age;
        this.hire_date = hire_date;
        this.position = position;
        this.salary = salary;
        this.emergency_contact = emergency_contact;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public String getName() {
        return name;
    }
    public String getContact() {
        return contact;
    }
    public String getAddress() {
        return address;
    }
    public int getAge() {
        return age;
    }
    public String getHire_date() {
        return hire_date;
    }
    public String getPosition() {
        return position;
    }
    public Double getSalary() {
        return salary;
    }
    public String getEmergency_contact() {
        return emergency_contact;
    }
    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setHire_date(String hire_date) {
        this.hire_date = hire_date;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public void setSalary(Double salary) {
        this.salary = salary;
    }
    public void setEmergency_contact(String emergency_contact) {
        this.emergency_contact = emergency_contact;
    }

}
