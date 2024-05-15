package com.example.libraryManagementApp.services;

import com.example.libraryManagementApp.Repository.BookRepository;
import com.example.libraryManagementApp.dto.BookDto;
import com.example.libraryManagementApp.entity.Book;
import com.example.libraryManagementApp.entity.BorrowingRecord;
import com.example.libraryManagementApp.services.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;


    @Test
    public void BookService_CreateBook_ReturnsBookDto() {
        Book book = new Book();
        book.setAuthor("Ali");
        book.setTitle("Days");
        book.setISBN("1jwkqkw13u193ujkccccc");
        book.setPublicationYear(2000);

        BookDto book1 = new BookDto();
        book1.setAuthor("Ali");
        book1.setTitle("Days");
        book1.setISBN("1jwkqkw13u193ujkccccc");
        book1.setPublicationYear(2000);

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        BookDto savedBook = bookService.createBook(book1);

        Assertions.assertThat(savedBook).isNotNull();
    }

    @Test
    public void BookService_GetAll_ReturnBookDtos(){
        List<Book> books = new ArrayList<>();

        when(bookRepository.findAll()).thenReturn(books);

        List<BookDto> bookDtos = bookService.getAllBooks();
        Assertions.assertThat(bookDtos).isNotNull();
    }

    @Test
    public void BookService_getBookById_ReturnBookDto(){
        Long id = 1L;

        Book book = new Book();
        book.setId(1L);
        book.setAuthor("Ali");
        book.setTitle("Days");
        book.setISBN("1jwkqkw13u193ujkccccc");
        book.setPublicationYear(2000);

        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book));
        BookDto bookDto = bookService.getBookById(id);
        Assertions.assertThat(bookDto).isNotNull();

    }

    @Test
    public void BookService_UpdateBook_ReturnBookDto() {
        Long id = 1L;

        Book book = new Book();
        book.setId(1L);
        book.setAuthor("Ali");
        book.setTitle("Days");
        book.setISBN("1jwkqkw13u193ujkccccc");
        book.setPublicationYear(2000);

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setAuthor("Ali");
        bookDto.setTitle("Days");
        bookDto.setISBN("1jwkqkw13u193ujkccccc");
        bookDto.setPublicationYear(2000);

        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book));
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        BookDto updatedBook = bookService.updateBookById(bookDto, id);
        Assertions.assertThat(updatedBook).isNotNull();
    }

    @Test
    public void BookService_DeleteBook_ReturnNothing() {
        Long id = 1L;

        Book book = new Book();
        book.setId(1L);
        book.setAuthor("Ali");
        book.setTitle("Days");
        book.setISBN("1jwkqkw13u193ujkccccc");
        book.setPublicationYear(2000);

        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book));

        doNothing().when(bookRepository).delete(book);
        assertAll(() -> bookService.deleteById(id));
    }

    }
