package com.literalura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.dto.BookDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GutendexClient {

    private static final String BASE_URL = "https://gutendex.com/books";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GutendexClient() {
        this.httpClient = HttpClient.newHttpClient(); // crea el cliente
        this.objectMapper = new ObjectMapper();       // Jackson para JSON
    }

    public List<BookDTO> getBooks() {
        return getBooksFromApi(BASE_URL);
    }

    public List<BookDTO> getBooksFromApi(String url) {
        List<BookDTO> books = new ArrayList<>();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            JsonNode root = objectMapper.readTree(json);
            JsonNode results = root.get("results");

            if (results.isArray()) {
                for (JsonNode node : results) {
                    BookDTO book = objectMapper.treeToValue(node, BookDTO.class);
                    books.add(book);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return books;
    }

    public void printBooks(List<BookDTO> books) {
        books.forEach(System.out::println);
    }

}


