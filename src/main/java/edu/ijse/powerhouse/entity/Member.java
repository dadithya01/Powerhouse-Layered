package edu.ijse.powerhouse.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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

}
