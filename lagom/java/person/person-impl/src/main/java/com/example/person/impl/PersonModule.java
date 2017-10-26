/*
 * 
 */
package com.example.person.impl;

import com.example.person.api.PersonService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;

/**
 * The module that binds the PersonService so that it can be served.
 */
public class PersonModule extends AbstractModule implements ServiceGuiceSupport {

  @Override
  protected void configure() {

      bindService(PersonService.class, PersonServiceImpl.class);
      bind(CrudTemplate.class).to(PersonTemplate.class);
  }

    @Provides
    @Singleton
    public MongoCollection mongoCollection(){
        return MongoClients.create().getDatabase("salaries").getCollection("employee_chicago_salaries");
    }
}
