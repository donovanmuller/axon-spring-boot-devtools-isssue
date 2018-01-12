package com.example;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;

import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ThingHandler {

    @EventHandler
    void on(ThingChanged event) {
        log.info("Thing changed: {}", event.getName());
    }
}
