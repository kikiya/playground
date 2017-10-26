/*
 * 
 */
package com.example.employee.impl;

import com.example.employee.api.EmployeeService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;

/**
 * The module that binds the EmployeeService so that it can be served.
 */
public class EmployeeModule extends AbstractModule implements ServiceGuiceSupport {

  @Override
  protected void configure() {

      bindService(EmployeeService.class, EmployeeServiceImpl.class);
      bind(SimpleCrudTemplate.class).to(EmployeeTemplate.class);
  }

    @Provides
    @Singleton
    public MongoCollection mongoCollection(){
        return MongoClients.create().getDatabase("salaries").getCollection("employee_chicago_salaries");
    }
}
