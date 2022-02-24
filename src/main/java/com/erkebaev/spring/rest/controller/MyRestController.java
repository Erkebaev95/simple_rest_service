package com.erkebaev.spring.rest.controller;

import com.erkebaev.spring.rest.entity.Employee;
import com.erkebaev.spring.rest.exception_handling.EmployeeIncorrectData;
import com.erkebaev.spring.rest.exception_handling.NoSuchEmployeeException;
import com.erkebaev.spring.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRestController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> showAllEmployees() {
        List<Employee> allEmployee = employeeService.getAllEmployees();
        return allEmployee;
    }

    // Создаем метод для получения клиента по id
    // что бы найти id клиента нам необходима прописать аннотацию PathVariable
    // в параметрах которая читает переменную из 'url'
    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable int id) {

        // получаем id клиента
        Employee employee = employeeService.getEmployee(id);

        // создаем сообщения об ошибке
        if (employee == null) {
            throw new NoSuchEmployeeException("There is not employee" +
                    "with Id = " + id + " in Database");
        }

        return employee;
    }

    // показывает информативную ошибку в виде 'json' для 'NoSuchEmployeeException'
    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handlerException(NoSuchEmployeeException exception) {
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    // показывает информативную ошибку в виде 'json' для всех Exception
    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handlerException(Exception exception) {
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
