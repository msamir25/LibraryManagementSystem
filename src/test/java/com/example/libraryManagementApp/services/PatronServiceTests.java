package com.example.libraryManagementApp.services;

import com.example.libraryManagementApp.Repository.PatronRepository;
import com.example.libraryManagementApp.dto.BookDto;
import com.example.libraryManagementApp.dto.PatronDto;
import com.example.libraryManagementApp.entity.Book;
import com.example.libraryManagementApp.entity.BorrowingRecord;
import com.example.libraryManagementApp.entity.Patron;
import com.example.libraryManagementApp.services.impl.PatronServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.Banner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatronServiceTests {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronServiceImpl patronService;

    @Test
    public void PatronService_CreatePatron_ReturnsPatronDto() {

        Patron patron = new Patron();
        patron.setPhone("01029393339");
        patron.setAddress("cairo");
        patron.setEmail("ali@yahoo.com");
        patron.setName("ali");

        PatronDto patronDto = new PatronDto();
        patronDto.setPhone("01029393339");
        patronDto.setAddress("cairo");
        patronDto.setEmail("ali@yahoo.com");
        patronDto.setName("ali");

        when(patronRepository.save(Mockito.any(Patron.class))).thenReturn(patron);

        PatronDto savedPatron = patronService.createPatron(patronDto);

        Assertions.assertThat(savedPatron).isNotNull();


    }

    @Test
    public void PatronService_GetAll_ReturnPatronDtos(){
        List<Patron> patrons = new ArrayList<>();

        when(patronRepository.findAll()).thenReturn(patrons);

        List<PatronDto> patronDtos = patronService.getAllPatrons();
        Assertions.assertThat(patronDtos).isNotNull();
    }

    @Test
    public void BookService_getPatronById_ReturnPatronDto(){
        Long id = 1L;

        Patron patron = new Patron();
        patron.setPhone("01029393339");
        patron.setAddress("cairo");
        patron.setEmail("ali@yahoo.com");
        patron.setName("ali");

        when(patronRepository.findById(id)).thenReturn(Optional.ofNullable(patron));
        PatronDto patronDto = patronService.getPatronById(id);
        Assertions.assertThat(patronDto).isNotNull();
        Assertions.assertThat(patronDto.getName()).isEqualTo("ali");
    }

    @Test
    public void BookService_UpdateBook_ReturnBookDto() {
        Long id = 1L;

        Patron patron = new Patron();
        patron.setPhone("01029393339");
        patron.setAddress("cairo");
        patron.setEmail("ali@yahoo.com");
        patron.setName("ali");

        PatronDto patronDto = new PatronDto();
        patronDto.setPhone("01029393339");
        patronDto.setAddress("cairo");
        patronDto.setEmail("ali@yahoo.com");
        patronDto.setName("ali");

        when(patronRepository.findById(id)).thenReturn(Optional.ofNullable(patron));
        when(patronRepository.save(Mockito.any(Patron.class))).thenReturn(patron);

        PatronDto updatePatron = patronService.updatePatronById(patronDto, id);
        Assertions.assertThat(updatePatron).isNotNull();
    }

    @Test
    public void PatronService_DeletePatron_ReturnNothing() {
        Long id = 1L;

        Patron patron = new Patron();
        patron.setPhone("01029393339");
        patron.setAddress("cairo");
        patron.setEmail("ali@yahoo.com");
        patron.setName("ali");

        when(patronRepository.findById(id)).thenReturn(Optional.ofNullable(patron));

        doNothing().when(patronRepository).delete(patron);
        assertAll(() -> patronService.deleteById(id));
    }


}
