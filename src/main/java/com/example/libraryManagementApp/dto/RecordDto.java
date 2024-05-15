package com.example.libraryManagementApp.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RecordDto {

    private Long id;

    private String title;
    private String patronName;
    private LocalDate borrowedDate;
    private LocalDate dueDate;

}
