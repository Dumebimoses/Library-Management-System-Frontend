package com.library.ui.service;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.library.ui.DTO.BookDTO;
import com.library.ui.Mapper.BookMapper;
import com.library.ui.model.Book;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class BookApiService {

    private static final String BASE_URL = "http://localhost:8080/api/books";

    private final HttpClient httpClient;
    private final Gson gson;

    public BookApiService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
    }

    public List<Book> getAllBooks() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            List<BookDTO> dtos = gson.fromJson(response.body(),
                    new TypeToken<List<BookDTO>>(){}.getType());

            // Convert DTOs to Models
            return dtos.stream()
                    .map(BookMapper::toModel)
                    .collect(Collectors.toList());
        } else {
            throw new Exception("Failed to fetch books: " + response.statusCode());
        }
    }

    public Book addBook(Book book) throws Exception {
        // Convert model to DTO
        BookDTO dto = BookMapper.toDTO(book);
        String jsonBody = gson.toJson(dto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            BookDTO resultDto = gson.fromJson(response.body(), BookDTO.class);
            return BookMapper.toModel(resultDto);
        } else {
            throw new Exception("Failed to add book: " + response.statusCode() +
                    " - " + response.body());
        }
    }

    public Book updateBook(Long id, Book book) throws Exception {
        BookDTO dto = BookMapper.toDTO(book);
        String jsonBody = gson.toJson(dto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            BookDTO resultDto = gson.fromJson(response.body(), BookDTO.class);
            return BookMapper.toModel(resultDto);
        } else {
            throw new Exception("Failed to update book: " + response.statusCode() +
                    " - " + response.body());
        }
    }

    public void deleteBook(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new Exception("Failed to delete book: " + response.statusCode());
        }
    }

    public List<Book> searchBooks(String title, String author) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL + "/search?");

        if (title != null && !title.isEmpty()) {
            urlBuilder.append("title=").append(title);
        }
        if (author != null && !author.isEmpty()) {
            if (title != null && !title.isEmpty()) {
                urlBuilder.append("&");
            }
            urlBuilder.append("author=").append(author);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlBuilder.toString()))
                .GET()
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            List<BookDTO> dtos = gson.fromJson(response.body(),
                    new TypeToken<List<BookDTO>>(){}.getType());

            return dtos.stream()
                    .map(BookMapper::toModel)
                    .collect(Collectors.toList());
        } else {
            throw new Exception("Failed to search books: " + response.statusCode());
        }
    }
}