package com.techacademy.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Authentication;
import com.techacademy.entity.Employee;
import com.techacademy.repository.EmployeeRepository;


@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository repository) {
        this.employeeRepository = repository;

    }

    /** 全件を検索して返す */
    public List<Employee> getEmployeeList() {
        // リポジトリのfindAllメソッドを呼び出す
        return employeeRepository.findAll();
    }
    /** Userを1件検索して返す */
    public Employee getEmployee(Integer id) {
        return employeeRepository.findById(id).get();
    }

    /** Userの登録を行なう */
    @Transactional
    public Employee saveEmployee(Employee employee) throws Exception{
        String code = employee.getAuthentication().getCode();
        boolean exists = employeeRepository.existsByAuthenticationCode(code);

        if (exists) {
            throw new Exception("社員番号 '" + code + "' は既に存在します。");
        }
        return employeeRepository.save(employee);
    }

    /** Userの削除を行なう */
    @Transactional
    public void deleteUser(Integer id) {
            employeeRepository.deleteById(id);
    }

}