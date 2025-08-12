package edu.ijse.powerhouse.entity;

public class Employee {
    private String employee_id;
    private String name;
    private String contact;
    private String address;
    private int age;
    private String hire_date;
    private String position;
    private double salary;
    private String emergency_contact;

    public Employee() {
    }

    public Employee(String employee_id, String name, String contact, String address, int age, String hire_date, String position, double salary, String emergency_contact) {
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

    public double getSalary() {
        return salary;
    }

    public String getEmergency_contact() {
        return emergency_contact;
    }
}
