package com.arobs.internship.library.business;

import com.arobs.internship.library.entities.book.Copy;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;

public interface CopyService {

    Copy insertCopy(Copy copy) throws ValidationException;

    List<Copy> findCopies();

    List<Copy> findCopiesOfBook(int id);

    Copy findAvailableCopyOfBook(int id);

    Copy findCopyById(int id);

    Copy updateCopy(String string, Boolean available, int id) throws ValidationException;

    int deleteCopy(int id);
}
