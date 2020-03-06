package com.arobs.internship.library.converters.impl;

import com.arobs.internship.library.converters.TagDTOConverter;
import com.arobs.internship.library.dtos.book.TagDTO;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagDTOConverterImpl implements TagDTOConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Tag dtoToTag(TagDTO tagDTO) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(tagDTO, Tag.class);
    }

    @Override
    public TagDTO tagToDTO(Tag tag) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(tag, TagDTO.class);
    }

    @Override
    public List<TagDTO> listTagToDTO(List<Tag> tags) {
        ModelMapper modelMapper = objectMapper.getMapper();
        TagDTO tagDTO;
        List<TagDTO> tagDTOS = new ArrayList<>();
        for (Tag tag : tags) {
            tagDTO = modelMapper.map(tag, TagDTO.class);
            tagDTOS.add(tagDTO);
        }
        return tagDTOS;
    }

    @Override
    public List<Tag> listDTOToTag(List<TagDTO> tagDTOS) {
        ModelMapper modelMapper = objectMapper.getMapper();
        Tag tag;
        List<Tag> tags = new ArrayList<>();
        for (TagDTO tagDTO : tagDTOS) {
            tag = modelMapper.map(tagDTO, Tag.class);
            tags.add(tag);
        }
        return tags;
    }
}
