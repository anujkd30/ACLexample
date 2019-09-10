package com.gsol.ACLexample.repository;

import com.gsol.ACLexample.model.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    //define field for entity manager
    private EntityManager entityManager;

    //set up constructor injection
    @Autowired
    public EmployeeDaoImpl(EntityManager theEntityManager){
        this.entityManager = theEntityManager;
    }

    @Override
    public List<Employee> getAllEmployees() {
        //get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        //create a query
        Query<Employee> theQuery = currentSession.createQuery("from Employee",Employee.class);

        //return the results
        return theQuery.getResultList();
    }

    @Override
    public Employee getEmployeeById(int id) {
        //get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        //Create a query and return the result
        return currentSession.get(Employee.class,id);
    }

    @Override
    public void deleteEmployeeById(int theId) {
        //get the hibernate currentSession
        Session currentSession = entityManager.unwrap(Session.class);

        //delete entry with primary key
        Query theQuery = currentSession.createQuery("delete from Employee where id=:employeeId");

        theQuery.setParameter("employeeId" ,theId);

        theQuery.executeUpdate();
    }


    @Override
    public void save(Employee employee) {
        //get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        //save the employee
        currentSession.saveOrUpdate(employee);
    }
}
