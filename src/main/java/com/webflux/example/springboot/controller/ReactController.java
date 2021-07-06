package com.webflux.example.springboot.controller;

import com.webflux.example.springboot.service.ReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/react")
public class ReactController {

    private ReactService reactService;

    @Autowired
    public void setReactService(ReactService reactService) {
        this.reactService = reactService;
    }

    @GetMapping("/simple")
    public String getSimpleString() {
        return "Simple hello. ";
    }

    @GetMapping("/react")
    public Mono<String> getReactString() {
        return Mono.just("React hello. ");
    }

    @GetMapping("/blocked")
    public Mono<String> getBlockedByFiveSecString() {
        return Mono.just(reactService.getBlockedString());
    }

}
