package com.arobs.internship.library.business;

import com.arobs.internship.library.dtos.BookDTO;
import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.handler.ValidationException;

import java.util.List;
import java.util.Set;

public interface BookService {
    void insertBook(Book book) throws ValidationException;

    List<BookDTO> findBooks();

    BookDTO findBookById(int id) throws ValidationException;

    void updateBook(String description, Set<TagDTO> tagDTOSet, int id) throws ValidationException;

    void deleteBook(String title, String author) throws ValidationException;

    Book dtoToBook(BookDTO bookDTO);

    BookDTO bookToDto(Book book);

}
