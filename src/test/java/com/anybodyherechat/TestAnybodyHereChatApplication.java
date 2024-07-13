package com.anybodyherechat;

import org.springframework.boot.SpringApplication;

public class TestAnybodyHereChatApplication {

    public static void main(String[] args) {
        SpringApplication.from(AnybodyHereChatApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
