package com.gsol.ACLexample.repository;

import com.gsol.ACLexample.model.Employee;

import java.util.List;

public interface EmployeeDao {
    public List<Employee> getAllEmployees();
    public Employee getEmployeeById(int id);
    public void deleteEmployeeById(int id);
    public void save(Employee employee);
}
