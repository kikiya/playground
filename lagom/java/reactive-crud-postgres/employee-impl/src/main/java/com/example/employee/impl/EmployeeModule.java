/*
 * 
 */
package com.example.employee.impl;

import com.example.employee.api.EmployeeService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

/**
 * The module that binds the EmployeeService so that it can be served.
 */
public class EmployeeModule extends AbstractModule implements ServiceGuiceSupport {

  @Override
  protected void configure() {

      bindService(EmployeeService.class, EmployeeServiceImpl.class);
      bind(SimpleCrudTemplate.class).to(EmployeeTemplate.class);

  }

}
