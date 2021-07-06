package com.webflux.example.springboot.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class ReactServiceImpl implements ReactService {

    public Mono<String> forConcatNonBlocked() {
        return Mono.just("Non blocked. ").delayElement(Duration.ofSeconds(5));
    }

    public String getBlocked() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            return e.getMessage();
        }
        return "Blocked string. ";
    }

    public String getSimple() {
        return "Hello simple. ";
    }

}
