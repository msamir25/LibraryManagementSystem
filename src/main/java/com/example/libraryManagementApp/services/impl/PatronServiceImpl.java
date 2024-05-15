package com.example.libraryManagementApp.services.impl;

import com.example.libraryManagementApp.Repository.PatronRepository;
import com.example.libraryManagementApp.dto.PatronDto;
import com.example.libraryManagementApp.entity.Book;
import com.example.libraryManagementApp.entity.Patron;
import com.example.libraryManagementApp.exceptions.ResourceNotFoundException;
import com.example.libraryManagementApp.services.PatronService;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatronServiceImpl implements PatronService {

    private PatronRepository patronRepository;

    @Autowired
    public PatronServiceImpl(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Override
    public PatronDto createPatron(PatronDto patronDto) {
        Patron patron = new Patron();

        patron.setAddress(patronDto.getAddress());
        patron.setEmail(patronDto.getEmail());
        patron.setName(patronDto.getName());
        patron.setPhone(patronDto.getPhone());


        return patronRepository.save(patron).getDto();
    }

    @Override
    public List<PatronDto> getAllPatrons() {
        List<Patron> patrons = patronRepository.findAll();
        return patrons.stream().map(Patron::getDto).collect(Collectors.toList());
    }

    @Override
    public PatronDto getPatronById(Long id) {
        Patron patron = patronRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(" Patron " , " id " , id));

        return patron.getDto();
    }

    @Override
    public PatronDto updatePatronById(PatronDto patronDto, Long id) {
        Patron patron = patronRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(" Patron " , " id " , id));

        patron.setName(patronDto.getName());
        patron.setAddress(patronDto.getAddress());
        patron.setEmail(patronDto.getEmail());
        patron.setPhone(patronDto.getPhone());
        return patronRepository.save(patron).getDto();
    }

    @Override
    public void deleteById(Long id) {
        Patron patron = patronRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(" Patron " , " id " , id));

        patronRepository.delete(patron);
    }
}
