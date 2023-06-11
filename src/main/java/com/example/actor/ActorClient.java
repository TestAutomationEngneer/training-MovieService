package com.example.actor;

import com.example.model.Actor;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.List;


@Client("http://localhost:9292")
public interface ActorClient {


    @Get("/actors/{id}")
    Actor getActorById(Long id);

    @Get("/actors")
    List<Actor> getAllActors();
}


