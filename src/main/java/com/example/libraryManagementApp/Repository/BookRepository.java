package com.example.libraryManagementApp.Repository;

import com.example.libraryManagementApp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository< Book,Long> {
}
