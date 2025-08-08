package edu.ijse.powerhouse.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Trainer {
    private String trainer_id;
    private String user_id;
    private String name;
    private String contact;
    private String address;
    private int age;
    private String specialization;
    private String certification;
    private String  hire_date;
    private String bio;
    private Double rating;

}
