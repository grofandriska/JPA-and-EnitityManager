package com.grofandriska.JPAdemo;

import com.grofandriska.JPAdemo.model.Employee;
import com.grofandriska.JPAdemo.model.dao.EmployeeDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceTest {

    private EntityManagerFactory entityManagerFactory;

    private EmployeeDao employeeDao;

    @BeforeEach
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        employeeDao = new EmployeeDao(entityManagerFactory);
    }

    @AfterEach
    public void close() {
        entityManagerFactory.close();
    }

    @Test
    public void testSaveThenFind() {
        Employee employee = new Employee(null,"John Doe");
        employeeDao.saveEmployee(employee);
        Employee loadedEmployee = employeeDao.findEmployeeById(employee.getId());
        assertEquals("John Doe", loadedEmployee.getName());
    }
}




