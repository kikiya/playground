package com.example;

import akka.japi.Pair;
import akka.stream.alpakka.amqp.AmqpConnectionDetails;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;


public class RabbitModule extends AbstractModule implements ServiceGuiceSupport {

    @Override
    protected void configure() {
        bindService(RabbitService.class, RabbitServiceImpl.class);
    }

    @Provides
    @Singleton
    public AmqpConnectionDetails connectionDetails(){
        AmqpConnectionDetails amqpConnectionDetails = AmqpConnectionDetails.create("localhost", 5673)
                .withHostsAndPorts(Pair.create("localhost", 5672), Pair.create("localhost", 5674));

        return amqpConnectionDetails;
    }


}
