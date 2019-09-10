package com.gsol.ACLexample.service;

import com.gsol.ACLexample.model.Employee;
import com.gsol.ACLexample.repository.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao theEmployeeDao){
        this.employeeDao = theEmployeeDao;
    }

    @Override
    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    @Override
    @Transactional
    public Employee getEmployeeById(int id) {
        return employeeDao.getEmployeeById(id);
    }

    @Override
    @Transactional
    public void deleteEmployeeById(int id) {
        employeeDao.deleteEmployeeById(id);
    }


    @Override
    @Transactional
    public void save(Employee employee) {
        employeeDao.save(employee);
    }
}
