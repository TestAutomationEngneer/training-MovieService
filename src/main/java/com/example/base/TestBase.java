package com.example.base;

import com.example.actor.ActorClient;
import com.example.model.Actor;
import io.micronaut.test.annotation.MockBean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestBase {

    @MockBean(ActorClient.class)
    public ActorClient getActorClient() {
        ActorClient actorClient = mock(ActorClient.class);
        Actor robert = Actor.builder().firstName("Robercik").lastName("Deniro").rating(4).oscarPrized(true).build();
        Actor leonardo = Actor.builder().firstName("Leonardo").lastName("Dicaprio").rating(2).oscarPrized(true).build();
        Actor tom = Actor.builder().firstName("Tom").lastName("Hanks").rating(6).oscarPrized(true).build();
        when(actorClient.getActorById(2L)).thenReturn(robert);
        when(actorClient.getActorById(5L)).thenReturn(leonardo);
        when(actorClient.getActorById(1L)).thenReturn(tom);
        return actorClient;
    }
}
