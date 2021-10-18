package com.grofandriska.JPAdemo.model.dao;

import com.grofandriska.JPAdemo.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class EmployeeDao {

    private EntityManagerFactory entityManagerFactory;

    public EmployeeDao() {
    }

    public EmployeeDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void saveEmployee(Employee employee) {
        EntityManager entityManager = entityManagerFactory.createEntityManager(); //persistance context
        entityManager.getTransaction().begin(); //új tranzakció
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Employee findEmployeeById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        /* entityManager.getTransaction().begin();*/
        Employee employee = entityManager.find(Employee.class, 1L);
        entityManager.close();
        return employee;
    }

    public List<Employee> listAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Employee> employees = entityManager.createQuery("select e from  Employee e order by e.name", Employee.class).getResultList();
        entityManager.close();
        return employees;
    }

    public void changeName(Long id, String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, id);
        employee.setName(name);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void delete(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Employee employee = entityManager.getReference(Employee.class, id);
        entityManager.remove(employee);
        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
