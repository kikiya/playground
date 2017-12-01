package com.example;

import akka.Done;
import akka.NotUsed;
import akka.stream.Materializer;
import akka.stream.alpakka.amqp.*;
import akka.stream.alpakka.amqp.javadsl.AmqpSink;
import akka.stream.alpakka.amqp.javadsl.AmqpSource;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Singleton
public class RabbitServiceImpl implements RabbitService {

    private final Materializer materializer;

    final String queueName = "amqp-conn-it-spec-simple-queue";
    final QueueDeclaration queueDeclaration = QueueDeclaration.create(queueName);
    final AmqpConnectionDetails amqpConnectionDetails;

    @Inject
    RabbitServiceImpl(Materializer materializer, AmqpConnectionDetails amqpConnectionDetails){

        this.amqpConnectionDetails = amqpConnectionDetails;
        this.materializer = materializer;
    }

    @Override
    public ServiceCall<Source<String, ?>, Done> saveJMS() {

        final Sink<ByteString, CompletionStage<Done>> amqpSink = AmqpSink.createSimple(
                AmqpSinkSettings.create(amqpConnectionDetails)
                        .withRoutingKey(queueName)
                        .withDeclarations(queueDeclaration)
        );

        return words ->

                words.take(10).map(ByteString::fromString).runWith(amqpSink, materializer);
    }

    @Override
    public ServiceCall<String, List<String>> readJMS() {

        final Integer bufferSize = 10;
        final Source<IncomingMessage, NotUsed> amqpSource = AmqpSource.atMostOnceSource(
                NamedQueueSourceSettings.create(
                        DefaultAmqpConnection.getInstance(),
                        queueName
                ).withDeclarations(queueDeclaration),
                bufferSize
        );
        return request -> {

            //#run-source
            final CompletionStage<List<String>> result =
                    amqpSource.map(m -> m.bytes().utf8String()).take(10).runWith(Sink.seq(), materializer);
            //#run-source

            return result;
        };
    }

    @Override
    public ServiceCall<Source<String, ?>, Source<String, ?>> twoWay() {

        final Sink<ByteString, CompletionStage<Done>> amqpSink = AmqpSink.createSimple(
                AmqpSinkSettings.create(amqpConnectionDetails)
                        .withRoutingKey(queueName)
                        .withDeclarations(queueDeclaration)
        );

        final Integer bufferSize = 10;

        final Source<IncomingMessage, NotUsed> amqpSource = AmqpSource.atMostOnceSource(
                NamedQueueSourceSettings.create(
                        DefaultAmqpConnection.getInstance(),
                        queueName
                ).withDeclarations(queueDeclaration),
                bufferSize
        );

        return words -> {

            //add words to the q and
            words.take(10).map(ByteString::fromString).runWith(amqpSink, materializer);

            //immediately read words from q
            return completedFuture(amqpSource.map(m -> "read from q: " + m.bytes().utf8String()));

        };
    }
}
