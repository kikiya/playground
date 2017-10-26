/*
 * 
 */
package com.example.employee.impl;

import akka.Done;
import akka.NotUsed;
import com.example.employee.api.Employee;
import com.example.employee.api.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the EmployeeService.
 */
public class EmployeeServiceImpl implements EmployeeService {

    private final SimpleCrudTemplate crudTemplate;

    @Inject
    public EmployeeServiceImpl(SimpleCrudTemplate crudTemplate) {

        this.crudTemplate = crudTemplate;
    }

    @Override
    public ServiceCall<Employee, Done> addEmployee() {
        return request -> {
            try {
                return crudTemplate.create(request);
            }catch (JsonProcessingException e){
                //todo change this to map to a reasonable http error
                throw new UnsupportedOperationException(e);
            }
        };
    }

    @Override
    public ServiceCall<NotUsed, Employee> retrieveByName(String name){ return request -> crudTemplate.retrieve(name); }

    @Override
    public ServiceCall<Employee, Done> modifyEmployeeDetails() {
        return request -> {
            try {
                return crudTemplate.update(request);
            }catch (JsonProcessingException e){

                throw new UnsupportedOperationException(e);
            }
        };
    }

    @Override
    public ServiceCall<NotUsed, Done> removeEmployee(String name) {
        return request -> crudTemplate.delete(name);
    }

    @Override
    public ServiceCall<NotUsed, List<Employee>> findAll() { return request -> crudTemplate.getDocs(); }


}
