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

    /*@Provides
    @Singleton
    public com.rabbitmq.client.Channel rabbitChannel(){
        //todo change back to amqp
        Connection connection = null;
        Channel channel = null;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            com.rabbitmq.client.Connection connection1 = factory.newConnection();
            channel = connection1.createChannel();
            String exchangeName = "test";
            String queueName = "test";
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, exchangeName, "test");
            connection = io.scalac.amqp.Connection$.MODULE$.apply();

        }finally {
            return channel;
        }
    }*/

    @Provides
    @Singleton
    public AmqpConnectionDetails connectionDetails(){
        AmqpConnectionDetails amqpConnectionDetails = AmqpConnectionDetails.create("invalid", 5673)
                .withHostsAndPorts(Pair.create("localhost", 5672), Pair.create("localhost", 5674));

        return amqpConnectionDetails;
    }


}
