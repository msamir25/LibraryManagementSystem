package com.example.libraryManagementApp.controller;

import com.example.libraryManagementApp.dto.BookDto;
import com.example.libraryManagementApp.dto.RecordDto;
import com.example.libraryManagementApp.services.BookService;
import com.example.libraryManagementApp.services.BorrowingRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(controllers = BorrowingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BorrowControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BorrowingController borrowingController;


    @MockBean
    private BorrowingRecordService recordService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void BorrowController_BorrowBook_ReturnCreated() throws Exception {

        Long bookId  =100L;
        Long patronId = 100L;

        RecordDto recordDto = new RecordDto();
        recordDto.setId(100L);
        recordDto.setTitle("Days");
        recordDto.setPatronName("alex");

        when(recordService.borrowBook(100L , 100L)).thenReturn(recordDto);
        mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}" , 100L , 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.patronName", CoreMatchers.is(recordDto.getPatronName())));
        //.andDo(MockMvcResultHandlers.print()); // that is for check the value of content of request and print it to console
    }




}
