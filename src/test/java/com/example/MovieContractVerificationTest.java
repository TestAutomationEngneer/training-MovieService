package com.example;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.actor.ActorClient;
import com.example.model.Actor;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "actor", hostInterface = "localhost", port = "9999")
@MicronautTest(environments = "pact")
@Tag("pact")
@Slf4j
@Property(name = "micronaut.http.services.actor.url", value = "http://localhost:9999")
public class MovieContractVerificationTest {

    @Inject
    private ActorClient actorClient;

    @Pact(consumer = "movie", provider = "actor")
    public RequestResponsePact getMovie(PactDslWithProvider builder) {
        return builder
                .given("Tomasz Karolak exists")
                .uponReceiving("GET request for actor")
                .path("/actors/99")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(HttpStatus.OK.getCode())
                .headers(Collections.singletonMap("Content-Type", "application/json"))
                .body(getActorPactBody())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getMovie")
    void shouldGetProperActorBasedOnMicronautClient() {
        Actor actor = actorClient.getActorById(99L);
        assertThat(actor).isNotNull();
        log.info(String.format("Pact test %s passed", "shouldGetProperActorBasedOnMicronautClient"));
    }

    @Test
    @PactTestFor(pactMethod = "getMovie")
    void shouldGetProperActorBasedOnMockServer(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/actors/99")
                .execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
        log.info(String.format("Pact test %s passed", "shouldGetProperActorBasedOnMockServer"));
    }

    private DslPart getActorPactBody() {
        return new PactDslJsonBody()
                .minArrayLike("actor", 1)
                .stringType("firstName", "Tomasz")
                .closeObject()
                .closeArray();
    }
}
