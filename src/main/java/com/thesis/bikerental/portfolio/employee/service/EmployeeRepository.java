package com.thesis.bikerental.portfolio.employee.service;

import com.thesis.bikerental.portfolio.employee.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT e from Employee e where e.isActive = true and e.user.firstName like %:search% ")
    Page<Employee> getEmployees(@Param("search") String search, Pageable pageable);
}
