package com.thesis.bikerental.portfolio.employee.service;

import com.thesis.bikerental.portfolio.employee.domain.Employee;
import com.thesis.bikerental.utils.api.ServiceGraphQL;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService extends ServiceGraphQL<Employee> {
}
