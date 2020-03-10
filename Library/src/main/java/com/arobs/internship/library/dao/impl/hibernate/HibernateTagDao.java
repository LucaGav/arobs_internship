package com.arobs.internship.library.dao.impl.hibernate;

import com.arobs.internship.library.business.impl.BookServiceImpl;
import com.arobs.internship.library.dao.TagDao;
import com.arobs.internship.library.entities.book.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class HibernateTagDao implements TagDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public void insert(Tag tag) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(tag);
    }

    @Override
    public List<Tag> findTags() {
        List<Tag> tags;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Tag");
        tags = query.getResultList();
        return tags;
    }

    @Override
    public Tag findByName(String tagName) {
        Tag tag = null;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Tag WHERE tagName =: tagName");
        query.setParameter("tagName", tagName);
        List<?> results = query.getResultList();
        if (!results.isEmpty()) {
            tag = (Tag) results.get(0);
        }
        return tag;
    }

    @Override
    public void delete(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Tag where tagName =: name");
        query.setParameter("name", name);
        query.executeUpdate();
    }
}
