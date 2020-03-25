package com.arobs.internship.library.business.impl.book;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.business.RentRequestService;
import com.arobs.internship.library.business.impl.operations.BookRentServiceImpl;
import com.arobs.internship.library.converters.TagDTOConverter;
import com.arobs.internship.library.dao.BookDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.book.TagDTO;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.entities.operations.BookRent;
import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.status.ActiveStatus;
import com.arobs.internship.library.util.status.BookRentStatus;
import com.arobs.internship.library.util.status.CopyStatus;
import com.arobs.internship.library.util.handler.ValidationException;
import com.arobs.internship.library.util.status.RentRequestStatus;
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

    @Autowired
    private CopyServiceImpl copyService;

    @Autowired
    private BookRentServiceImpl bookRentService;

    @Autowired
    private RentRequestService rentRequestService;

    private BookDao bookDao;

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        bookDao = factory.getBookDao();
    }

    @Override
    @Transactional
    public Book insertBook(Book book) throws ValidationException {
        List<Book> books = this.findBooks();
        for (Book b : books) {
            if (b.getAuthor().equals(book.getAuthor()) && b.getTitle().equals(book.getTitle())) {
                throw new ValidationException("Book having the same title and author already exists");
            }
        }
        book.setTags(this.handleBookTags(book.getTags()));
        Book insertedBook = bookDao.insert(book);
        this.createBookCopy(insertedBook);
        return insertedBook;
    }

    private Copy createBookCopy(Book book) throws ValidationException {
        Copy copy = new Copy(true, CopyStatus.AVAILABLE.name(), book);//add a copy when inserting a book
        return copyService.insertCopy(copy);
    }

    @Override
    @Transactional
    public Book updateBook(String description, Set<TagDTO> tagDTOSet, int id) throws ValidationException {
        Book book = this.findBookById(id);
        if (book == null) {
            throw new ValidationException("No book with this id found");
        }
        if (!description.equals(book.getDescription()) || !book.getTags().equals(tagDTOSet)) {
            book.setTags(this.handleBookTags(tagDTOConverter.setTagToDTO(tagDTOSet)));
            book.setDescription(description);
        } else {
            throw new ValidationException("No updated fields");
        }
        return book;
    }

    private Set<Tag> handleBookTags(Set<Tag> bookTags) {
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
    public Book deleteBook(int id) throws ValidationException {
        Book book = this.findBookById(id);
        if (book == null) {
            throw new ValidationException("No book with this id found");
        }
        validateBookRemoval(id);
        book.setStatus(ActiveStatus.INACTIVE.name());
        return book;
    }

    private void validateBookRemoval(int bookID) throws ValidationException {
        boolean brFlag = true;
        boolean rrFlag = true;
        List<BookRent> rentsOfBook = bookRentService.findBookRentsByBookID(bookID);
        for (BookRent bookRent : rentsOfBook) {
            if (!bookRent.getStatus().equals(BookRentStatus.RETURNED.name())) {
                brFlag = false;
            }
        }
        List<RentRequest> rentRequestsOfBook = rentRequestService.findRentRequestsByBookID(bookID);
        for (RentRequest rentRequest : rentRequestsOfBook) {
            if (!(rentRequest.getStatus().equals(RentRequestStatus.DECLINED.name())
                    || rentRequest.getStatus().equals(RentRequestStatus.GRANTED.name()))) {
                rrFlag = false;
            }
        }
        if (!brFlag) {
            throw new ValidationException("Cannot delete book, as it has unfinished book rents");
        }
        if (!rrFlag) {
            throw new ValidationException("Cannot delete book, as it has unfinished rent requests");
        }
    }
}
