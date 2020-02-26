package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.book.Tag;

import java.util.List;

public interface TagDao {
    int save(Tag tag);

    List<Tag> findTags();

    Tag findByDescription(String description);

    int delete(String description);

    int update(Tag tag);
}
