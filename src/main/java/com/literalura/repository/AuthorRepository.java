package com.literalura.repository;

import com.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Buscar un autor por su nombre exacto
    Optional<Author> findByName(String name);
    
    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books")
    List<Author> findAllWithBooks();
    
    @Query("""
        SELECT DISTINCT a FROM Author a 
        LEFT JOIN FETCH a.books 
        WHERE (a.birthYear <= :year) 
        AND (a.deathYear IS NULL OR a.deathYear >= :year)
        """)
    List<Author> findAliveInYearWithBooks(Integer year);
}
