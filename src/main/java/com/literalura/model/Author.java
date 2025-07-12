package com.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.literalura.util.AppConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entidad que representa un autor en el sistema.
 * Un autor puede tener múltiples libros asociados.
 */
@Entity
@Table(name = "authors",
       indexes = {
           @Index(name = "idx_author_name", columnList = "name"),
           @Index(name = "idx_author_birth_year", columnList = "birth_year"),
           @Index(name = "idx_author_death_year", columnList = "death_year")
       })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = AppConstants.MAX_NAME_LENGTH)
    @NotBlank(message = "El nombre del autor es obligatorio")
    @Size(max = AppConstants.MAX_NAME_LENGTH, 
          message = "El nombre no puede tener más de " + AppConstants.MAX_NAME_LENGTH + " caracteres")
    private String name;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "death_year")
    private Integer deathYear;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("authors") // Evita la recursión infinita
    private Set<Book> books = new HashSet<>();

    // Constructores
    public Author() {}

    public Author(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    // Métodos de utilidad
    public void addBook(Book book) {
        if (book != null) {
            this.books.add(book);
            book.getAuthors().add(this);
        }
    }

    public void removeBook(Book book) {
        if (book != null) {
            this.books.remove(book);
            book.getAuthors().remove(this);
        }
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name) &&
               Objects.equals(birthYear, author.birthYear) &&
               Objects.equals(deathYear, author.deathYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthYear, deathYear);
    }

    @Override
    public String toString() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Autor: ").append(name);
            
            if (birthYear != null || deathYear != null) {
                sb.append(" (");
                if (birthYear != null) {
                    sb.append(birthYear);
                }
                sb.append(" - ");
                if (deathYear != null) {
                    sb.append(deathYear);
                }
                sb.append(")");
            }
            
            if (books != null && !books.isEmpty()) {
                sb.append(" | Libros: ");
                String bookTitles = books.stream()
                    .map(Book::getTitle)
                    .limit(3)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("Sin libros");
                
                if (books.size() > 3) {
                    bookTitles += "... (y " + (books.size() - 3) + " más)";
                }
                
                sb.append(bookTitles);
            }
            
            return sb.toString();
        } catch (Exception e) {
            return "Autor: " + (name != null ? name : "Desconocido");
        }
    }
}
