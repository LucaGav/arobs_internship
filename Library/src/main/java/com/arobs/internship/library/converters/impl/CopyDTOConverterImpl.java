package com.arobs.internship.library.converters.impl;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.converters.CopyDTOConverter;
import com.arobs.internship.library.dtos.CopyDTO;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.util.status.CopyStatus;
import com.arobs.internship.library.util.handler.ValidationException;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CopyDTOConverterImpl implements CopyDTOConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookService bookService;

    @Override
    public List<CopyDTO> listCopyToDTO(List<Copy> copies) {
        ModelMapper modelMapper = objectMapper.getMapper();
        CopyDTO copyDTO;
        List<CopyDTO> copyDTOS = new ArrayList<>();
        for (Copy copy : copies) {
            copyDTO = modelMapper.map(copy, CopyDTO.class);
            copyDTOS.add(copyDTO);
        }
        return copyDTOS;
    }

    @Override
    public Copy dtoToCopy(CopyDTO copyDTO) throws ValidationException {
        ModelMapper modelMapper = objectMapper.getMapper();
        Copy copy = modelMapper.map(copyDTO, Copy.class);
        copy.setStatus(CopyStatus.AVAILABLE.name());
        copy.setBook(bookService.findBookById(copyDTO.getBookID()));
        return copy;
    }

    @Override
    public CopyDTO copyToDTO(Copy copy) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(copy, CopyDTO.class);
    }
}
