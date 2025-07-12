package com.literalura.client;

import com.literalura.dto.BookDTO;
import com.literalura.dto.AuthorDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GutendexClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String GUTENDEX_API_URL = "https://gutendex.com/books/";

    public List<BookDTO> buscarLibroPorTitulo(String titulo) {
        String url = GUTENDEX_API_URL + "?search=" + titulo.replace(" ", "%20");
        GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);

        if (response == null || response.getResults() == null) {
            return Collections.emptyList();
        }

        return response.getResults().stream()
                .map(dto -> {
                    BookDTO book = new BookDTO();
                    book.setId(dto.getId());
                    book.setTitle(dto.getTitle());
                    book.setLanguage(dto.getLanguages().isEmpty() ? "en" : dto.getLanguages().get(0));
                    
                    // Mapear autores
                    if (dto.getAuthors() != null) {
                        List<AuthorDTO> autores = dto.getAuthors().stream()
                                .map(authorDto -> {
                                    AuthorDTO author = new AuthorDTO();
                                    author.setName(authorDto.getName());
                                    author.setBirthYear(authorDto.getBirthYear());
                                    author.setDeathYear(authorDto.getDeathYear());
                                    return author;
                                })
                                .collect(Collectors.toList());
                        book.setAuthors(autores);
                    }
                    
                    return book;
                })
                .collect(Collectors.toList());
    }

    // Clases internas para mapear la respuesta de la API
    private static class GutendexResponse {
        private List<BookDto> results;
        
        public List<BookDto> getResults() { 
            return results != null ? results : Collections.emptyList(); 
        }
        
        public void setResults(List<BookDto> results) { 
            this.results = results; 
        }
    }

    private static class BookDto {
        private Long id;
        private String title;
        private List<String> languages;
        private List<AuthorDto> authors;
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public List<String> getLanguages() { 
            return languages != null ? languages : Collections.emptyList(); 
        }
        public void setLanguages(List<String> languages) { this.languages = languages; }
        
        public List<AuthorDto> getAuthors() { 
            return authors != null ? authors : Collections.emptyList(); 
        }
        public void setAuthors(List<AuthorDto> authors) { this.authors = authors; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class AuthorDto {
        private String name;
        
        @JsonProperty("birth_year")
        private Integer birthYear;
        
        @JsonProperty("death_year")
        private Integer deathYear;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public Integer getBirthYear() { return birthYear; }
        public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }
        
        public Integer getDeathYear() { return deathYear; }
        public void setDeathYear(Integer deathYear) { this.deathYear = deathYear; }
    }
}
