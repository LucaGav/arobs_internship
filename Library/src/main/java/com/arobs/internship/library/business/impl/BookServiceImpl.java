package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.dao.BookDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.BookDTO;
import com.arobs.internship.library.dtos.EmployeeDTO;
import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.handler.ValidationException;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
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
    @Transactional
    public void insertBook(Book book) throws ValidationException {
        List<Book> books = this.findBooks();
        for (Book b : books) {
            if (b.getAuthor().equals(book.getAuthor()) && b.getTitle().equals(book.getTitle())) {
                throw new ValidationException("Book having the same title and author already exists");
            }
        }
        book.setTags(this.updateTags(book.getTags()));
        bookDao.save(book);
    }

    @Override
    @Transactional
    public List<Book> findBooks() {
        List<Book> books = bookDao.findBooks();
        return books;
    }

    @Override
    @Transactional
    public Book findBookById(int id) throws ValidationException {
        Book book = bookDao.findById(id);
        if (book == null) {
            throw new ValidationException("No book with id: " + id + "found in the database");
        }
        return book;
    }

    @Override
    @Transactional
    public void updateBook(String description, Set<TagDTO> tagDTOSet, int id) throws ValidationException {
        Book book = this.findBookById(id);
        if (book == null) {
            throw new ValidationException("No book with this id found");
        }
        if (!description.equals(book.getDescription()) || !book.getTags().equals(tagDTOSet)) {
            Set<Tag> newTags = new HashSet<>();
            Tag tag;
            for(TagDTO tagDTO: tagDTOSet){
                tag = tagService.dtoToTag(tagDTO);
                newTags.add(tag);
            }
            book.setTags(this.updateTags(newTags));
            book.setDescription(description);
           // bookDao.update(book);
        } else {
            throw new ValidationException("No updated fields");
        }
    }

    @Override
    @Transactional
    public void deleteBook(String title, String author) throws ValidationException {
        boolean foundBook = false;
        List<Book> books = this.findBooks();
        for (Book book : books) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                foundBook = true;
                break;
            }
        }
        if (!foundBook) {
            throw new ValidationException("No book with the title: " + title + " of the author: " + author + " was found");
        }
        bookDao.delete(title, author);
    }

    @Override
    public List<BookDTO> listBookToDto(List<Book> books) {
        ModelMapper modelMapper = objectMapper.getMapper();
        BookDTO bookDTO;
        List<BookDTO> bookDTOS = new ArrayList<>();
        for(Book book: books){
            bookDTO = modelMapper.map(book, BookDTO.class);
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }

    @Override
    public Book dtoToBook(BookDTO bookDTO) {
        ModelMapper modelMapper = objectMapper.getMapper();
        Book book = modelMapper.map(bookDTO, Book.class);
       // book.setTags(this.updateTags(bookDTO.getTags()));
        return book;
    }

    @Override
    public BookDTO bookToDto(Book book) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(book, BookDTO.class);
    }

    public Set<Tag> updateTags(Set<Tag> bookTags){
        Set<Tag> newTags = new HashSet<>();
        Tag newTag;
        for (Tag tag : bookTags) {
            try {
                newTag = tagService.findTagByDescription(tag.getTagDescription());
                newTags.add(newTag);//find in db? then add it to the et
            } catch (ValidationException ex) {
                //newTag = tagService.dtoToTag(tag);//didn't find in db? add to set as new tag
                newTags.add(tag);
            }
        }
        return newTags;
    }
}
