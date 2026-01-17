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

git clone https://github.com/yourusername/library-management-system-frontend.git
cd library-management-system-frontend
```

### 2. Install Dependencies

```bash
mvn clean install
```

This will:
- Download all required dependencies
- Compile the Java source code
- Package the application

---

## ⚙️ Configuration

### Backend URL Configuration

Open `src/main/java/com/library/ui/service/BookApiService.java` and update the `BASE_URL`:

```java
private static final String BASE_URL = "http://localhost:8080/api/books";
```

**Common configurations:**
- Local development: `http://localhost:8080/api/books`
- Remote server: `http://your-server.com:8080/api/books`
- Custom port: `http://localhost:3000/api/books`

### Module Configuration

The `module-info.java` is already configured, but if you add new packages, update it:

```java
module mozay.library_management_system_frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;

    opens com.library.ui.controller to javafx.fxml;
    opens com.library.ui.model to javafx.base;
    opens com.library.ui.dto to com.google.gson;
    
    exports mozay.library_management_system_frontend;
    exports com.library.ui.controller;
    exports com.library.ui.model;
    exports com.library.ui.dto;
    exports com.library.ui.mapper;
}
```

---

## ▶️ Running the Application

### Option 1: Using Maven

```bash
mvn javafx:run
```

### Option 2: Using Java Command

```bash
mvn clean package
java --module-path target/classes -m mozay.library_management_system_frontend/mozay.library_management_system_frontend.LibraryApp
```

### Option 3: Using IDE (IntelliJ IDEA)

1. Open the project in IntelliJ IDEA
2. Right-click on `LibraryApp.java`
3. Select **Run 'LibraryApp.main()'**

---

## 📖 Usage Guide

### Adding a Book

1. Fill in all required fields in the right panel:
   - **Title**: Book title
   - **Author**: Author name
   - **ISBN**: ISBN number
   - **Published Date**: Select from date picker

2. Click the **Add Book** button
3. The book will appear in the table upon successful creation

### Updating a Book

1. Click on a book in the table to select it
2. Modify the fields in the right panel
3. Click the **Update Book** button
4. Changes will be reflected in the table

### Deleting a Book

1. Select a book from the table
2. Click the **Delete Book** button
3. Confirm the deletion in the dialog
4. The book will be removed from the table

### Searching for Books

1. Enter a search term in the search field (searches by title)
2. Click the **Search** button
3. The table will display matching results
4. Click **Clear** to show all books again

### Refreshing the List

Click the **Refresh** button to reload all books from the backend.

---

## 🏗 Architecture

### Design Pattern: MVC with DTO Pattern

```
┌─────────────────┐
│   LibraryApp    │  Application Entry Point
└────────┬────────┘
         │
    ┌────▼──────────┐
    │ MainController│  Handles UI events & user interactions
    └────┬──────────┘
         │
    ┌────▼──────────┐
    │BookApiService │  Communicates with REST API
    └────┬──────────┘
         │
    ┌────▼──────────┐
    │  BookMapper   │  Converts between DTO ↔ Model
    └────┬──────────┘
         │
    ┌────▼──────┬────────┐
    │   BookDTO │  Book  │
    │  (JSON)   │  (UI)  │
    └───────────┴────────┘
```

### Key Components

| Component | Responsibility |
|-----------|---------------|
| **LibraryApp** | Application entry point, loads FXML |
| **MainController** | UI logic, event handling, validation |
| **Book** | JavaFX model with observable properties for UI binding |
| **BookDTO** | Plain Java object for JSON serialization |
| **BookMapper** | Converts between Book and BookDTO |
| **BookApiService** | HTTP client for backend communication |

### Why Separate DTO and Model?

- **Book (Model)**: Uses JavaFX `Property` classes for automatic UI updates
- **BookDTO**: Plain fields that work with Gson for JSON serialization
- **Problem**: Gson cannot serialize JavaFX properties
- **Solution**: Mapper converts between them

---

## 🐛 Troubleshooting

### Common Issues

#### 1. **Application won't start - "Location is not set"**

**Cause**: FXML file path is incorrect

**Solution**: Verify the FXML file is at `src/main/resources/view/main-view.fxml`

```bash
# Check file exists
ls src/main/resources/view/main-view.fxml
```

#### 2. **"Connection refused" or "Failed to fetch books"**

**Cause**: Backend API is not running or URL is incorrect

**Solution**:
- Verify backend is running: `curl http://localhost:8080/api/books`
- Check `BASE_URL` in `BookApiService.java`
- Ensure no firewall is blocking the connection

#### 3. **"Module not found" errors**

**Cause**: Missing JavaFX modules

**Solution**: Rebuild the project
```bash
mvn clean install
```

#### 4. **Date parsing errors**

**Cause**: Date format mismatch between frontend and backend

**Solution**: Ensure backend returns dates in `yyyy-MM-dd` format

#### 5. **Table not updating after operations**

**Cause**: Observable list not properly bound

**Solution**: Verify `bookTable.setItems(bookList)` is called in `initialize()`

### Debug Mode

Enable detailed logging by adding to `LibraryApp.java`:

```java
System.setProperty("javafx.verbose", "true");
```

### Backend Health Check

Test your backend API before running the frontend:

```bash
# Get all books
curl http://localhost:8080/api/books

# Add a book (example)
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","author":"Author","isbn":"123","publishedDate":"2024-01-01"}'
```

---

## 🤝 Contributing

### Development Setup

1. Fork the repository
2. Create a feature branch
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Make your changes
4. Run tests (if available)
5. Commit with clear messages
   ```bash
   git commit -m "Add: feature description"
   ```
6. Push to your fork
   ```bash
   git push origin feature/your-feature-name
   ```
7. Create a Pull Request

### Code Style

- Follow Java naming conventions
- Use meaningful variable names
- Add JavaDoc comments for public methods
- Keep methods focused and concise
- Handle exceptions appropriately

---

## 📝 API Endpoints Expected

This frontend expects the following backend endpoints:

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/books` | Get all books |
| POST | `/api/books` | Create a new book |
| GET | `/api/books/{id}` | Get book by ID |
| PUT | `/api/books/{id}` | Update book |
| DELETE | `/api/books/{id}` | Delete book |
| GET | `/api/books/search?title={query}` | Search books by title |

### Expected JSON Format

**Request (POST/PUT):**
```json
{
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "isbn": "978-0743273565",
  "publishedDate": "1925-04-10"
}
```

**Response (GET):**
```json
{
  "id": 1,
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "isbn": "978-0743273565",
  "publishedDate": "1925-04-10"
}
```

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👥 Authors

- **Your Name** - Initial work

---

## 🙏 Acknowledgments

- JavaFX community for excellent documentation
- Gson library for JSON serialization
- Maven for dependency management

---

## 📞 Support

If you encounter any issues or have questions:

1. Check the [Troubleshooting](#troubleshooting) section
2. Review existing GitHub issues
3. Create a new issue with:
   - Error messages
   - Steps to reproduce
   - Your environment details (OS, Java version, etc.)

---

**Happy Coding! 🚀**
