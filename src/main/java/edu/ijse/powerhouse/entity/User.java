package edu.ijse.powerhouse.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String userId;
    private String name;
    private String phone;
    private String email;
    private String userName;
    private String password;
    private String userTypeId;
    private String registrationDate;
    private String status;

}
