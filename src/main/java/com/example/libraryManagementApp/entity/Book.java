package com.example.libraryManagementApp.entity;

import com.example.libraryManagementApp.dto.BookDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title" ,nullable = false)
    private String title;

    @Column(name = "author" ,  nullable = false)
    private String author;

    @Column(name = "publication_year")
    private int publicationYear;

    @Column(name = "ISBN")
    private String ISBN;

   // @Column(name = "is_available", columnDefinition = "DEFAULT true")
    private boolean available = true ; // Default: available for borrowing



    public BookDto getDto(){
        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setISBN(ISBN);
        bookDto.setAuthor(author);
        bookDto.setTitle(title);
        bookDto.setPublicationYear(publicationYear);
        bookDto.setAvailable(available);

        return bookDto;
    }

}
