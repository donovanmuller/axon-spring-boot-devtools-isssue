package com.example;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class NewThing {

    @TargetAggregateIdentifier
    private String id;

    private String name;
}
