package com.example;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.rx.RedisStringReactiveCommands;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class CacheModule extends AbstractModule implements ServiceGuiceSupport {

    @Override
    protected void configure() {
        bindService(CacheService.class, CacheServiceImpl.class);
    }

    @Provides
    @Singleton
    public RedisStringReactiveCommands<String, String> reactiveCommands(){
        RedisClient client = RedisClient.create("redis://localhost");
        RedisStringReactiveCommands<String, String> reactiveCommands = client.connect().reactive();

        return reactiveCommands;
    }
}
