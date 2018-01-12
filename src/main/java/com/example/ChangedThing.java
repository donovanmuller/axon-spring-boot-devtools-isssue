package com.example;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class ChangedThing {

    @TargetAggregateIdentifier
    private String id;

    private String name;
}
