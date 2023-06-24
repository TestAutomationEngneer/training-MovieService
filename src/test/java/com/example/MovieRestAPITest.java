package com.example;

import com.example.repository.MovieRepository;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@MicronautTest
@Slf4j
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieRestAPITest {

//    @Inject
//    private EmbeddedServer server;
//
//    @Inject
//    MovieRepository movieRepository;
//
//    @BeforeAll
//    public void setup() {
//        RestAssured.port = server.getPort();
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//        RestAssured.basePath = "http://localhost:9595";
//    }

    @Test
    void shouldGetTitanicMovie(RequestSpecification spec) {
       spec. given()
                .when()
                .get("/movies/3")
                .then()
                .statusCode(200)
                .body(equalTo("Titanic"));
    }

}
