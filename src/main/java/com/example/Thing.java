package com.example;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Thing {

    @AggregateIdentifier
    private String id;

    private String name;

    Thing() {
    }

    @CommandHandler
    public Thing(NewThing command) {
        apply(new ThingCreated(command.getId(), command.getName()));
    }

    @EventSourcingHandler
    void on(ThingCreated event) {
        this.id = event.getId();
        this.name = event.getName();
    }

    @CommandHandler
    void changeThing(ChangedThing command) {
        apply(new ThingChanged(command.getName()));
    }

    @EventSourcingHandler
    void on(ThingChanged event) {
        this.name = event.getName();
    }
}
