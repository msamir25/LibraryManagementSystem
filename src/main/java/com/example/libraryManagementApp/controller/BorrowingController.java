package com.example.libraryManagementApp.controller;

import com.example.libraryManagementApp.dto.RecordDto;
import com.example.libraryManagementApp.entity.BorrowingRecord;
import com.example.libraryManagementApp.services.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingController {

    private BorrowingRecordService borrowingRecordService;

    @Autowired
    public BorrowingController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<RecordDto> borrowBook(@PathVariable(name = "bookId") Long bookId ,
                                                      @PathVariable(name = "patronId") Long patronId){
        return new ResponseEntity<>(borrowingRecordService.borrowBook(bookId , patronId) , HttpStatus.CREATED);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<?> returnBook(@PathVariable(name = "bookId") Long bookId ,
                                        @PathVariable(name = "patronId") Long patronId){

        //return borrowingRecordService.returnBook(bookId , patronId);
        return ResponseEntity.ok(borrowingRecordService.returnBook(bookId , patronId));
    }

}
