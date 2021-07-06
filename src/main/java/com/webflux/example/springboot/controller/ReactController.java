package com.webflux.example.springboot.controller;

import com.webflux.example.springboot.service.ReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/react")
public class ReactController {

    private ReactService rct;

    @Autowired
    public void setRct(ReactService rct) {
        this.rct = rct;
    }

    @GetMapping("/simple")
    public String getSimple() {
        return rct.getSimple();
    }

    @GetMapping("/react")
    public Mono<String> getReact() {
        return Mono.just(rct.getSimple());
    }

    @GetMapping("/blocked")
    public Mono<String> getBlockedByFiveSec() {
        Scheduler scheduler = Schedulers.newBoundedElastic(
                5,
                10,
                "MyThreadGroup");
        return Mono.just(rct.getBlocked())
                .publishOn(scheduler)
                .doOnNext(v -> System.out.println(
                        "onNext: " + Thread.currentThread().getName()));
    }

    @GetMapping("/nonBlocked/concat")
    public Flux<String> getConcat() {
        return rct
                .forConcatNonBlocked()
                .mergeWith(rct.forConcatNonBlocked());
    }

    @GetMapping("/fives")
    public Flux<String> getFiveBlocked() {
        return Flux.just(rct.getBlocked())
                .mergeWith(Flux.just(rct.getBlocked()))
                .mergeWith(Flux.just(rct.getBlocked()))
                .mergeWith(Flux.just(rct.getBlocked()))
                .mergeWith(Flux.just(rct.getBlocked()))
                .publishOn(Schedulers.parallel()) //check newParallel and elastic
                .doOnNext(v -> System.out.println(
                        "onNext: " + Thread.currentThread().getName()));
    }

    @GetMapping("/web-client")
    public Mono<String> getByWebClient() {
        WebClient webClient = WebClient.create("http://localhost:8080/react");

        Mono<String> monoResult = webClient.get()
                .uri("/react")
                .retrieve()
                .bodyToMono(String.class);
        //do smth with monoResult
        return monoResult;
    }

}
