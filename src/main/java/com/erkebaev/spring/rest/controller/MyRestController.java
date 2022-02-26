package com.erkebaev.spring.rest.controller;

import com.erkebaev.spring.rest.entity.Employee;
import com.erkebaev.spring.rest.exception_handling.NoSuchEmployeeException;
import com.erkebaev.spring.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Добавляем нового сотрудника
    // PostMapping ссылается на страницу employees
    @PostMapping("/employees")
    public Employee addNewEmployee(@RequestBody Employee employee) {
        // добавляем сотрудника
        employeeService.saveEmployee(employee);

        return employee;
    }

    // изменяем данные
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {

        employeeService.saveEmployee(employee);
        return employee;
    }

    // удаляем сотрудника
    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable int id) {
        // проверяем id сотрудника
        // если нет такого id то выбрасываем Exception
        Employee employee = employeeService.getEmployee(id);
        if (employee == null) {
            throw new NoSuchEmployeeException("There is not employee with id = " +
                    id + " in Database");
        }

        // удаляем по id сотрудника
        employeeService.deleteEmployee(id);
        return "Employee with id = " + id + " was deleted";
    }

}
