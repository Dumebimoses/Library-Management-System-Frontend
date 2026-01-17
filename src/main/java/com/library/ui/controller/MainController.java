package com.library.ui.controller;

import com.library.ui.model.Book;
import com.library.ui.service.BookApiService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class MainController {

    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, Long> idColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> isbnColumn;
    @FXML private TableColumn<Book, LocalDate> dateColumn;

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField isbnField;
    @FXML private DatePicker dateField;
    @FXML private TextField searchField;

    private final BookApiService apiService = new BookApiService();
    private final ObservableList<Book> bookList = FXCollections.observableArrayList();
    private Book selectedBook;

    @FXML
    public void initialize() {
        // Set up table columns using property methods
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().publishedDateProperty());

        bookTable.setItems(bookList);

        // Listen for selection changes
        bookTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) populateForm(newVal);
                });

        loadBooks();
    }

    private void loadBooks() {
        new Thread(() -> {
            try {
                List<Book> books = apiService.getAllBooks();
                Platform.runLater(() -> {
                    bookList.setAll(books);
                    clearForm();
                });
            } catch (Exception e) {
                showError("Load Error", e.getMessage());
            }
        }).start();
    }

    private void populateForm(Book book) {
        selectedBook = book;
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        isbnField.setText(book.getIsbn());
        dateField.setValue(book.getPublishedDate());
    }

    private void clearForm() {
        selectedBook = null;
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        dateField.setValue(null);
        bookTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        if (!validateForm()) return;

        Book book = new Book(
                titleField.getText(),
                authorField.getText(),
                isbnField.getText(),
                dateField.getValue().toString()
        );

        new Thread(() -> {
            try {
                apiService.addBook(book);
                Platform.runLater(() -> {
                    loadBooks();
                    showInfo("Success", "Book added successfully");
                });
            } catch (Exception e) {
                showError("Add Error", e.getMessage());
            }
        }).start();
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        if (selectedBook == null) {
            showWarning("No Selection", "Select a book to update");
            return;
        }

        if (!validateForm()) return;

        selectedBook.setTitle(titleField.getText());
        selectedBook.setAuthor(authorField.getText());
        selectedBook.setIsbn(isbnField.getText());
        selectedBook.setPublishedDate(dateField.getValue());

        new Thread(() -> {
            try {
                apiService.updateBook(selectedBook.getId(), selectedBook);
                Platform.runLater(() -> {
                    loadBooks();
                    showInfo("Success", "Book updated successfully");
                });
            } catch (Exception e) {
                showError("Update Error", e.getMessage());
            }
        }).start();
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        Book selected = bookTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("No Selection", "Select a book to delete");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Book");
        confirm.setHeaderText("Delete Book");
        confirm.setContentText("Are you sure you want to delete '" + selected.getTitle() + "'?");

        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                new Thread(() -> {
                    try {
                        apiService.deleteBook(selected.getId());
                        Platform.runLater(() -> {
                            loadBooks();
                            showInfo("Deleted", "Book deleted successfully");
                        });
                    } catch (Exception e) {
                        showError("Delete Error", e.getMessage());
                    }
                }).start();
            }
        });
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            loadBooks();
            return;
        }

        new Thread(() -> {
            try {
                List<Book> results = apiService.searchBooks(query, null);
                Platform.runLater(() -> bookList.setAll(results));
            } catch (Exception e) {
                showError("Search Error", e.getMessage());
            }
        }).start();
    }

    @FXML
    private void handleClear(ActionEvent event) {
        searchField.clear();
        loadBooks();
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadBooks();
        showInfo("Refreshed", "Book list refreshed successfully");
    }

    private boolean validateForm() {
        if (titleField.getText().isBlank()) {
            showWarning("Validation Error", "Title is required");
            return false;
        }
        if (authorField.getText().isBlank()) {
            showWarning("Validation Error", "Author is required");
            return false;
        }
        if (isbnField.getText().isBlank()) {
            showWarning("Validation Error", "ISBN is required");
            return false;
        }
        if (dateField.getValue() == null) {
            showWarning("Validation Error", "Published date is required");
            return false;
        }
        return true;
    }

    private void showError(String title, String msg) {
        Platform.runLater(() -> alert(Alert.AlertType.ERROR, title, msg));
    }

    private void showWarning(String title, String msg) {
        Platform.runLater(() -> alert(Alert.AlertType.WARNING, title, msg));
    }

    private void showInfo(String title, String msg) {
        Platform.runLater(() -> alert(Alert.AlertType.INFORMATION, title, msg));
    }

    private void alert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}