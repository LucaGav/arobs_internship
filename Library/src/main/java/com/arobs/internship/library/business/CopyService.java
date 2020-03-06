package com.arobs.internship.library.business;

import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;

public interface CopyService {

    void insertCopy(Copy copy) throws ValidationException;

    List<Copy> findCopies();

    List<Copy> findCopiesOfBook(int id);

    Copy findCopyById(int id);

    void updateCopy(String string, Boolean available, int id) throws ValidationException;

    void deleteCopy(int id) throws ValidationException;
}
