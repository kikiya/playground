package com.example;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class WeatherModule extends AbstractModule implements ServiceGuiceSupport {

    @Override
    protected void configure() {
        bindService(WeatherService.class, WeatherServiceImpl.class);
    }
}
