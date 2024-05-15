package com.example.libraryManagementApp.services;

import com.example.libraryManagementApp.dto.RecordDto;
import com.example.libraryManagementApp.entity.BorrowingRecord;
import org.springframework.http.ResponseEntity;

public interface BorrowingRecordService {

    RecordDto borrowBook(Long bookId , Long patronId);

    public RecordDto returnBook(Long bookId , Long patronId);
}
