package com.example.libraryManagementApp.services;

import com.example.libraryManagementApp.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto bookDto);


    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    BookDto updateBookById(BookDto bookDto ,  Long id);

    void deleteById(Long id);
}
