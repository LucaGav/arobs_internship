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
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class HibernateTagDao implements TagDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public int save(Tag tag) {
        Session session = this.sessionFactory.getCurrentSession();
        return (Integer) session.save(tag);
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
    public Tag findByDescription(String description) {
        Tag tag = null;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Tag WHERE tagDescription =: tagDescription");
        query.setParameter("tagDescription", description);
        //tag = (Tag) query.getSingleResult();
        List<?> results = query.getResultList();
        if (!results.isEmpty()) {
            tag = (Tag) results.get(0);
        }
        return tag;
    }

    @Override
    public int delete(String description) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Tag where tagDescription =: description");
        query.setParameter("description", description);
        query.executeUpdate();
        return 1;
    }

   /* @Override
    public int update(Tag tag) {
        return 0;
    }*/
}
