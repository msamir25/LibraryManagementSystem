package com.example.libraryManagementApp.Repository;

import com.example.libraryManagementApp.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends JpaRepository<Patron , Long> {
}
