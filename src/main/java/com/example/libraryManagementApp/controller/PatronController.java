package com.example.libraryManagementApp.controller;

import com.example.libraryManagementApp.dto.PatronDto;
import com.example.libraryManagementApp.services.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PatronController {

    private PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @PostMapping("/patrons")
    public ResponseEntity<PatronDto> createPatron(@RequestBody @Valid PatronDto patronDto){
        return new ResponseEntity<>(patronService.createPatron(patronDto) , HttpStatus.CREATED);

    }

    @GetMapping("/patrons")
    public ResponseEntity<List<PatronDto>> getAllPatron(){
        return  ResponseEntity.ok(patronService.getAllPatrons());
    }

    @GetMapping("/patrons/{id}")
    public ResponseEntity<PatronDto> getPatronById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(patronService.getPatronById(id));
    }

    @PutMapping("/patrons/{id}")
    public ResponseEntity<PatronDto> updatePatronById(@RequestBody @Valid PatronDto patronDto ,
                                                      @PathVariable(name = "id") Long id){
        return ResponseEntity.ok(patronService.updatePatronById(patronDto , id));
    }

    @DeleteMapping("/patrons/{id}")
    public ResponseEntity<String> deletePatronById(@PathVariable(name = "id") Long id){
        patronService.deleteById(id);
        return new ResponseEntity<>("Patron is Deleted Successfully" , HttpStatus.OK);

    }




}
