package com.arobs.internship.library.business;

import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.entities.book.Tag;

import java.util.List;

public interface TagService {
    void insertTag(Tag tag);

    List<TagDTO> findTags();

    Tag findTagByDescription(String description);

    //void updateTag(String description, int id);

    void deleteTag(String description);

    Tag dtoToTag(TagDTO tagDTO);

    TagDTO tagToDto(Tag tag);

}
