package com.arobs.internship.library.dao.impl.hibernate;

import com.arobs.internship.library.dao.TagDao;
import com.arobs.internship.library.entities.book.Tag;

import java.util.List;

public class HibernateTagDao implements TagDao {
    @Override
    public int save(Tag tag) {
        return 0;
    }

    @Override
    public List<Tag> findTags() {
        return null;
    }

    @Override
    public Tag findByDescription(String description) {
        return null;
    }

    @Override
    public int delete(String description) {
        return 0;
    }

    @Override
    public int update(Tag tag) {
        return 0;
    }
}
