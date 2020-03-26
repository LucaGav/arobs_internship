package com.arobs.internship.library.service;

import com.arobs.internship.library.business.impl.book.BookServiceImpl;
import com.arobs.internship.library.business.impl.book.CopyServiceImpl;
import com.arobs.internship.library.dao.BookDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dao.factory.hibernate.HibernateDaoFactory;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.util.handler.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private DaoFactory daoFactory;

    @Mock
    private BookDao bookDao;

    @Mock
    private HibernateDaoFactory hibernateDaoFactory;

    @Mock
    private CopyServiceImpl copyService;

    private Copy copy;

    private Book bookMock;

    @BeforeEach
    void setUp() {

        bookMock = new Book(800, "Asculta", "Corona", "Pswo", new Date());
        copy = new Copy(true, "AVAILABLE", bookMock);
        when(daoFactory.getInstance()).thenReturn(hibernateDaoFactory);
        when(daoFactory.getInstance().getBookDao()).thenReturn(bookDao);
        this.bookService.init();
        this.copyService.init();
    }

    @Test
    void whenInsertBook_givenBook_returnBook() throws ValidationException {

        when(bookDao.insert(any(Book.class))).thenReturn(bookMock);
        Book book = bookService.insertBook(new Book(800, "Asculta", "Corona", "Pswo", new Date()));
        assertEquals(book, bookMock);
    }

    @Test
    void whenInsertCopy_givenBook_returnCopy() throws ValidationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        when(copyService.insertCopy(any(Copy.class))).thenReturn(copy);
        final Method privateMethod = BookServiceImpl.class.getDeclaredMethod("createBookCopy", Book.class);
        privateMethod.setAccessible(true);
        Copy c = (Copy) privateMethod.invoke(bookService, bookMock);
        assertEquals(c, copy);
    }
}
