package com.arobs.internship.library.dao.impl.hibernate.operations;

import com.arobs.internship.library.dao.RentRequestDao;
import com.arobs.internship.library.dao.impl.hibernate.util.QueryUtil;
import com.arobs.internship.library.entities.operations.RentRequest;
import com.arobs.internship.library.util.status.RentRequestStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class HibernateRentRequestDao implements RentRequestDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public RentRequest insert(RentRequest rentRequest) {
        Session session = this.sessionFactory.getCurrentSession();
        rentRequest.setStatus(RentRequestStatus.WAITINGAVAILABLECOPY.name());
        rentRequest.setRequestDate(new Date());
        session.save(rentRequest);
        return rentRequest;
    }

    @Override
    public List<RentRequest> findRentRequests() {
        List<RentRequest> rentRequests;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM RentRequest");
        rentRequests = query.getResultList();
        return rentRequests;

    }

    @Override
    public RentRequest findById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(RentRequest.class, id);
    }

    @Override
    public List<RentRequest> findByBookId(int bookID) {
        List<RentRequest> rentRequests;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from RentRequest WHERE bookID =: id");
        query.setParameter("id", bookID);
        rentRequests = query.getResultList();
        return rentRequests;
    }

    @Override
    public List<RentRequest> findByEmployeeId(int employeeID) {
        List<RentRequest> rentRequests;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from RentRequest WHERE employeeID =: id");
        query.setParameter("id", employeeID);
        rentRequests = query.getResultList();
        return rentRequests;
    }

    @Override
    public RentRequest findByEmployeeAndBookID(int employeeID, int bookID) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from RentRequest WHERE employeeID =: id AND bookID =: id2").setMaxResults(1);
        query.setParameter("id", employeeID);
        query.setParameter("id2", bookID);
        return QueryUtil.safeGetUniqueResult(query.getResultList());
    }

    @Override
    public RentRequest findWaitingForCopyByBookId(int bookID) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from RentRequest WHERE bookID =: id AND status =: status").setMaxResults(1);
        query.setParameter("id", bookID);
        query.setParameter("status", RentRequestStatus.WAITINGAVAILABLECOPY.name());
        return QueryUtil.safeGetUniqueResult(query.getResultList());
    }

    @Override
    public List<RentRequest> findListWaitingForCopyByBookId(int bookID) {
        List<RentRequest> rentRequests;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from RentRequest WHERE bookID =: id AND status =: status");
        query.setParameter("id", bookID);
        query.setParameter("status", RentRequestStatus.WAITINGAVAILABLECOPY.name());
        rentRequests = query.getResultList();
        return rentRequests;
    }

    @Override
    public int delete(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM RentRequest where rentreqID =:id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
