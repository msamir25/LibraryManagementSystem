package com.example.libraryManagementApp.services.impl;

import com.example.libraryManagementApp.Repository.BookRepository;
import com.example.libraryManagementApp.Repository.PatronRepository;
import com.example.libraryManagementApp.Repository.RecordRepository;
import com.example.libraryManagementApp.dto.RecordDto;
import com.example.libraryManagementApp.entity.Book;
import com.example.libraryManagementApp.entity.BorrowingRecord;
import com.example.libraryManagementApp.entity.Patron;
import com.example.libraryManagementApp.exceptions.BookApiException;
import com.example.libraryManagementApp.services.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    private BookRepository bookRepository;

    private PatronRepository patronRepository;

    private RecordRepository recordRepository;

    @Autowired
    public BorrowingRecordServiceImpl(BookRepository bookRepository, PatronRepository patronRepository, RecordRepository recordRepository) {

        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.recordRepository = recordRepository;
    }



    @Transactional
    @Override
    public RecordDto borrowBook(Long bookId , Long patronId) {

        //Catch the book  you need to borrow
        // and patron  who is need to borrow
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<Patron> optionalPatron = patronRepository.findById(patronId);

        if (optionalBook.isPresent() && optionalPatron.isPresent() && optionalBook.get().isAvailable()){

            //if we find them and the book is available
            // we create a borrow record and store  the book and patron in database
            BorrowingRecord record = new BorrowingRecord();
            record.setBook(optionalBook.get());
            record.setPatron(optionalPatron.get());
            record.setBorrowedDate(LocalDate.now());
            record.setDueDate(LocalDate.now().plusDays(7));
            record.setReturned(false);
            BorrowingRecord savedRecord = recordRepository.save(record);

            // Update book availability

            optionalBook.get().setAvailable(false);
            bookRepository.save(optionalBook.get());

            // For returning a dto
            RecordDto recordDto = new RecordDto();
            recordDto.setTitle(savedRecord.getBook().getTitle());
            recordDto.setId(savedRecord.getId());
            recordDto.setPatronName(savedRecord.getPatron().getName());
            recordDto.setDueDate(savedRecord.getDueDate());
            recordDto.setBorrowedDate(savedRecord.getBorrowedDate());
            return recordDto;

        }else {
            throw new BookApiException(HttpStatus.BAD_REQUEST , "We can not find this record .. !");
        }
    }

    @Transactional
    public RecordDto returnBook(Long bookId , Long patronId){
        Optional<BorrowingRecord> record = recordRepository.findByBookIdAndPatronId(bookId , patronId);
        if (record.isPresent() && !record.get().isReturned()){
            BorrowingRecord borrowingRecord = record.get();
            borrowingRecord.getBook().setAvailable(true);
            borrowingRecord.setReturned(true);
            BorrowingRecord savedRecord = recordRepository.save(borrowingRecord);

            RecordDto recordDto = new RecordDto();
            recordDto.setId(savedRecord.getId());
            recordDto.setTitle(savedRecord.getBook().getTitle());
            recordDto.setPatronName(savedRecord.getPatron().getName());
            recordDto.setBorrowedDate(savedRecord.getBorrowedDate());
            recordDto.setDueDate(savedRecord.getDueDate());
            //return ResponseEntity.ok(recordDto);
            return recordDto;
        }
        throw new BookApiException(HttpStatus.BAD_REQUEST , "We can not find this record .. !");
    }

}
