package com.example.actor;

import com.example.model.Actor;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.client.annotation.Client;

import java.util.List;


@Client(id = "actor", path = "")
public interface ActorClient {


    @Get("/actors/{id}")
    Actor getActorById(@PathVariable Long id);

    @Get("/actors")
    List<Actor> getAllActors();
}


