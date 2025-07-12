package com.literalura.repository;

import com.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByLanguageIgnoreCase(String language);
    
    // Método para buscar un libro por su ID de Gutendex
    Optional<Book> findByGutendexId(Long gutendexId);
    
    // Método para verificar si un libro ya existe por su ID de Gutendex
    boolean existsByGutendexId(Long gutendexId);
    
    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors")
    List<Book> findAllWithAuthors();
    
    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors WHERE b.language = :language")
    List<Book> findByLanguageWithAuthors(String language);
}
