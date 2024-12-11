package com.example.is_coursework.controllers;


import com.example.is_coursework.dto.requests.CharacterRequest;
import com.example.is_coursework.dto.responses.FactResponse;
import com.example.is_coursework.dto.responses.CharacterResponse;
import com.example.is_coursework.services.CharacterService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/characters")
public class CharacterController {
    private final CharacterService characterService;

    @PostMapping("/create")
    public CharacterResponse createCharacter(Authentication authentication, @RequestBody CharacterRequest characterRequest) {
        String username = authentication.getName();
        return characterService.createCharacter(characterRequest, username);
    }

    @GetMapping("/{id}")
    public CharacterResponse getCharacterById(@PathVariable Long id) {
        return characterService.getCharacterById(id);
    }

    @GetMapping("/all")
    public List<CharacterResponse> getAllCharacters() {
        return characterService.getAllCharacters();
    }

    @GetMapping("/{id}/{factType}")
    public FactResponse openFact(@PathVariable Long id, @PathVariable String factType) {
        return characterService.openFact(id, factType);
    }
}
