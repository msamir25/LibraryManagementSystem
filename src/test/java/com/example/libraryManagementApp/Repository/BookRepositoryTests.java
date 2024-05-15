package com.example.libraryManagementApp.Repository;

import com.example.libraryManagementApp.entity.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BookRepositoryTests {
    @Autowired
    private BookRepository bookRepository;


    @Test
    public void BookRepository_SaveAll_ReturnSavedBook(){
        Book book = new Book();
        book.setAuthor("Ali");
        book.setTitle("Days");
        book.setISBN("1jwkqkw13u193ujkccccc");
        book.setPublicationYear(2000);
        Book savedBook = bookRepository.save(book);

        Assertions.assertThat(savedBook).isNotNull();
    }

    @Test
    public void BookRepository_GetAll_ReturnSavedBooks(){
        //arrange
        Book book1 = new Book();
        book1.setAuthor("Ali");
        book1.setTitle("Days");
        book1.setISBN("1jwkqkw13u193ujkccccc");
        book1.setPublicationYear(2000);

        Book book2 = new Book();
        book2.setAuthor("Ali1");
        book2.setTitle("Days1");
        book2.setISBN("1jwkqk111w13u193ujkccccc");
        book2.setPublicationYear(2001);
        //act
        Book savedBook = bookRepository.save(book1);
        Book savedBook2 = bookRepository.save(book2);

        List<Book> books = bookRepository.findAll();
        //assert
        Assertions.assertThat(books).isNotNull();


    }

    @Test
    public void BookRepository_FindById_ReturnSavedBook(){
        //arrange
        Book book = new Book();
        book.setAuthor("Ali2");
        book.setTitle("Days2");
        book.setISBN("1jwkqkw13u193ujkccccc22");
        book.setPublicationYear(2000);
        //act
        bookRepository.save(book);
        Book savedBook = bookRepository.findById(book.getId()).get();

        //assert
        Assertions.assertThat(savedBook).isNotNull();
    }


    @Test
    public void BookRepository_UpdateById_ReturnSavedBook(){
        //arrange
        Book book = new Book();
        book.setAuthor("Ali2");
        book.setTitle("Days2");
        book.setISBN("1jwkqkw13u193ujkccccc22");
        book.setPublicationYear(2000);
        //act
        bookRepository.save(book);
        Book savedBook = bookRepository.findById(book.getId()).get();
        savedBook.setTitle("hany");
        savedBook.setAuthor("mohamed");
        Book updatedbook = bookRepository.save(savedBook);

        //assert
        Assertions.assertThat(updatedbook.getTitle()).isEqualTo("hany");
        Assertions.assertThat(updatedbook.getAuthor()).isEqualTo("mohamed");
    }

    @Test
    public void BookRepository_DeleteById_ReturnEmptyBook(){
        //arrange
        Book book = new Book();
        book.setAuthor("Ali2");
        book.setTitle("Days2");
        book.setISBN("1jwkqkw13u193ujkccccc22");
        book.setPublicationYear(2000);
        //act
        bookRepository.save(book);
        Book savedBook = bookRepository.findById(book.getId()).get();

         bookRepository.delete(savedBook);

        Optional<Book> optionalBook = bookRepository.findById(savedBook.getId());
        //assert
        Assertions.assertThat(optionalBook).isEmpty();
    }


}
