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

    //For Lazy initializations and testing purpose
    public Employee findEmployeeByIdWithNicknames(Long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Employee employee = entityManager
                .createQuery("select e from Employee e join fetch e.nicknames where id = :id",Employee.class)
                .setParameter("id",id)
                .getSingleResult();
        entityManager.close();
        return employee;
    }

    public Employee findEmployeeByIdWithVacations(Long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Employee employee = entityManager
                .createQuery("select e from Employee e join fetch e.vacationBookings where id = :id",Employee.class)
                .setParameter("id",id)
                .getSingleResult();
        entityManager.close();
        return employee;
    }

    public Employee findEmployeeByIdWithPhoneNumbers(Long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Employee employee = entityManager
                .createQuery("select e from Employee e join fetch e.phoneNumbers where id = :id",Employee.class)
                .setParameter("id",id)
                .getSingleResult();
        entityManager.close();
        return employee;
    }

    public Employee findEmployeeById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, 1L);
        entityManager.close();
        return employee;
    }

    public List<Employee> updateEmployeeList() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Employee> employees = entityManager.createQuery("select e from  Employee e order by e.name", Employee.class).getResultList();
        //managed állapotba kerültek
        entityManager.getTransaction().begin();

        for (Employee employee : employees) {
            employee.setName(employee.getName() + "***");

            System.out.println(employee.getId() + "  modified");
            //már itt is frissül az adatbázisban és lefut az update nem a commitnál//
            //nem látható más tranzakció számára csak commit után
            //ha flush után olvasok akkor az új érték jelenik meg
            entityManager.flush();
        }

        entityManager.getTransaction().commit();
        entityManager.close();
        return employees;
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

    public void updateEmployee(Employee employee) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(employee);

        Employee merged = entityManager.merge(employee);//nem javasolt--inkább betöltöm és explicit paraméterekkel állítom be -find-set-com
        //ezzel átállítom ...

        employee.setName("****" + employee.getName());//never appears(out of persistence context--testing purpose
        merged.setName("***" + merged.getName());//this reference will appear
        entityManager.getTransaction().commit();
        entityManager.close();

    }

}
