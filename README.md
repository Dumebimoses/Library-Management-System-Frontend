Library Management System - Frontend

A modern JavaFX-based desktop application for managing library books with full CRUD operations and real-time backend integration.

 Features

- Book Management
  - Add new books with title, author, ISBN, and published date
  - Update existing book information
  - Delete books with confirmation dialog
  - View all books in a sortable table

- Search & Filter
  - Real-time search by book title
  - Quick clear and refresh functionality

- User Interface
  - Clean, modern JavaFX interface
  - Responsive table view with auto-resizing columns
  - Form validation with user-friendly error messages
  - Date picker for easy date selection

- Backend Integration
  - RESTful API communication
  - Asynchronous operations to prevent UI freezing
  - Proper error handling and user feedback



Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17+ | Programming language |
| JavaFX | 17.0.6 | UI framework |
| Maven | 3.8+ | Build tool & dependency management |
| Gson | 2.10.1 | JSON serialization/deserialization |
| Java HTTP Client | Built-in | REST API communication |

Prerequisites

Before running this application, ensure you have:

1. Java Development Kit (JDK) 17 or higher
2. Apache Maven 3.8+
3. JavaFX SDK(automatically managed by Maven)
4. Backend API running (typically on `http://localhost:8080`

Project Structure

library-management-system-frontend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── mozay.library_management_system_frontend/
│   │   │   │   └── LibraryApp.java                 # Main application entry point
│   │   │   └── com.library.ui/
│   │   │       ├── controller/
│   │   │       │   └── MainController.java         # UI controller
│   │   │       ├── model/
│   │   │       │   └── Book.java                   # JavaFX model with properties
│   │   │       ├── dto/
│   │   │       │   └── BookDTO.java                # Plain data transfer object
│   │   │       ├── mapper/
│   │   │       │   └── BookMapper.java             # DTO ↔ Model converter
│   │   │       └── service/
│   │   │           └── BookApiService.java         # REST API client
│   │   └── resources/
│   │       └── view/
│   │           └── main-view.fxml                  # UI layout definition
│   └── module-info.java                             # Java module configuration
├── pom.xml                                          # Maven configuration
└── README.md                                        # This file

Installation

1. Clone the Repository

git clone https://github.com/Dumebimoses/Library-Management-System-Frontend.git
cd library-management-system-frontend

2. Install Dependencies
mvn clean install
This will:
- Download all required dependencies
- Compile the Java source code
- Package the application

Configuration

Backend URL Configuration

Open src/main/java/com/library/ui/service/BookApiService.java and update the BASE_URL:

java
private static final String BASE_URL = "http://localhost:8080/api/books";

Common configurations:
- Local development: http://localhost:8080/api/books
- Remote server: http://your-server.com:8080/api/books
- Custom port: `http://localhost:3000/api/books`

Module Configuration

The module-info.java is already configured, but if you add new packages, update it:



Running the Application

Option 1: Using Maven

mvn javafx:run

Option 2: Using Java Command

mvn clean package
java --module-path target/classes -m mozay.library_management_system_frontend/mozay.library_management_system_frontend.LibraryApp

Option 3: Using IDE (IntelliJ IDEA)

1. Open the project in IntelliJ IDEA
2. Right-click on LibraryApp.java
3. Select Run 'LibraryApp.main()'

Usage Guide

Adding a Book

1. Fill in all required fields in the right panel:
   - Title: Book title
   - Author: Author name
   - ISBN: ISBN number
   - Published Date: Select from date picker

2. Click the Add Book button
3. The book will appear in the table upon successful creation

 Updating a Book

1. Click on a book in the table to select it
2. Modify the fields in the right panel
3. Click the Update Book button
4. Changes will be reflected in the table

Deleting a Book

1. Select a book from the table
2. Click the Delete Book button
3. Confirm the deletion in the dialog
4. The book will be removed from the table

Searching for Books

1. Enter a search term in the search field (searches by title)
2. Click the Search button
3. The table will display matching results
4. Click Clear to show all books again

Refreshing the List

Click the Refresh button to reload all books from the backend.
