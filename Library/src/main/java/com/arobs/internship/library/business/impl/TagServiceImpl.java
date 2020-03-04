package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.TagService;
import com.arobs.internship.library.dao.TagDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.handler.ValidationException;
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
    private DaoFactory daoFactory;

    private TagDao tagDao;

    @PostConstruct
    public void init() {
        DaoFactory factory = daoFactory.getInstance();
        tagDao = factory.getTagDao();
    }

    @Override
    @Transactional
    public void insertTag(Tag tag) throws ValidationException {
        List<Tag> tags = this.findTags();
        for (Tag t : tags) {
            if (t.getTagDescription().equals(tag.getTagDescription())) {
                throw new ValidationException("Tag with description " + t.getTagDescription() + " already exists.");
            }
        }
        tagDao.save(tag);
    }

    @Override
    @Transactional
    public List<Tag> findTags() {
        return tagDao.findTags();
    }

    @Override
    @Transactional
    public Tag findTagByDescription(String description) throws ValidationException {
        Tag tag = tagDao.findByDescription(description);
        if (tag == null) {
            throw new ValidationException("No tag with this description found");
        }
        return tag;
    }

    @Override
    @Transactional
    public void deleteTag(String description) throws ValidationException {
        boolean foundDescription = false;
        List<Tag> tags = this.findTags();
        for (Tag tag : tags) {
            if (tag.getTagDescription().equals(description)) {
                foundDescription = true;
                break;
            }
        }
        if (!foundDescription) {
            throw new ValidationException("Description is invalid");
        }
        tagDao.delete(description);
    }
}
