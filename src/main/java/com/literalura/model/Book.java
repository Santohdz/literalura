package com.literalura.model;

import com.literalura.util.AppConstants;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entidad que representa un libro en el sistema.
 * Un libro puede tener múltiples autores.
 */
@Entity
@Table(name = "books", 
       indexes = {
           @Index(name = "idx_book_title", columnList = "title"),
           @Index(name = "idx_book_language", columnList = "language")
       })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "gutendex_id", unique = true, nullable = false)
    private Long gutendexId;
    
    @Column(nullable = false, length = AppConstants.MAX_TITLE_LENGTH)
    private String title;
    
    @Column(length = AppConstants.MAX_LANGUAGE_LENGTH)
    private String language;
    
    @ManyToMany(fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
        name = "book_authors",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @JsonIgnoreProperties("books") // Evita la recursión infinita
    private Set<Author> authors = new HashSet<>();
    
    // Constructores
    public Book() {}
    
    public Book(Long gutendexId, String title, String language) {
        this.gutendexId = gutendexId;
        this.title = title;
        this.language = language;
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getGutendexId() {
        return gutendexId;
    }
    
    public void setGutendexId(Long gutendexId) {
        this.gutendexId = gutendexId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public Set<Author> getAuthors() {
        return authors;
    }
    
    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
    
    // Métodos de utilidad
    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }
    
    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }
    
    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(gutendexId, book.gutendexId) &&
               Objects.equals(title, book.title);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(gutendexId, title);
    }
    
    @Override
    public String toString() {
        try {
            String autores = authors.stream()
                .map(Author::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Autor desconocido");
                
            return String.format("%s | Idioma: %s | Autores: %s", 
                title, 
                language != null ? language : "No especificado",
                autores);
        } catch (Exception e) {
            return String.format("%s | Idioma: %s", 
                title != null ? title : "Sin título", 
                language != null ? language : "No especificado");
        }
    }
}
