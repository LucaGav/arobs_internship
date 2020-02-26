package com.arobs.internship.library.dao.impl.hibernate;

import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.entities.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class HibernateEmployeeDao implements EmployeeDao {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;


    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public int save(Employee employee) {
        //Session session = this.entityManager.unwrap(Session.class);
        Transaction transaction = null;
        try {
            Session session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Integer res = (Integer) session.save(employee);
            transaction.commit();
            return res;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Rollback");
            }
        }
        return 0;
    }

    @Override
    public List<Employee> findEmployees() {
        //Session session = this.entityManager.unwrap(Session.class);
        Transaction transaction = null;
        List<Employee> employees;
        try {
            Session session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Employee");
            employees = query.getResultList();
            transaction.commit();
            return employees;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Rollback");
            }
        }
        return null;
    }

    @Override
    public Employee findById(int id) {
        //Session session = this.entityManager.unwrap(Session.class);
        Transaction transaction = null;
        Employee employee;
        try {
            Session session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            employee = session.get(Employee.class, id);
            transaction.commit();
            return employee;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Rollback");
            }
        }
        return null;
    }

    @Override
    //@Transactional
    public int delete(String email) {
        //Session session = this.entityManager.unwrap(Session.class);
        Transaction transaction = null;
        try {
            Session session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Employee where email =:email");
            query.setParameter("email", email);
            query.executeUpdate();
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Rollback");
            }
        }
        return 0;
    }

    @Override
    //@Transactional
    public int update(Employee employee) {
        //Session session = this.entityManager.unwrap(Session.class);
        Transaction transaction = null;
        try {
            Session session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(employee);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Rollback");
            }
            e.printStackTrace();
        }
        return 0;
    }
}
