package com.example.libraryManagementApp.services;

import com.example.libraryManagementApp.Repository.BookRepository;
import com.example.libraryManagementApp.Repository.PatronRepository;
import com.example.libraryManagementApp.Repository.RecordRepository;
import com.example.libraryManagementApp.dto.RecordDto;
import com.example.libraryManagementApp.entity.Book;
import com.example.libraryManagementApp.entity.BorrowingRecord;
import com.example.libraryManagementApp.entity.Patron;
import com.example.libraryManagementApp.services.impl.BorrowingRecordServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordServiceImplTests {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private BorrowingRecordServiceImpl borrowingRecordService;

    @Test
    public void RecordService_BorrowBook_ReturnRecordDto() {
        Long bookId = 1L;
        Long patronId = 1L;

        Book book = new Book();
        book.setId(1L);
        book.setAuthor("Ali");
        book.setTitle("Days");
        book.setISBN("1jwkqkw13u193ujkccccc");
        book.setPublicationYear(2000);

        Patron patron = new Patron();
        patron.setPhone("01029393339");
        patron.setAddress("cairo");
        patron.setEmail("ali@yahoo.com");
        patron.setName("ali");

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowedDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(7));
        record.setReturned(false);


        when(patronRepository.findById(patronId)).thenReturn(Optional.ofNullable(patron));
        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        when(recordRepository.save(Mockito.any(BorrowingRecord.class))).thenReturn(record);

        RecordDto recordDto = borrowingRecordService.borrowBook(bookId, patronId);

        Assertions.assertThat(recordDto).isNotNull();

        Assertions.assertThat(recordDto.getPatronName()).isEqualTo(patron.getName());
        Assertions.assertThat(recordDto.getTitle()).isEqualTo(book.getTitle());


    }

    @Test
    public void RecordService_ReturnBook_ReturnRecordDto() {

        Long bookId = 1L;
        Long patronId = 1L;

        Book book = new Book();
        book.setId(1L);
        book.setAuthor("Ali");
        book.setTitle("Days");
        book.setISBN("1jwkqkw13u193ujkccccc");
        book.setPublicationYear(2000);

        Patron patron = new Patron();
        patron.setPhone("01029393339");
        patron.setAddress("cairo");
        patron.setEmail("ali@yahoo.com");
        patron.setName("ali");

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowedDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(7));
        record.setReturned(false);

        when(recordRepository.findByBookIdAndPatronId(bookId , patronId)).thenReturn(Optional.ofNullable(record));
        when(recordRepository.save(Mockito.any(BorrowingRecord.class))).thenReturn(record);

        //ResponseEntity<RecordDto> recordDto = borrowingRecordService.returnBook(bookId , patronId);

        RecordDto recordDto = borrowingRecordService.returnBook(bookId , patronId);
        Assertions.assertThat(recordDto).isNotNull();
        //Assertions.assertThat(recordDto.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}