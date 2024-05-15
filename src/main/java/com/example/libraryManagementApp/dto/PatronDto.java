package com.example.libraryManagementApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatronDto {

    private Long id;

    @NotBlank(message = "name is mandatory . ")
    private String name;

    @Email(message = "Please enter a valid email address")
    private String email;

    @Size(min = 11, max = 13, message = "Phone must be 11 or 13 characters")
    private String phone;
    @NotBlank(message = "address is mandatory . ")
    private String address;
}
