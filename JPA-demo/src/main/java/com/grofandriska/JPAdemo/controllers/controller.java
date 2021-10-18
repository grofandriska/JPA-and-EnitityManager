package com.grofandriska.JPAdemo.controllers;

import com.grofandriska.JPAdemo.services.EmpService;
import com.grofandriska.JPAdemo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class controller {

    @Autowired
    private EmpService empService;

    public controller(EmpService empService) {
        this.empService = empService;
    }

    @GetMapping("get")
    private List<Employee> get() {
        return empService.get();

    }
}
