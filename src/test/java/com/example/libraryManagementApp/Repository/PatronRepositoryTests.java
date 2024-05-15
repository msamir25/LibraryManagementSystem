package com.example.libraryManagementApp.Repository;

import com.example.libraryManagementApp.entity.Book;
import com.example.libraryManagementApp.entity.Patron;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PatronRepositoryTests {

    @Autowired
    private PatronRepository patronRepository;

    @Test
    public void PatronRepository_SaveAll_ReturnSavedPatron(){
        Patron patron = new Patron();
        patron.setPhone("01029393339");
        patron.setAddress("cairo");
        patron.setEmail("ali@yahoo.com");
        patron.setName("ali");

        Patron savedPatron = patronRepository.save(patron);

        Assertions.assertThat(savedPatron).isNotNull();
        Assertions.assertThat(savedPatron.getName()).isEqualTo("ali");
    }


    @Test
    public void PatronRepository_GetAll_ReturnSavedPatrons(){
        Patron patron = new Patron();
        patron.setPhone("01029393339");
        patron.setAddress("cairo");
        patron.setEmail("ali@yahoo.com");
        patron.setName("ali");

        Patron patron2 = new Patron();
        patron2.setPhone("01029393339");
        patron2.setAddress("cairo");
        patron2.setEmail("ali@yahoo.com");
        patron2.setName("ali");

        Patron savedPatron = patronRepository.save(patron);
        Patron savedPatron2 = patronRepository.save(patron2);

        List<Patron>patrons = patronRepository.findAll();

        Assertions.assertThat(patrons).isNotNull();
    }



    @Test
    public void PatronRepository_FindById_ReturnSavedPatron(){
        Patron patron = new Patron();
        patron.setPhone("01029393339");
        patron.setAddress("cairo");
        patron.setEmail("ali@yahoo.com");
        patron.setName("ali");

         patronRepository.save(patron);

        Patron savedPatron = patronRepository.findById(patron.getId()).get();

        Assertions.assertThat(savedPatron).isNotNull();
        Assertions.assertThat(savedPatron.getName()).isEqualTo("ali");
    }



    @Test
    public void PatronRepository_UpdateById_ReturnSavedPatron(){
        Patron patron = new Patron();
        patron.setPhone("01029393339");
        patron.setAddress("cairo");
        patron.setEmail("ali@yahoo.com");
        patron.setName("ali");

        patronRepository.save(patron);

        Patron savedPatron = patronRepository.findById(patron.getId()).get();
        savedPatron.setName("mohamed");
        savedPatron.setAddress("Alex");
        savedPatron.setEmail("mohamed@yahoo.com");
        Patron updatedPatron = patronRepository.save(savedPatron);

        Assertions.assertThat(updatedPatron).isNotNull();
        Assertions.assertThat(updatedPatron.getName()).isEqualTo("mohamed");
        Assertions.assertThat(updatedPatron.getEmail()).isEqualTo("mohamed@yahoo.com");
        Assertions.assertThat(updatedPatron.getAddress()).isEqualTo("Alex");

    }

    @Test
    public void PatronRepository_DeleteById_ReturnEmptyPatron(){
        Patron patron = new Patron();
        patron.setPhone("01029393339");
        patron.setAddress("cairo");
        patron.setEmail("ali@yahoo.com");
        patron.setName("ali");

        patronRepository.save(patron);
        Patron savedPatron = patronRepository.findById(patron.getId()).get();

        patronRepository.delete(savedPatron);

        Optional<Patron> optionalPatron = patronRepository.findById(savedPatron.getId());
        Assertions.assertThat(optionalPatron).isEmpty();
    }


}
