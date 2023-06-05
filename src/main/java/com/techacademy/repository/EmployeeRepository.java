package com.techacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techacademy.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    boolean existsByAuthenticationCode(String code);

}