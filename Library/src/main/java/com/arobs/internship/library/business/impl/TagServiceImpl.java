package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.TagService;
import com.arobs.internship.library.dao.TagDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.handler.CustomException;
import com.arobs.internship.library.util.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
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
    @Transactional
    public void insertTag(Tag tag) throws CustomException {
        List<TagDTO> tagDTOS = this.findTags();
        for(TagDTO t: tagDTOS){
            if(t.getTagDescription().equals(tag.getTagDescription())){
                throw new CustomException("Tag with description " + t.getTagDescription() + " already exists.");
            }
        }
        tagDao.save(tag);
    }

    @Override
    @Transactional
    public List<TagDTO> findTags() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        List<Tag> tags = tagDao.findTags();
        for(Tag t : tags){
            TagDTO tagDTO = tagToDto(t);
            tagDTOS.add(tagDTO);
        }
        return tagDTOS;
    }

    @Override
    @Transactional
    public Tag findTagByDescription(String description) throws CustomException {
        Tag tag = tagDao.findByDescription(description);
        if (tag == null) {
            throw new CustomException("No tag with this description found");
        }
        return tag;
    }

   /* @Override
    public void updateTag(String description, int id) {
        TagDTO tagDTO = this.findTagByDescription()
    }*/

    @Override
    @Transactional
    public void deleteTag(String description) throws CustomException {
        boolean foundDescription = false;
        List<TagDTO> tags = this.findTags();
        for(TagDTO tagDTO: tags){
            if(tagDTO.getTagDescription().equals(description)){
                foundDescription = true;
                break;
            }
        }
        if(!foundDescription){
            throw new CustomException("Description is invalid");
        }
        tagDao.delete(description);
    }

    @Override
    @Transactional
    public Tag dtoToTag(TagDTO tagDTO) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(tagDTO, Tag.class);
    }

    @Override
    @Transactional
    public TagDTO tagToDto(Tag tag) {
        ModelMapper modelMapper = objectMapper.getMapper();
        return modelMapper.map(tag, TagDTO.class);
    }
}
