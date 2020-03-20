package com.arobs.internship.library.dao.impl.hibernate.employee;

import com.arobs.internship.library.dao.SuspendedEmployeeDao;
import com.arobs.internship.library.dao.impl.hibernate.util.QueryUtil;
import com.arobs.internship.library.entities.employee.SuspendedEmployee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class HibernateSuspendedEmployeeDao implements SuspendedEmployeeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public SuspendedEmployee insert(SuspendedEmployee suspendedEmployee) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(suspendedEmployee);
        return suspendedEmployee;
    }

    @Override
    public List<SuspendedEmployee> findSuspendedEmployees() {
        List<SuspendedEmployee> suspendedEmployees;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("From SuspendedEmployee");
        suspendedEmployees = query.getResultList();
        return suspendedEmployees;
    }

    @Override
    public SuspendedEmployee findByID(int id) {
        SuspendedEmployee suspendedEmployee;
        Session session = this.sessionFactory.getCurrentSession();
        suspendedEmployee = session.get(SuspendedEmployee.class,id);
        return suspendedEmployee;
    }

    @Override
    public SuspendedEmployee findByEmployeeID(int employeeID) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM SuspendedEmployee WHERE employeeID =: id");
        query.setParameter("id", employeeID);
        return QueryUtil.safeGetUniqueResult(query.getResultList());
    }

    @Override
    public int delete(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM SuspendedEmployee WHERE suspendedEmployeeID =: id");
        query.setParameter("id",id);
        return query.executeUpdate();
    }
}
