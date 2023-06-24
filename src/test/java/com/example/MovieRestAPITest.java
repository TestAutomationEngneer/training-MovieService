package com.example;

import com.example.actor.ActorClient;
import com.example.model.Actor;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieRestAPITest {

    @Inject
    EmbeddedServer server;

    @Inject
    ActorClient actorClient;

    @MockBean(ActorClient.class)
    ActorClient getActorClient() {
        ActorClient actorClient = mock(ActorClient.class);
        Actor robert = Actor.builder().firstName("Robert").lastName("Deniro").rating(4).oscarPrized(true).build();
        Actor leonardo = Actor.builder().firstName("Leonardo").lastName("Dicaprio").rating(2).oscarPrized(true).build();
        Actor tom = Actor.builder().firstName("Tom").lastName("Hanks").rating(6).oscarPrized(true).build();
        when(actorClient.getActorById(2L)).thenReturn(robert);
        when(actorClient.getActorById(5L)).thenReturn(leonardo);
        when(actorClient.getActorById(1L)).thenReturn(tom);
        return actorClient;
    }


    @BeforeAll
    public void setup() {
        RestAssured.port = server.getPort();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "http://localhost:9595";
    }

    @Test
    void shouldGetCatchMeIfYouCaMovie() {
        given()
                .when()
                .get("/movies/5")
                .then()
                .statusCode(200).log().all()
                .body("title", equalTo("Catch Me If You Can"))
                .body("actors", hasSize(2));

    }

    @Test
    void shouldGetLeonardoFromCatchMeIfYouCanMovie() {
        given()
                .when()
                .get("/movies/5")
                .then()
                .statusCode(200).log().all()
                .body("title", equalTo("Catch Me If You Can"))
                .body("actors", hasSize(2))
                .body("actors[0].firstName", equalTo("Leonardo"));

    }

    @Test
    void shouldGetHeatMovie() {
        given()
                .when()
                .get("/movies/2")
                .then()
                .statusCode(200).log().all()
                .body("title", equalTo("Heat"))
                .body("actors", hasSize(1));

    }

    @Test
    void shouldGetRobertFromTitanicMovie() {
        given()
                .when()
                .get("/movies/2")
                .then()
                .statusCode(200).log().all()
                .body("title", equalTo("Heat"))
                .body("actors", hasSize(1))
                .body("actors[0].firstName", equalTo("Robert"));

    }

}
