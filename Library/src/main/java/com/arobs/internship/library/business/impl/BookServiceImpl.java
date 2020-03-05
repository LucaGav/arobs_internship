package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.converters.TagDTOConverter;
import com.arobs.internship.library.dao.BookDao;
import com.arobs.internship.library.dao.CopyDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.util.status.CopyStatus;
import com.arobs.internship.library.util.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private TagDTOConverter tagDTOConverter;

    @Autowired
    private DaoFactory daoFactory;

    @Autowired
    private TagServiceImpl tagService;

    private BookDao bookDao;

    private CopyDao copyDao;

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        bookDao = factory.getBookDao();
        copyDao = factory.getCopyDao();
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
        book.setAddedDate(new Date());
        bookDao.save(book);
        Copy copy = new Copy(true, CopyStatus.AVAILABLE.name(), book);//add a copy when inserting a book
        copyDao.save(copy);
    }

    @Override
    @Transactional
    public List<Book> findBooks() {
        return bookDao.findBooks();
    }

    @Override
    @Transactional
    public Book findBookById(int id) {
        return bookDao.findById(id);
    }

    @Override
    @Transactional
    public void updateBook(String description, Set<TagDTO> tagDTOSet, int id) throws ValidationException {
        Book book = this.findBookById(id);
        if (book == null) {
            throw new ValidationException("No book with this id found");
        }
        if (!description.equals(book.getDescription()) || !book.getTags().equals(tagDTOSet)) {
            List<TagDTO> dtoList = new ArrayList<>(tagDTOSet);
            List<Tag> newTags = tagDTOConverter.listDTOToTag(dtoList);
            book.setTags(this.updateTags(new HashSet<>(newTags)));
            book.setDescription(description);
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


    public Set<Tag> updateTags(Set<Tag> bookTags) {
        Set<Tag> newTags = new HashSet<>();
        Tag newTag;
        for (Tag tag : bookTags) {
            newTag = tagService.findTagByName(tag.getTagName());
            if (newTag == null) {
                newTags.add(tag);
            } else {
                newTags.add(newTag);
            }
        }
        return newTags;
    }
}
