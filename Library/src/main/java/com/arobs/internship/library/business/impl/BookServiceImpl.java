package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.dao.BookDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.BookDTO;
import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.handler.MyCustomException;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DaoFactory daoFactory;

    @Autowired
    private TagServiceImpl tagService;

    private BookDao bookDao;

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        bookDao = factory.getBookDao();
    }

    @Override
    public void insertBook(Book book) {
        List<BookDTO> books = this.findBooks();
        for(BookDTO b: books){
            if(b.getAuthor().equals(book.getAuthor()) && b.getTitle().equals(book.getTitle())){
                throw new MyCustomException("Book having the same title and author already exists");
            }
        }
        bookDao.save(book);
    }

    @Override
    public List<BookDTO> findBooks() {
        List<BookDTO> bookDTOS = new ArrayList<>();
        List<Book> books = bookDao.findBooks();
        for(Book b: books){
            BookDTO dto = bookToDto(b);
            bookDTOS.add(dto);
        }
        return bookDTOS;
    }

    @Override
    public BookDTO findBookById(int id) {
        Book book = bookDao.findById(id);
        if(book == null){
            throw new MyCustomException("No book with id: " + id + "found in the database");
        }
        return this.bookToDto(book);
    }

    @Override
    public void updateBook(String description, int id) {

    }

    @Override
    public void deleteBook(String title, String author) {
        boolean foundBook = false;
        List<BookDTO> books = this.findBooks();
        for(BookDTO bookDTO: books){
            if(bookDTO.getTitle().equals(title) && bookDTO.getAuthor().equals(author)){
                foundBook = true;
                break;
            }
        }
        if(!foundBook){
            throw new MyCustomException("No book with the title: " + title + " of the author: " + author + " was found");
        }
        bookDao.delete(title,author);
    }

    @Override
    public Book dtoToBook(BookDTO bookDTO) {
        ModelMapper modelMapper = objectMapper.getMapper();
        Book book = modelMapper.map(bookDTO, Book.class);
        Set<Tag> tags = new HashSet<>();
        Tag tag;
        Set<TagDTO> tagDTOs = bookDTO.getTags();
        for (TagDTO tagDTO : tagDTOs) {
            try {
                tag = tagService.findTagByDescription(tagDTO.getTagDescription());
                tags.add(tag);//find in db? then add it to the et
            } catch (MyCustomException ex) {
                tag = tagService.dtoToTag(tagDTO);//didn't find in db? add to set as new tag
                tags.add(tag);
            }
        }
        book.setTags(tags);
        return book;
    }

    @Override
    public BookDTO bookToDto(Book book) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(book, BookDTO.class);
    }
}
