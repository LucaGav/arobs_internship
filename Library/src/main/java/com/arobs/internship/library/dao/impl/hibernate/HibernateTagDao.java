package com.arobs.internship.library.dao.impl.hibernate;

import com.arobs.internship.library.business.impl.BookServiceImpl;
import com.arobs.internship.library.dao.TagDao;
import com.arobs.internship.library.entities.book.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public int save(Tag tag) {
        Transaction transaction = null;
        try {
            Session session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Integer res = (Integer) session.save(tag);
            transaction.commit();
            return res;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.warn("Rollback");
            }
        }
        return 0;
    }

    @Override
    public List<Tag> findTags() {
        Transaction transaction = null;
        List<Tag> tags;
        try {
            Session session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Tag");
            tags = query.getResultList();
            transaction.commit();
            return tags;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.warn("Rollback");
            }
        }
        return null;
    }

    @Override
    public Tag findByDescription(String description) {
        Transaction transaction = null;
        Tag tag = null;
        try {
            Session session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Tag WHERE tagDescription =: tagDescription");
            query.setParameter("tagDescription", description);
            //tag = (Tag) query.getSingleResult();
            List<?> results = query.getResultList();
            if (!results.isEmpty()) {
                tag = (Tag) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.warn("Rollback");
            }
        }
        return tag;
    }

    @Override
    public int delete(String description) {
        Transaction transaction = null;
        try {
            Session session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Tag where tagDescription =: description");
            query.setParameter("description", description);
            query.executeUpdate();
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.warn("Rollback");
            }
        }
        return 0;
    }

   /* @Override
    public int update(Tag tag) {
        return 0;
    }*/
}
