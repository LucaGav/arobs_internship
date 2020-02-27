package com.arobs.internship.library.business;

import com.arobs.internship.library.dtos.BookDTO;
import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.entities.book.Book;

import java.util.List;
import java.util.Set;

public interface BookService {
    void insertBook(Book book);

    List<BookDTO> findBooks();

    BookDTO findBookById(int id);

    void updateBook(String description, Set<TagDTO> tagDTOSet, int id);

    void deleteBook(String title, String author);

    Book dtoToBook(BookDTO bookDTO);

    BookDTO bookToDto(Book book);

}
