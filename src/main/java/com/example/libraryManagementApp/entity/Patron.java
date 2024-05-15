package com.example.libraryManagementApp.entity;

import com.example.libraryManagementApp.dto.PatronDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "patrons")
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String address;


    public PatronDto getDto(){
        PatronDto patronDto = new PatronDto();
        patronDto.setAddress(address);
        patronDto.setId(id);
        patronDto.setName(name);
        patronDto.setPhone(phone);
        patronDto.setEmail(email);
        return patronDto;
    }
}
