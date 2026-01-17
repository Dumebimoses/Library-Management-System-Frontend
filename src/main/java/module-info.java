module mozay.library_management_system_frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;

    opens mozay.library_management_system_frontend to javafx.fxml;
    exports mozay.library_management_system_frontend;

    opens com.library.ui.controller to javafx.fxml;
    exports com.library.ui.controller;

    opens com.library.ui.model to javafx.base;
    exports com.library.ui.model;

    // Export DTO for Gson
    opens com.library.ui.DTO to com.google.gson;
    exports com.library.ui.DTO;

    exports com.library.ui.Mapper;
}