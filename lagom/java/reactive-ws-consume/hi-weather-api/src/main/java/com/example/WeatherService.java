package com.example;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

public interface WeatherService extends Service {

    ServiceCall<NotUsed, String>  hiWeather(String zipcode);


    @Override
    default Descriptor descriptor(){

            return named("weather").withCalls(
            pathCall("/api/happy-weather/:zipcode", this::hiWeather)
        ).withAutoAcl(true);
    }
}
