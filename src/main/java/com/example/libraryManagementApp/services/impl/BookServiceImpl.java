package com.example.libraryManagementApp.services.impl;

import com.example.libraryManagementApp.Repository.BookRepository;
import com.example.libraryManagementApp.dto.BookDto;
import com.example.libraryManagementApp.entity.Book;
import com.example.libraryManagementApp.exceptions.ResourceNotFoundException;
import com.example.libraryManagementApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {

        Book book = new Book();

        book.setAuthor(bookDto.getAuthor());
        book.setISBN(bookDto.getISBN());
        book.setPublicationYear(bookDto.getPublicationYear());
        book.setTitle(bookDto.getTitle());

        return bookRepository.save(book).getDto();
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(Book::getDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(" Book " , " id " , id));

        return book.getDto();
    }

    @Override
    public BookDto updateBookById(BookDto bookDto , Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(" Book " , " id " , id));

        book.setISBN(bookDto.getISBN());
        book.setPublicationYear(bookDto.getPublicationYear());
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());

        Book savedBook = bookRepository.save(book);
        return savedBook.getDto();

    }

    @Override
    public void deleteById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(" Book " , " id " , id));
        bookRepository.delete(book);
    }
}
