package com.arobs.internship.library.business;

import com.arobs.internship.library.dtos.book.TagDTO;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;
import java.util.Set;

public interface BookService {

    Book insertBook(Book book) throws ValidationException;

    List<Book> findBooks();

    Book findBookById(int id);

    Book updateBook(String description, Set<TagDTO> tagDTOSet, int id) throws ValidationException;

    Book deleteBook(int id) throws ValidationException;

}
