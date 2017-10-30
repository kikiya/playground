/*
 * 
 */
package com.example.employee.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import java.util.List;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

/**
 * The person service interface.
 * <p>
 * This describes everything that Lagom needs to know about how to serve and
 * consume the EmployeeService.
 */
public interface EmployeeService extends Service {

  /**
   * Example: curl http://localhost:9000/api/employee/all
   */

  ServiceCall<Employee, Done> addEmployee();

  ServiceCall<NotUsed, Employee> retrieveByName(String name);

  ServiceCall<Employee, Done> modifyEmployeeDetails();

  ServiceCall<NotUsed, Done> removeEmployee(String name);

  ServiceCall<NotUsed, List<Employee>> findAll();

  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return named("person").withCalls(
            restCall(Method.GET, "/api/employee/all",  this::findAll),
            restCall(Method.POST, "/api/employee/add", this::addEmployee),
            restCall(Method.GET, "/api/employee/find/:name", this::retrieveByName),
            restCall(Method.PUT, "/api/employee/modify", this::modifyEmployeeDetails),
            restCall(Method.DELETE,"/api/employee/remove/:name", this::removeEmployee)
      ).withAutoAcl(true);
    // @formatter:on
  }
}
