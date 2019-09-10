package com.gsol.ACLexample.service;

import com.gsol.ACLexample.model.Employee;

import java.util.List;

public interface EmployeeService{
    public List<Employee> getAllEmployees();
    public Employee getEmployeeById(int id);
    public void deleteEmployeeById(int id);
    public void save(Employee employee);
}