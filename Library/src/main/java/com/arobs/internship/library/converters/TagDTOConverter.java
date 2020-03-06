package com.arobs.internship.library.converters;

import com.arobs.internship.library.dtos.book.TagDTO;
import com.arobs.internship.library.entities.book.Tag;

import java.util.List;

public interface TagDTOConverter {

    Tag dtoToTag(TagDTO tagDTO);

    TagDTO tagToDTO(Tag tag);

    List<TagDTO> listTagToDTO(List<Tag> tags);

    List<Tag> listDTOToTag(List<TagDTO> tagDTOS);

}
