package com.webflux.example.springboot.service;

import reactor.core.publisher.Mono;

public interface ReactService {

    String getBlocked();

    Mono<String> forConcatNonBlocked();

    String getSimple();

}
