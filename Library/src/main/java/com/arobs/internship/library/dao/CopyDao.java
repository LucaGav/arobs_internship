package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.book.Copy;

import java.util.List;

public interface CopyDao {

    Copy insert(Copy copy);

    List<Copy> findCopies();

    List<Copy> findByBookID(int bookId);

    Copy findAvailableByBookID(int bookId);

    Copy findById(int id);

}
