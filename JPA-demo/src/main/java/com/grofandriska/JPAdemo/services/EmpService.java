package com.grofandriska.JPAdemo.services;

import com.grofandriska.JPAdemo.model.Employee;
import com.grofandriska.JPAdemo.repo.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpService {

    private Repo repo;

    public EmpService() {
    }

    public EmpService(Repo repo) {
        this.repo = repo;
    }

    public List<Employee> get() {
        return repo.findAll();
    }
}
