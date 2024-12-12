package com.example.is_coursework.controllers;

import com.example.is_coursework.dto.requests.CreateCharacterRequest;
import com.example.is_coursework.dto.requests.GenerateFactRequest;
import com.example.is_coursework.dto.requests.OpenedFactsRequest;
import com.example.is_coursework.dto.responses.FactResponse;
import com.example.is_coursework.dto.responses.CharacterResponse;
import com.example.is_coursework.dto.responses.GenerateFactResponse;
import com.example.is_coursework.dto.responses.OpenFactsResponse;
import com.example.is_coursework.interfaces.CurrentUser;
import com.example.is_coursework.models.User;
import com.example.is_coursework.models.enums.FactType;
import com.example.is_coursework.services.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/characters")
public class CharacterController {
    private final CharacterService characterService;

    @PostMapping("/create")
    public CharacterResponse createCharacter(@CurrentUser User user, @RequestBody CreateCharacterRequest createCharacterRequest) {
        return characterService.createCharacter(createCharacterRequest, user);
    }

    @GetMapping("/{id}")
    public CharacterResponse getCharacterById(@PathVariable Long id) {
        return characterService.getCharacterById(id);
    }

    @PutMapping("/open_fact/{character_id}/{factType}")
    public FactResponse openFact(@PathVariable Long character_id, @PathVariable FactType factType) {
        return characterService.openFact(character_id, factType);
    }

    @GetMapping("/get_opened")
    public OpenFactsResponse getOpenedFacts(OpenedFactsRequest openedFactsRequest) {
        return characterService.getOpenFacts(openedFactsRequest);
    }

    @PutMapping("/generate_facts")
    public GenerateFactResponse generateFact(@CurrentUser User user, GenerateFactRequest generateFactRequest) {
        return characterService.generateFact(user, generateFactRequest);
    }
}
