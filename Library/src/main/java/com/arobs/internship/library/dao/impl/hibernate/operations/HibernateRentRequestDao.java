package com.arobs.internship.library.dao.impl.hibernate.operations;

import com.arobs.internship.library.dao.RentRequestDao;
import com.arobs.internship.library.entities.operations.RentRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class HibernateRentRequestDao implements RentRequestDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public RentRequest insert(RentRequest rentRequest) {
        Session session = this.sessionFactory.getCurrentSession();
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
        return session.get(RentRequest.class,id);
    }

    @Override
    public int delete(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM RentRequest where rentreqID =:id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
