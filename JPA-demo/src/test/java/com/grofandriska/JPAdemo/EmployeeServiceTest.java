package com.grofandriska.JPAdemo;

import com.grofandriska.JPAdemo.model.Employee;
import com.grofandriska.JPAdemo.model.VacationEntry;
import com.grofandriska.JPAdemo.model.dao.EmployeeDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(List.of("Feri", "Peti"), employeeList.stream().map(Employee::getName).collect(Collectors.toList()));
    }

    @Test
    public void testChangeName() {
        Employee employee = new Employee(null, "John Doe");
        employeeDao.saveEmployee(employee);
        long id = employee.getId();
        employeeDao.changeName(id, "Petya");
        Employee loadedEmployee = employeeDao.findEmployeeById(id);
        assertEquals("Petya", loadedEmployee.getName());

    }

    @Test
    public void testDelete() {
        Employee employee = new Employee(null, "John Doe");
        employeeDao.saveEmployee(employee);
        long id = employee.getId();
        employeeDao.delete(id);
        List<Employee> employeeList = employeeDao.listAll();
        assertTrue(employeeList.isEmpty());
    }

    @Test
    public void testSaveEmployeeChangeState() {
        Employee employee = new Employee(null, "Nemo");
        employeeDao.saveEmployee(employee);

        employee.setName("Alfredo");
        Employee modifiedEmployee = employeeDao.findEmployeeById(employee.getId());

        assertEquals("Nemo", modifiedEmployee.getName());
        assertFalse(employee == modifiedEmployee);
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee(null, "Nemo");
        employeeDao.saveEmployee(employee);
        employee.setName("Nero");
        employeeDao.updateEmployee(employee);
        Employee modifiedEmployee = employeeDao.findEmployeeById(employee.getId());
        assertEquals("Nemo",modifiedEmployee.getName());

    }
    @Test
    public void testFlush() {
        for (int i = 0;i<11;i++){
            employeeDao.saveEmployee(new Employee(null, "Nemo" + i));
        }
        employeeDao.updateEmployeeList();

    }
    @Test
    public void nickname() {
        Employee employee = new Employee(null, "Nemo", Set.of("peti", "kati"));
        employeeDao.saveEmployee(employee);

        Employee anotherEmployee = employeeDao.findEmployeeById(employee.getId());
        System.out.println(anotherEmployee.getNicknames());
        assertEquals(Set.of("peti", "kati"),anotherEmployee.getNicknames());
    }
    @Test
    public void nicknameByID() {
        Employee employee = new Employee(null, "Nemo", Set.of("peti", "kati"));
        employeeDao.saveEmployee(employee);

        Employee anotherEmployee = employeeDao.findEmployeeByIdWithNicknames(employee.getId());
        System.out.println(anotherEmployee.getNicknames());
        assertEquals(Set.of("peti", "kati"),anotherEmployee.getNicknames());
    }

    @Test
    public void testVacation(){
        Employee employee = new Employee(null,"Feri");
        employee.setVacationBookings(Set.of(new VacationEntry(LocalDate.of(2020,9,9),3),
                                            new VacationEntry(LocalDate.of(2020,10,11),2)));
        employeeDao.saveEmployee(employee);

        Employee anotherEmployee = employeeDao.findEmployeeByIdWithVacations(employee.getId());
        System.out.println(anotherEmployee.getVacationBookings());
        assertEquals(2,anotherEmployee.getVacationBookings().size());

    }

    @Test
    public void testPhoneNumbers(){
        Employee employee = new Employee(null,"Petya");
        employee.setPhoneNumbers(Map.of("home","2255","work","44554"));
        employeeDao.saveEmployee(employee);
        System.out.println(employee.getPhoneNumbers());
        Employee another = employeeDao.findEmployeeByIdWithPhoneNumbers(employee.getId());
        System.out.println(another.getPhoneNumbers());
        assertEquals("2255",another.getPhoneNumbers().get("home"));

    }
}




