package com.example;

import akka.NotUsed;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.lambdaworks.redis.api.rx.RedisStringReactiveCommands;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import rx.RxReactiveStreams;

import java.util.Optional;

import javax.inject.Inject;

public class CacheServiceImpl implements CacheService {

    private final RedisStringReactiveCommands<String, String> reactiveCommands;

    private final Materializer materializer;

    @Inject
    CacheServiceImpl(Materializer materializer, RedisStringReactiveCommands<String, String> reactiveCommands){

        this.reactiveCommands = reactiveCommands;
        this.materializer = materializer;
    }

    @Override
    public ServiceCall<NotUsed, String> cacheValue(String key, String value) {
        return request -> {
            Source<String, NotUsed> source = Source.fromPublisher(RxReactiveStreams.toPublisher(reactiveCommands.set(key, value)));

            return source.runWith(Sink.head(), materializer);
        };
    }

    @Override
    public ServiceCall<NotUsed, Optional<String>> retrieveValue(String key) {
        return request -> {

            Source<String, NotUsed> source = Source.fromPublisher(RxReactiveStreams.toPublisher(reactiveCommands.get(key)));

            return source.runWith(Sink.headOption(), materializer);
        };
    }
}
