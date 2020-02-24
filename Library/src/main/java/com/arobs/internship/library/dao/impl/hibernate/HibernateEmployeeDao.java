package com.arobs.internship.library.dao.impl.hibernate;

import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.entities.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HibernateEmployeeDao implements EmployeeDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public int save(Employee employee) {
        Session session = this.entityManager.unwrap(Session.class);
        Integer returnedId = (Integer) session.save(employee);
        System.out.println("Ajung in save hibernate: " + returnedId);
        System.out.println("Save worked");
        return returnedId;
    }

    @Override
    public List<Employee> findEmployees() {
        Session session = this.entityManager.unwrap(Session.class);
        Query query = session.createQuery("FROM Employee");
        List<Employee> employees = query.getResultList();
        return employees;
    }

    @Override
    @Transactional
    public Employee findById(int id) {
        Session session = this.entityManager.unwrap(Session.class);
        Employee employee = session.get(Employee.class,id);
        return employee;
    }

    @Override
    @Transactional
    public int delete(String email) {
        Session session = this.entityManager.unwrap(Session.class);
        Query query = session.createQuery("DELETE FROM Employee where email =:email");
        query.setParameter("email",email);
        query.executeUpdate();
        return 1;
    }

    @Override
    @Transactional
    public int update(Employee employee) {
        Session session = this.entityManager.unwrap(Session.class);
        session.update(employee);
        return 1;
    }
}
