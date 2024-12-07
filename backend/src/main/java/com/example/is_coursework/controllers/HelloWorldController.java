package com.example.is_coursework.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Hello World Controller", description = "Тестовый контроллер")
public class HelloWorldController {
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
