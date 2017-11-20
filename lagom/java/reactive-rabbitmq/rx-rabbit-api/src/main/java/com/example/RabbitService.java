package com.example;

import akka.Done;
import akka.stream.javadsl.Source;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import java.util.List;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

public interface RabbitService extends Service {

    ServiceCall<Source<String, ?>, Done>  saveJMS();

    ServiceCall<String, List<String>> readJMS();

    ServiceCall<Source<String, ?>, Source<String, ?>> twoWay();


    @Override
    default Descriptor descriptor() {

        return named("redis").withCalls(
                pathCall("/api/words/", this::saveJMS),
                pathCall("/api/reads/", this::readJMS),
                pathCall("/api/twoway/", this::twoWay)

        ).withAutoAcl(true);
    }
}
