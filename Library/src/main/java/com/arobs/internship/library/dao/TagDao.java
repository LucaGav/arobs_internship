package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.book.Tag;

import java.util.List;

public interface TagDao {

    void save(Tag tag);

    List<Tag> findTags();

    Tag findByDescription(String description);

    void delete(String description);

}
