package com.arobs.internship.library.dao;

import com.arobs.internship.library.entities.book.Tag;

import java.util.List;

public interface TagDao {

    Tag insert(Tag tag);

    List<Tag> findTags();

    Tag findByName(String tagName);

    int delete(String name);

}
