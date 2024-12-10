package com.example.is_coursework.controllers;


import com.example.is_coursework.dto.requests.HeroRequest;
import com.example.is_coursework.dto.responses.FactResponse;
import com.example.is_coursework.dto.responses.HeroResponse;
import com.example.is_coursework.services.HeroService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/heroes")
public class HeroController {
    private final HeroService heroService;

    @PostMapping("/create")
    public HeroResponse createHero(Authentication authentication, @RequestBody HeroRequest heroRequest) {
        String username = authentication.getName();
        return heroService.createHero(heroRequest, username);
    }

    @GetMapping("/{id}")
    public HeroResponse getHeroById(@PathVariable Long id) {
        return heroService.getHeroById(id);
    }

    @GetMapping("/all")
    public List<HeroResponse> getAllHeroes() {
        return heroService.getAllHeroes();
    }

    @GetMapping("/{id}/{factType}")
    public FactResponse openFact(@PathVariable Long id, @PathVariable String factType) {
        return heroService.openFact(id, factType);
    }
}
