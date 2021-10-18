package com.grofandriska.JPAdemo.model.dao;

import com.grofandriska.JPAdemo.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

    public Employee findEmployeeById(Long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, 1L);
        return employee;
    }
}
