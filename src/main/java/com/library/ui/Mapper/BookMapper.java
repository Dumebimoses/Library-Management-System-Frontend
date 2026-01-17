package com.library.ui.Mapper;

import com.library.ui.DTO.BookDTO;
import com.library.ui.model.Book;

import java.time.LocalDate;

public class BookMapper {

    /**
     * Convert BookDTO (from API) to Book (for UI)
     */
    public static Book toModel(BookDTO dto) {
        if (dto == null) return null;

        return new Book(
                dto.getId(),
                dto.getTitle(),
                dto.getAuthor(),
                dto.getIsbn(),
                dto.getPublishedDate()
        );
    }

    /**
     * Convert Book (from UI) to BookDTO (for API)
     */
    public static BookDTO toDTO(Book book) {
        if (book == null) return null;

        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());

        LocalDate date = book.getPublishedDate();
        dto.setPublishedDate(date != null ? date.toString() : null);

        return dto;
    }
}