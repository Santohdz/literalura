package com.literalura.dto;

import java.util.List;

public class BookDTO {

    private Long id;  // gutendexId o id
    private String title;
    private String language;
    private List<AuthorDTO> authors; // si manejas autores aquí

    public BookDTO() {
        // Constructor vacío necesario para frameworks (Jackson, JPA, etc)
    }

    // Constructor con parámetros para facilitar creación
    public BookDTO(String title, String language, Long id) {
        this.title = title;
        this.language = language;
        this.id = id;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }
}
