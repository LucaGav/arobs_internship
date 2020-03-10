package com.arobs.internship.library.business.impl;

import com.arobs.internship.library.business.TagService;
import com.arobs.internship.library.dao.TagDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.util.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            if (t.equals(tag.getTagName())) {
                throw new ValidationException("Tag with description " + t.getTagName() + " already exists.");
            }
        }
        tagDao.insert(tag);
    }

    @Override
    @Transactional
    public List<Tag> findTags() {
        return tagDao.findTags();
    }

    @Override
    @Transactional
    public Tag findTagByName(String name) {
        return tagDao.findByName(name);
    }

    @Override
    @Transactional
    public void deleteTag(String name) throws ValidationException {
        boolean foundName = false;
        List<Tag> tags = this.findTags();
        for (Tag tag : tags) {
            if (tag.getTagName().equals(name)) {
                foundName = true;
                break;
            }
        }
        if (!foundName) {
            throw new ValidationException("Tag name is invalid");
        }
        tagDao.delete(name);
    }

    public Set<Tag> handleBookTags(Set<Tag> bookTags) {
        Set<Tag> newTags = new HashSet<>();
        Tag newTag;
        for (Tag tag : bookTags) {
            newTag = this.findTagByName(tag.getTagName());
            if (newTag == null) {
                newTags.add(tag);
            } else {
                newTags.add(newTag);
            }
        }
        return newTags;
    }
}
