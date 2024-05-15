package com.example.libraryManagementApp.services;

import com.example.libraryManagementApp.dto.BookDto;
import com.example.libraryManagementApp.dto.PatronDto;

import java.util.List;

public interface PatronService {
    PatronDto createPatron(PatronDto patronDto);


    List<PatronDto> getAllPatrons();

    PatronDto getPatronById(Long id);

    PatronDto updatePatronById(PatronDto patronDto ,  Long id);

    void deleteById(Long id);
}
