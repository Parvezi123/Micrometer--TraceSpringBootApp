package parvez.micrometer.tracespringbootapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parvez.micrometer.tracespringbootapp.entity.Employee;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private static final List<Employee> DB = new ArrayList<>();
    static {
        DB.add(new Employee("123","John","Developer"));
        DB.add(new Employee("456","Mike","Tester"));
        DB.add(new Employee("789","Daniel","Designer"));
    }

    @GetMapping
    List<Employee> retrieveEmployees() {
        return DB;
    }

    @GetMapping("/{id}")
    Employee retrieveEmployeeById(@PathVariable String id) {
        return DB.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow( () -> new RuntimeException("Invalid Employee Id"));
    }

}
