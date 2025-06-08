package com.ecom.payload;

import com.ecom.entities.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private int age;
    private String gender;
    private String phoneNumber;
    private Role role;
}
