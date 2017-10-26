/*
 * 
 */
package com.example.person.impl;

import akka.Done;
import akka.NotUsed;
import com.example.person.api.Employee;
import com.example.person.api.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.deser.RawExceptionMessage;
import com.lightbend.lagom.javadsl.api.transport.MessageProtocol;
import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import org.apache.http.entity.ContentType;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the PersonService.
 */
public class PersonServiceImpl implements PersonService {

    private final CrudTemplate crudTemplate;

    @Inject
    public PersonServiceImpl(CrudTemplate crudTemplate) {

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
    public ServiceCall<NotUsed, List<Employee>> retrieveByName(String name){
        return request -> crudTemplate.retrieve(name);
    }

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
    public ServiceCall<NotUsed, List<Employee>> findAll() {

        return request ->
                crudTemplate.getDocs();
    }


}
