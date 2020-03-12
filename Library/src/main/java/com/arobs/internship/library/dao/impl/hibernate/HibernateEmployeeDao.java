package com.arobs.internship.library.dao.impl.hibernate;

import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.dao.impl.hibernate.util.QueryUtil;
import com.arobs.internship.library.entities.Employee;
import com.arobs.internship.library.util.entities.EmployeeUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class HibernateEmployeeDao implements EmployeeDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(HibernateEmployeeDao.class);

    @Override
    public Employee insert(Employee employee) {
        Session session = this.sessionFactory.getCurrentSession();
        employee.setPassword(EmployeeUtil.passwordEncryption(employee.getPassword()));
        session.save(employee);
        return employee;
    }

    @Override
    public List<Employee> findEmployees() {
        List<Employee> employees;
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Employee");
        employees = query.getResultList();
        return employees;
    }

    @Override
    public Employee findById(int id) {
        Employee employee;
        Session session = this.sessionFactory.getCurrentSession();
        employee = session.get(Employee.class, id);
        return employee;
    }

    @Override
    public Employee findByEmail(String email) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Employee WHERE email =: email");
        query.setParameter("email", email);
        return QueryUtil.safeGetUniqueResult(query.getResultList());
    }

    @Override
    public int delete(String email) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Employee where email =:email");
        query.setParameter("email", email);
        return query.executeUpdate();
    }

    @Override
    public Employee update(Employee employee) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(employee);
        return employee;
    }
}
