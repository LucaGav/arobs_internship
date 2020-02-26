package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.TagService;
import com.arobs.internship.library.dao.TagDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public class TagServiceImpl implements TagService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DaoFactory daoFactory;

    private TagDao tagDao;

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        tagDao = factory.getTagDao();
    }

    @Override
    public void insertTag(Tag tag) {

    }

    @Override
    public List<TagDTO> findTags() {
        return null;
    }

    @Override
    public TagDTO findTagByDescription(String description) {
        return null;
    }

    @Override
    public void updateTag(String description, int id) {

    }

    @Override
    public void deleteTag(String title) {

    }

    @Override
    public Tag dtoToTag(TagDTO tagDTO) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(tagDTO, Tag.class);
    }

    @Override
    public TagDTO tagToDto(Tag tag) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(tag, TagDTO.class);
    }
}
