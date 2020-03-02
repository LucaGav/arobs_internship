package com.arobs.internship.library.business;

import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.handler.ValidationException;

import java.util.List;

public interface TagService {
    void insertTag(Tag tag) throws ValidationException;

    List<TagDTO> findTags();

    Tag findTagByDescription(String description) throws ValidationException;

    //void updateTag(String description, int id);

    void deleteTag(String description) throws ValidationException;

    Tag dtoToTag(TagDTO tagDTO);

    TagDTO tagToDto(Tag tag);

}
