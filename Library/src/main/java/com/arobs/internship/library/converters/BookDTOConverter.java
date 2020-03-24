package com.arobs.internship.library.converters;

import com.arobs.internship.library.dtos.book.BookDTO;
import com.arobs.internship.library.entities.book.Book;

import java.util.List;

public interface BookDTOConverter {

    List<BookDTO> listBookToDTO(List<Book> books);

    Book dtoToBook(BookDTO bookDTO);

    BookDTO bookToDTO(Book book);

}
