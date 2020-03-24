package com.arobs.internship.library.business;

import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.util.handler.ValidationException;

import java.util.List;

public interface TagService {

    Tag insertTag(Tag tag) throws ValidationException;

    List<Tag> findTags();

    Tag findTagByName(String name);

    int deleteTag(String name);

}
