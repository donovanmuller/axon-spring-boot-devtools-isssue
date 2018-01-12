package com.example;

import org.axonframework.commandhandling.gateway.CommandGateway;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/things")
public class ThingController {

    private CommandGateway commandGateway;

    public ThingController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    String createThing(@RequestBody NewThing thing) {
        return commandGateway.sendAndWait(thing);
    }

    @PutMapping("/{id}")
    void updateThing(@PathVariable String id, @RequestBody ChangedThing thing) {
        commandGateway.sendAndWait(new ChangedThing(id, thing.getName()));
    }
}
