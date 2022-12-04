package com.thesis.bikerental.portfolio.employee.api;

import com.thesis.bikerental.portfolio.employee.domain.Employee;
import com.thesis.bikerental.portfolio.employee.service.EmployeeService;
import com.thesis.bikerental.portfolio.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UserServiceImpl userService;


    @PatchMapping
    public ResponseEntity<HashMap<String, ?>> updateEmployee(@RequestBody Employee employee){
        HashMap<String ,?> hashMap = new HashMap<>();

        employeeService.save(employee);

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String, ?>> deleteEmployee(@RequestParam("id") long id){
        HashMap<String ,?> hashMap = new HashMap<>();

        employeeService.deleteById(id);

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HashMap<String, ?>> createEmployee(@RequestBody Employee employee){
        HashMap<String ,?> hashMap = new HashMap<>();

        if(employee.getUser() != null){
            userService.save(employee.getUser());
        }

        employeeService.save(employee);
        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }


    @SchemaMapping(typeName = "Query",value = "employees")
    public List<Employee> getAllEmployee(@Argument String search, @Argument int page, @Argument int size, @Argument int status){

        return employeeService.data(search,page,size,status);
    }


    @SchemaMapping(typeName = "Query",value = "employeeById")
    public Employee getEmployeeById(@Argument long id){

        return employeeService.findById(id);
    }

    @GetMapping("/settings")
    public ResponseEntity<?> settings() {

        HashMap<String, Object> result = new HashMap<>();
        result.putIfAbsent("data", employeeService.apiSettings());


        return new ResponseEntity<>(result,HttpStatus.OK);
    }


}
