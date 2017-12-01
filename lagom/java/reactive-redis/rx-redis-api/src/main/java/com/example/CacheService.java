package com.example;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import java.util.Optional;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

public interface CacheService extends Service {

    ServiceCall<NotUsed, String> cacheValue(String key, String value);

    ServiceCall<NotUsed, Optional<String>> retrieveValue(String key);


    @Override
    default Descriptor descriptor() {

        return named("redis").withCalls(
                pathCall("/api/cache/:key/:value", this::cacheValue),
                pathCall("/api/retrieve/:key", this::retrieveValue)

        ).withAutoAcl(true);
    }
}
