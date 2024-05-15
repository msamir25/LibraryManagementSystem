package com.example.libraryManagementApp.controller;

import com.example.libraryManagementApp.dto.BookDto;
import com.example.libraryManagementApp.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books")
    public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDto ){
        return new ResponseEntity<>(bookService.createBook(bookDto) , HttpStatus.CREATED);

    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }


    @PutMapping("/books/{id}")
    public ResponseEntity<BookDto> updateBook(@RequestBody @Valid BookDto bookDto ,
                                              @PathVariable(name = "id") Long id){

        return ResponseEntity.ok(bookService.updateBookById(bookDto , id));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(name = "id") Long id){
        bookService.deleteById(id);
        return new ResponseEntity<>("Book is Deleted Successfully" , HttpStatus.OK);
    }








}
