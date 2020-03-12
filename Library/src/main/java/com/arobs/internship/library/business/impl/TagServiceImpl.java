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
    public Tag insertTag(Tag tag) throws ValidationException {
        List<Tag> tags = this.findTags();
        for (Tag t : tags) {
            if (t.equals(tag.getTagName())) {
                throw new ValidationException("Tag with description " + t.getTagName() + " already exists.");
            }
        }
       return tagDao.insert(tag);
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
    public int deleteTag(String name){
        return tagDao.delete(name);
    }
}
