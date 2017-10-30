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
import java.util.Optional;
import java.util.concurrent.CompletionStage;

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
        return request ->  crudTemplate.create(request);
    }

    @Override
    public ServiceCall<NotUsed, Optional<Employee>> retrieveByName(String name) {
        return request -> crudTemplate.retrieve(name);
    }

    @Override
    public ServiceCall<Employee, Done> modifyEmployeeDetails() {
        return request -> crudTemplate.update(request);
    }

    @Override
    public ServiceCall<NotUsed, Done> removeEmployee(String name) {
        return request -> crudTemplate.delete(name);
    }

    @Override
    public ServiceCall<NotUsed, List<Employee>> findAll() {
        return request -> crudTemplate.getDocs();
    }


}
