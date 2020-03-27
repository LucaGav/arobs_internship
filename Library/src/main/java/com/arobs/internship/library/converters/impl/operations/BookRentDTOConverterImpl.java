package com.arobs.internship.library.converters.impl.operations;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.business.EmployeeService;
import com.arobs.internship.library.converters.BookRentDTOConverter;
import com.arobs.internship.library.dtos.operations.BookRentDTO;
import com.arobs.internship.library.entities.book.Book;
import com.arobs.internship.library.entities.employee.Employee;
import com.arobs.internship.library.entities.operations.BookRent;
import com.arobs.internship.library.util.ObjectMapper;
import com.arobs.internship.library.util.handler.ValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookRentDTOConverterImpl implements BookRentDTOConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BookService bookService;

    @Override
    public BookRent dtoToBookRent(BookRentDTO bookRentDTO) throws ValidationException {
        BookRent bookRent = new BookRent();
        bookRent.setEmployee(employeeService.findEmployeeById(bookRentDTO.getEmployeeID()));
        bookRent.setBook(bookService.findBookById(bookRentDTO.getBookID()));
        return bookRent;
    }

    @Override
    public BookRentDTO bookRentToDTO(BookRent bookRent) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(bookRent, BookRentDTO.class);
    }

    @Override
    public List<BookRentDTO> listBookRentToDTO(List<BookRent> bookRents) {
        ModelMapper modelMapper = objectMapper.getMapper();
        BookRentDTO bookRentDTO;
        List<BookRentDTO> bookRentDTOS = new ArrayList<>();
        for (BookRent bookRent : bookRents) {
            bookRentDTO = modelMapper.map(bookRent, BookRentDTO.class);
            bookRentDTOS.add(bookRentDTO);
        }
        return bookRentDTOS;
    }
}
