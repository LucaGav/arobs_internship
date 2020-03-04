package com.arobs.internship.library.business;

import com.arobs.internship.library.dtos.CopyDTO;
import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.handler.ValidationException;

import java.util.List;

public interface CopyService {

    void insertCopy(Copy copy) throws ValidationException;

    List<Copy> findCopies();

    List<Copy> findCopiesOfBook(int id);

    Copy findCopyById(int id) throws ValidationException;

    void updateCopy(String string, Boolean available, int id) throws ValidationException;

    void deleteCopy(int id) throws ValidationException;

    List<CopyDTO> listCopyToDto(List<Copy> copies);

    Copy dtoToCopy(CopyDTO copyDTO) throws ValidationException;

    CopyDTO copyToDto(Copy copy);
}
