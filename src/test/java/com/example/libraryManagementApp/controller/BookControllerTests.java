package com.example.libraryManagementApp.controller;

import com.example.libraryManagementApp.dto.BookDto;
import com.example.libraryManagementApp.entity.Book;
import com.example.libraryManagementApp.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static java.lang.reflect.Array.get;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookController bookController;


    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void BookController_CreateBook_ReturnCreated() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor("Ali");
        bookDto.setTitle("Days");
        bookDto.setISBN("1jwkqaldklkw");
        bookDto.setPublicationYear(2000);

        when(bookService.createBook(bookDto)).thenReturn(bookDto);
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto))
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(bookDto.getTitle())));
                //.andDo(MockMvcResultHandlers.print()); // that is for check the value of content of request and print it to console
    }


    @Test
    public void BookController_GetBookById_ReturnCreated() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId(100L);
        bookDto.setAuthor("Ali");
        bookDto.setTitle("Days");
        bookDto.setISBN("1jwkqaldklkw");
        bookDto.setPublicationYear(2000);

        when(bookService.getBookById(100L)).thenReturn(bookDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}" , 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(bookDto.getTitle())));
    }



    @Test
    public void BookController_GetAllBook_ReturnOk() throws Exception {

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setAuthor("Ali");
        bookDto.setTitle("Days");
        bookDto.setISBN("1jwkqkw13u193ujkccccc");
        bookDto.setPublicationYear(2000);

        BookDto bookDto2 = new BookDto();
        bookDto2.setId(2L);
        bookDto2.setAuthor("Ali Test");
        bookDto2.setTitle("Days Test");
        bookDto2.setISBN("1jwkqkw13u193ujkccccc");
        bookDto2.setPublicationYear(2000);

        List<BookDto> bookDtoList = Arrays.asList(bookDto , bookDto2);
        when(bookService.getAllBooks()).thenReturn(bookDtoList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", CoreMatchers.is(bookDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", CoreMatchers.is(bookDto2.getTitle())));
    }



    @Test
    public void BookController_DeleteBookById_ReturnNothing() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId(100L);
        bookDto.setAuthor("Ali");
        bookDto.setTitle("Days");
        bookDto.setISBN("1jwkqaldklkw");
        bookDto.setPublicationYear(2000);

        doNothing().when(bookService).deleteById(100L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}" , 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



    }






