package com.arobs.internship.library.converters;

import com.arobs.internship.library.dtos.book.TagDTO;
import com.arobs.internship.library.entities.book.Tag;

import java.util.List;
import java.util.Set;

public interface TagDTOConverter {

    Tag dtoToTag(TagDTO tagDTO);

    TagDTO tagToDTO(Tag tag);

    List<TagDTO> listTagToDTO(List<Tag> tags);

    Set<Tag> setTagToDTO(Set<TagDTO> tagDTOSet);

    List<Tag> listDTOToTag(List<TagDTO> tagDTOS);

}
