package com.grofandriska.JPAdemo.repo;

import com.grofandriska.JPAdemo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repo extends JpaRepository<Employee,Long> {
}
