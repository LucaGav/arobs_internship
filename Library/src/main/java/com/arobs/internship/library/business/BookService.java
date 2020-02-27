package com.arobs.internship.library.business;

import com.arobs.internship.library.dtos.BookDTO;
import com.arobs.internship.library.entities.book.Book;

import java.util.List;

public interface BookService {
    void insertBook(Book book);

    List<BookDTO> findBooks();

    BookDTO findBookById(int id);

    void updateBook(String description, int id);

    void deleteBook(String title, String author);

    Book dtoToBook(BookDTO bookDTO);

    BookDTO bookToDto(Book book);

}
