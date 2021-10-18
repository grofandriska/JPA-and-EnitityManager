package com.grofandriska.JPAdemo;

import com.grofandriska.JPAdemo.model.Employee;
import com.grofandriska.JPAdemo.model.dao.EmployeeDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeServiceTest {

    private EntityManagerFactory entityManagerFactory;

    private EmployeeDao employeeDao;

    @BeforeEach
    public void init() {
        /*FLYWAY
         * (MYSQL)Datasource datasource= new MysqlDataSource();
         * datasource.setURL(jdbc:mysql://localhost/....);
         *           .setUser();
         *           .setPassword();
         *
         *Flyway flyway = new Flyway();
         *       flyway.setDataSource(datasource);
         *             .clean();
         *             .migrate();
         *
         *  entityManagerFactory = Persistence.createEntityManagerFactory("pu");
         *    employeeDao = new EmployeeDao(entityManagerFactory);
         *
         *
         *
         * */
        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        employeeDao = new EmployeeDao(entityManagerFactory);
    }

    @AfterEach
    public void close() {
        entityManagerFactory.close();
    }

    @Test
    public void testSaveThenFindById() {
        Employee employee = new Employee(null, "John Doe");
        employeeDao.saveEmployee(employee);
        long id = employee.getId();
        Employee loadedEmployee = employeeDao.findEmployeeById(id);
        assertEquals("John Doe", loadedEmployee.getName());
    }

    @Test
    public void testSaveThenListAll() {
        employeeDao.saveEmployee(new Employee(null, "Peti"));
        employeeDao.saveEmployee(new Employee(null, "Feri"));
        List<Employee> employeeList = employeeDao.listAll();
        assertEquals(List.of("Feri","Peti"),employeeList.stream().map(Employee::getName).collect(Collectors.toList()));
    }

    @Test
    public void testChangeName(){
        Employee employee = new Employee(null, "John Doe");
        employeeDao.saveEmployee(employee);
        long id = employee.getId();
        employeeDao.changeName(id,"Petya");
        Employee loadedEmployee = employeeDao.findEmployeeById(id);
        assertEquals("Petya", loadedEmployee.getName());

    }

    @Test
    public void testDelete(){
        Employee employee = new Employee(null, "John Doe");
        employeeDao.saveEmployee(employee);
        long id = employee.getId();
        employeeDao.delete(id);
        List<Employee> employeeList = employeeDao.listAll();
        assertTrue(employeeList.isEmpty());
    }
}




