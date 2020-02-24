package com.arobs.internship.library.dao.impl.jdbc;

import com.arobs.internship.library.dao.EmployeeDao;
import com.arobs.internship.library.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class JdbcEmployeeDao implements EmployeeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Employee employee) {
        return jdbcTemplate.update("insert into employee (firstName,lastName,role,password,email) " +
                        "values(?,?,?,?,?)",
                employee.getFirstName(),employee.getLastName(),
                employee.getRole(),employee.getPassword(),employee.getEmail());
    }

    @Override
    public List<Employee> findEmployees() {
        List<Employee> employees = jdbcTemplate.query("SELECT * FROM employee",
                (rs,rowNum)-> new Employee(
                                rs.getInt("employeeID"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("role"),
                                rs.getString("password"),
                                rs.getString("email")
                             )
                 );
        return employees;
    }

    @Override
    public Employee findById(int id) {
        Employee employee;
        try {
            employee = jdbcTemplate.queryForObject("SELECT * FROM employee WHERE employeeID = ?", new Object[]{id},
                    (rs, rowNum) -> new Employee(
                            rs.getInt("employeeID"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("role"),
                            rs.getString("password"),
                            rs.getString("email")
                    )
            );
        } catch (DataAccessException e) {
            employee = null;
        }
        return employee;
    }

    @Override
    public int delete(String email) {
        return jdbcTemplate.update("DELETE FROM employee WHERE email = ?",new Object[]{email}
                );
    }

    @Override
    public int update(Employee employee) {
        return jdbcTemplate.update("UPDATE employee set firstName = ?, lastName = ?, email = ? WHERE employeeID = ?",
                employee.getFirstName(),employee.getLastName(),employee.getEmail(),employee.getEmployeeID());
    }


}
