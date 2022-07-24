package com.thesis.bikerental.portfolio.employee.service;

import com.thesis.bikerental.portfolio.employee.domain.Employee;
import com.thesis.bikerental.utils.api.ApiSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<Employee> data(String search, int page, int size, int status) {
        return null;
    }

    @Override
    public Employee save(Employee employee) {
        try {
            employeeRepository.save(employee);
        }catch (Exception e) {
            return null;
        }
        return employee;
    }

    @Override
    public boolean deleteById(long id) {
        Employee employee = findById(id);

        if(employee == null) return false;

        employeeRepository.deleteById(id);

        return true;
    }

    @Override
    public Employee findById(long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElse(null);
    }

    @Override
    public ApiSettings apiSettings() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
