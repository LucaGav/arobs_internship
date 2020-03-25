package com.arobs.internship.library.dao.impl.hibernate.auxiliary;

import com.arobs.internship.library.dao.PendingRequestDao;
import com.arobs.internship.library.dao.impl.hibernate.util.QueryUtil;
import com.arobs.internship.library.entities.auxiliary.PendingRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class HibernatePendingRequestDao implements PendingRequestDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public PendingRequest insert(PendingRequest pendingRequest) {
        Session session = this.sessionFactory.getCurrentSession();
        pendingRequest.setPendingDate(new Date());
        session.persist(pendingRequest);
        return pendingRequest;
    }

    @Override
    public List<PendingRequest> findPendingRequests() {
        List<PendingRequest> pendingRequests;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM PendingRequest");
        pendingRequests = query.getResultList();
        return pendingRequests;
    }

    @Override
    public PendingRequest findById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from PendingRequest WHERE pendingreqID =: id").setMaxResults(1);
        query.setParameter("id", id);
        return QueryUtil.safeGetUniqueResult(query.getResultList());
    }

    @Override
    public int delete(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM PendingRequest WHERE pendingreqID =: id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
