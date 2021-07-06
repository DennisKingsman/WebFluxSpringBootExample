package com.webflux.example.springboot.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ReactServiceImpl {

    public String getBlockedString() {
        try{
            TimeUnit.SECONDS.sleep(5);
        }catch (InterruptedException e) {
            return e.getMessage();
        }
        return "Blocked string. ";
    }

}
