package com.thesis.bikerental.portfolio.employee.service;

import com.thesis.bikerental.portfolio.employee.domain.Employee;
import com.thesis.bikerental.utils.api.ApiSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final ApiSettings apiSettings = new ApiSettings(0,0,0,0,0);

    @Override
    public List<Employee> data(String search, int page, int size, int status) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Employee> pages = employeeRepository.getEmployees(search,pageable);
        System.out.println(pages.getContent());
        apiSettings.initApiSettings(size,page,pages.getTotalPages(),pages.getTotalElements());
        return pages.getContent();
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
        return apiSettings;
    }

    @Override
    public long count() {
        return 0;
    }
}
