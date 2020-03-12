package com.arobs.internship.library.service;

import com.arobs.internship.library.business.impl.BookServiceImpl;
import com.arobs.internship.library.business.impl.CopyServiceImpl;
import com.arobs.internship.library.dao.BookDao;
import com.arobs.internship.library.dao.CopyDao;
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
import java.net.DatagramPacket;
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

    @BeforeEach
    void setUp(){
        when(daoFactory.getInstance()).thenReturn(hibernateDaoFactory);
        when(daoFactory.getInstance().getBookDao()).thenReturn(bookDao);
        this.bookService.init();
        this.copyService.init();
    }

    @Test
    void whenInsertBook_givenBook_returnBook() throws ValidationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Book bookMock = new Book(800,"Asculta","Corona","Pswo",new Date());
        when(bookDao.insert(any(Book.class))).thenReturn(bookMock);
        Book book = bookService.insertBook(new Book(800,"Asculta","Corona","Pswo", new Date()));

       /* Copy copy = new Copy(true,"AVAILABLE",book);
        when(copyDao.insert(any(Copy.class))).thenReturn(copy);

        final Method privateMethod = BookServiceImpl.class.getDeclaredMethod("createBookCopy", Book.class);
        privateMethod.setAccessible(true);
        final Copy c = (Copy) privateMethod.invoke(bookService,book);

        assertEquals(c, copy);
        */
        assertEquals(book,bookMock);
    }
}
