package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.dao.BookDao;
import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.BookDTO;
import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

public class BookServiceImpl implements BookService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DaoFactory daoFactory;

    private BookDao bookDao;

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        bookDao = factory.getBookDao();
    }

    @Override
    public void insertBook(Book book) {

    }

    @Override
    public List<BookDTO> findBooks() {
        return null;
    }

    @Override
    public BookDTO findBookById(int id) {
        return null;
    }

    @Override
    public void updateBook(String description, int id) {

    }

    @Override
    public void deleteBook(String title) {

    }

    @Override
    public Book dtoToBook(BookDTO bookDTO) {
        ModelMapper modelMapper = objectMapper.getMapper();
        Book book = modelMapper.map(bookDTO, Book.class);
        Set<TagDTO> tagDTOs = bookDTO.getTags();
        for(TagDTO tagDTO: tagDTOs){
            /*if(find tag in db by description)
             Set Tag = Set Tag + new Tag
             */
            book.addNewTag(tagDTO.getTagDescription());
        }
        //book.setTags(Set Tag)
        return book;
    }

    @Override
    public BookDTO bookToDto(Book book) {
        return null;
    }
}
