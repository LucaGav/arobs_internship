package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.BookService;
import com.arobs.internship.library.business.CopyService;
import com.arobs.internship.library.dao.CopyDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.CopyDTO;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.entities.util.CopyStatus;
import com.arobs.internship.library.handler.ValidationException;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CopyServiceImpl implements CopyService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private DaoFactory daoFactory;

    private CopyDao copyDao;

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        copyDao = factory.getCopyDao();
    }

    @Override
    @Transactional
    public void insertCopy(Copy copy) throws ValidationException {
        if (!CopyStatus.contains(copy.getStatus().toUpperCase())) {
            throw new ValidationException("No valid status for copy");
        }
        copyDao.save(copy);
    }

    @Override
    @Transactional
    public List<Copy> findCopies() {
        return copyDao.findCopies();
    }

    @Override
    @Transactional
    public List<Copy> findCopiesOfBook(int id) {
        return copyDao.findByBookID(id);
    }

    @Override
    @Transactional
    public Copy findCopyById(int id) throws ValidationException {
        Copy copy = copyDao.findById(id);
        if (copy == null) {
            throw new ValidationException("No copy with id: " + id + "found in the database");
        }
        return copy;
    }

    @Override
    @Transactional
    public void updateCopy(String status, Boolean available, int id) throws ValidationException {
        Copy copy = this.findCopyById(id);
        if (copy == null) {
            throw new ValidationException("No copy with this id found.");
        }
        if (!CopyStatus.contains(status.toUpperCase())) {
            throw new ValidationException("No valid status for copy");
        }
        if (!status.equals(copy.getStatus()) || copy.isAvailable() != available) {
            copy.setAvailable(available);
            copy.setStatus(status);
        } else {
            throw new ValidationException("No updated fields");
        }
    }

    @Override
    @Transactional
    public void deleteCopy(int id) throws ValidationException {
        boolean foundCopy = false;
        List<Copy> copies = this.findCopies();
        for (Copy copy : copies) {
            if (copy.getCopyID() == id) {
                foundCopy = true;
                break;
            }
        }
        if (!foundCopy) {
            throw new ValidationException("No copy with this id found");
        }
        copyDao.delete(id);
    }

    @Override
    public List<CopyDTO> listCopyToDto(List<Copy> copies) {
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
        copy.setBook(bookService.findBookById(copyDTO.getBookID()));
        return copy;
    }

    @Override
    public CopyDTO copyToDto(Copy copy) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(copy, CopyDTO.class);
    }
}
