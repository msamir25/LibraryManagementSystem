package com.example.libraryManagementApp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long id;

    @NotBlank(message = "Title is mandatory . ")
    private String title;

    @NotBlank(message = "Author name is mandatory")
    private String author;

    @Min(value = 1000, message = "Publication year must be after 999")
    private int publicationYear;

    @NotBlank(message = "ISBN is mandatory")
    @Size(min = 10, max = 13, message = "ISBN must be 10 or 13 characters")
    private String ISBN;

    private boolean available;
}
