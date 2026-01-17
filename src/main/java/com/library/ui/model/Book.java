package com.library.ui.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Book {
    private final LongProperty id;
    private final StringProperty title;
    private final StringProperty author;
    private final StringProperty isbn;
    private final ObjectProperty<LocalDate> publishedDate;

    public Book() {
        this(0L, "", "", "", null);
    }


    public Book(Long id, String title, String author, String isbn, String publishedDateStr) {
        this.id = new SimpleLongProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.isbn = new SimpleStringProperty(isbn);

        // Convert String to LocalDate
        LocalDate date = null;
        if (publishedDateStr != null && !publishedDateStr.isEmpty()) {
            try {
                date = LocalDate.parse(publishedDateStr);
            } catch (Exception e) {
                date = null;
            }
        }
        this.publishedDate = new SimpleObjectProperty<>(date);
    }

    // Constructor without ID (for new books)
    public Book(String title, String author, String isbn, String publishedDateStr) {
        this(0L, title, author, isbn, publishedDateStr);
    }

    // ID Property
    public Long getId() {
        return id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    // Title Property
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    // Author Property
    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public StringProperty authorProperty() {
        return author;
    }

    // ISBN Property
    public String getIsbn() {
        return isbn.get();
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    public StringProperty isbnProperty() {
        return isbn;
    }

    // Published Date Property
    public LocalDate getPublishedDate() {
        return publishedDate.get();
    }

    public void setPublishedDate(LocalDate date) {
        this.publishedDate.set(date);
    }


    public String getPublishedDateAsString() {
        LocalDate date = publishedDate.get();
        return date != null ? date.toString() : "";
    }

    public void setPublishedDate(String dateStr) {
        try {
            this.publishedDate.set(LocalDate.parse(dateStr));
        } catch (Exception e) {
            this.publishedDate.set(null);
        }
    }

    public ObjectProperty<LocalDate> publishedDateProperty() {
        return publishedDate;
    }
}