package com.literalura.service;

import com.literalura.client.GutendexClient;
import com.literalura.dto.BookDTO;
import com.literalura.dto.AuthorDTO;
import com.literalura.model.Book;
import com.literalura.model.Author;
import com.literalura.repository.AuthorRepository;
import com.literalura.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GutendexClient gutendexClient;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GutendexClient gutendexClient) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.gutendexClient = gutendexClient;
    }

    @Transactional(readOnly = true)
    public List<Book> listarLibros() {
        return bookRepository.findAllWithAuthors();
    }

    @Transactional
    public String buscarYGuardarLibroPorTitulo(String titulo) {
        List<BookDTO> booksDto = gutendexClient.buscarLibroPorTitulo(titulo);
        
        if (booksDto == null || booksDto.isEmpty()) {
            return "No se encontraron libros con el título: " + titulo;
        }
        
        // Tomar solo el primer libro de la lista
        BookDTO dto = booksDto.get(0);
        StringBuilder mensaje = new StringBuilder("Procesando libro: " + dto.getTitle() + "\n");
        
        // Verificar si el libro ya existe en la base de datos
        Optional<Book> existingBook = bookRepository.findByGutendexId(dto.getId());
        if (existingBook.isPresent()) {
            return "El libro ya existe en la base de datos: " + dto.getTitle();
        }

        // Crear el libro
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setLanguage(dto.getLanguage());
        book.setGutendexId(dto.getId());
        
        // Manejar los autores
        Set<Author> autores = new HashSet<>();
        if (dto.getAuthors() != null && !dto.getAuthors().isEmpty()) {
            for (AuthorDTO authorDto : dto.getAuthors()) {
                // Buscar si el autor ya existe
                Optional<Author> existingAuthor = authorRepository.findByName(authorDto.getName());
                Author author;
                
                if (existingAuthor.isPresent()) {
                    author = existingAuthor.get();
                    mensaje.append("- Autor existente encontrado: ").append(author.getName()).append("\n");
                } else {
                    // Crear nuevo autor si no existe
                    author = new Author();
                    author.setName(authorDto.getName());
                    author.setBirthYear(authorDto.getBirthYear());
                    author.setDeathYear(authorDto.getDeathYear());
                    mensaje.append("- Nuevo autor agregado: ").append(author.getName()).append("\n");
                }
                
                // Establecer la relación bidireccional
                author.getBooks().add(book);
                autores.add(author);
            }
            book.setAuthors(autores);
        }
        
        // Guardar el libro (esto guardará también los autores en cascada)
        Book savedBook = bookRepository.save(book);
        mensaje.append("\n✓ Libro agregado exitosamente: ").append(savedBook.getTitle());
        
        return mensaje.toString();
    }

    @Transactional(readOnly = true)
    public List<Book> listarLibrosPorIdioma(String idioma) {
        return bookRepository.findByLanguageWithAuthors(idioma);
    }
}
