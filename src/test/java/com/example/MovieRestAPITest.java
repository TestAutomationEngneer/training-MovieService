package com.example;

import com.example.actor.ActorClient;
import com.example.base.TestBase;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@MicronautTest
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieRestAPITest extends TestBase {

    @Inject
    EmbeddedServer server;

    @Inject
    ActorClient actorClient;

    @BeforeAll
    public void setup() {
        RestAssured.port = server.getPort();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "http://localhost:9595";
    }

    @Test
    void shouldGetCatchMeIfYouCaMovie() {
        given().log().all()
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
                .body("actors[0].firstName", equalTo("Robercik"));  //z  @MockBean(ActorClient.class)
    }
}
