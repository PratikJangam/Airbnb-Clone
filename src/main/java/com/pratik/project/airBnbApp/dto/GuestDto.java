package com.pratik.project.airBnbApp.dto;

import com.pratik.project.airBnbApp.entity.User;
import com.pratik.project.airBnbApp.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
