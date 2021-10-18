package com.grofandriska.JPAdemo;

import com.grofandriska.JPAdemo.model.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class JpaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaDemoApplication.class, args);
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Employee employee = new Employee(null, "Bandi");
        em.persist(employee);
        em.getTransaction().commit();
        System.out.println(employee.getId());

        long id = employee.getId();

        employee = em.find(Employee.class, id);
        //EM cacheli tehát nem kell lekérdeznie újra ( see log)
        System.out.println(employee);

        em.getTransaction().begin();
        employee = em.find(Employee.class, id);
        employee.setName("Andris");
        em.getTransaction().commit();

        List<Employee> employeeList = em.createQuery("select e from Employee e ", Employee.class).getResultList();
        System.out.println(employeeList);

        em.getTransaction().begin();
        employee = em.find(Employee.class, 1L);
        em.remove(employee);
        em.getTransaction().commit();

        employeeList = em.createQuery("select e from Employee e ", Employee.class).getResultList();
        System.out.println(employeeList);

        em.close();
        factory.close();
    }
}
