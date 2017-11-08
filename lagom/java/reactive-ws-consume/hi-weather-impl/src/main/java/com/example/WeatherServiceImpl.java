package com.example;

import akka.NotUsed;
import com.example.WeatherService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.libs.ws.*;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

public class WeatherServiceImpl implements WeatherService {

    private final WSClient client;

    private final static String SERVICE_ADDRESS = "http://api.openweathermap.org/data/2.5/weather?zip=%s&APPID=%s";

    private final String apiKey;

    @Inject
    WeatherServiceImpl(WSClient client){
        this.client = client;
        Config config = ConfigFactory.load();

        apiKey = config.getString("api-key");
    }

    @Override
    public ServiceCall<NotUsed, String> hiWeather(String zipcode){
        return request -> {
            String address = String.format(SERVICE_ADDRESS, zipcode, apiKey);

            CompletionStage<String> response = client.url(address).get().thenApply(r-> happiFy(r.getBody()));

            return response;
        };
    }

    private String happiFy(String weather){
        return weather.replaceAll("[C|c]loud", "sunshiny");
    }
}
