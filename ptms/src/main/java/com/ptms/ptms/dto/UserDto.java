package com.ptms.ptms.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(min = 5 , message = "password must be atleast 6 characters")
    private String password;
}
