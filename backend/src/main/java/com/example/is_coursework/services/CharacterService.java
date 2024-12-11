package com.example.is_coursework.services;

import com.example.is_coursework.dto.requests.CharacterRequest;
import com.example.is_coursework.dto.responses.CharacterResponse;
import com.example.is_coursework.dto.responses.FactResponse;
import com.example.is_coursework.models.Character;
import com.example.is_coursework.models.*;
import com.example.is_coursework.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final BodyTypeRepository bodyTypeRepository;
    private final ProfessionRepository professionRepository;
    private final TraitRepository traitRepository;
    private final PhobiaRepository phobiaRepository;
    private final HealthRepository healthRepository;
    private final BagRepository bagRepository;
    private final HobbyRepository hobbyRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public CharacterResponse createCharacter(CharacterRequest characterRequest, String username) {
        BodyType bodyType = bodyTypeRepository.findById(characterRequest.getBodyTypeId()).orElseThrow(
                () -> new IllegalArgumentException("BodyType not found"));
        Health health = healthRepository.findById(characterRequest.getHealthId()).orElseThrow(
                () -> new IllegalArgumentException("Health not found"));
        Trait trait = traitRepository.findById(characterRequest.getTraitId()).orElseThrow(
                () -> new IllegalArgumentException("Trait not found"));
        Hobby hobby = hobbyRepository.findById(characterRequest.getHobbyId()).orElseThrow(
                () -> new IllegalArgumentException("Hobby not found"));
        Profession profession = professionRepository.findById(characterRequest.getProfessionId()).orElseThrow(
                () -> new IllegalArgumentException("Profession not found"));
        Phobia phobia = phobiaRepository.findById(characterRequest.getPhobiaId()).orElseThrow(
                () -> new IllegalArgumentException("Phobia not found"));
        Equipment equipment = equipmentRepository.findById(characterRequest.getEquipmentId()).orElseThrow(
                () -> new IllegalArgumentException("Equipment not found"));
        Bag bag = bagRepository.findById(characterRequest.getBagId()).orElseThrow(
                () -> new IllegalArgumentException("Bag not found"));

        int totalLevel = bodyType.getLevel() + health.getLevel() + trait.getLevel() + hobby.getLevel() +
                profession.getLevel() + phobia.getLevel() + equipment.getLevel() + bag.getLevel();

        if (totalLevel != 0) {
            // TODO: hash check
            throw new IllegalArgumentException("Characteristics must be balanced.");
        }

        Character character = toCharacter(characterRequest);
        character.setBodyType(bodyType);
        character.setHealth(health);
        character.setTrait(trait);
        character.setHobby(hobby);
        character.setProfession(profession);
        character.setPhobia(phobia);
        character.setEquipment(equipment);
        character.setBag(bag);

        Optional<User> user = userRepository.findUserByLogin(username);
        character.setUser(user.orElseThrow());
        characterRepository.save(character);

        return toCharacterResponse(character);
    }

    public CharacterResponse getCharacterById(Long id) {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Character not found"));
        return toCharacterResponse(character);
    }

    public FactResponse openFact(Long characterId, String factType) {
        Character character = characterRepository.findById(characterId).orElseThrow(
                () -> new IllegalArgumentException("Character not found"));

        return switch (factType.toLowerCase()) {
            case "bodytype" -> FactResponse.builder().name(character.getBodyType().getName()).build();
            case "health" -> FactResponse.builder().name(character.getHealth().getName()).build();
            case "trait" -> FactResponse.builder().name(character.getTrait().getName()).build();
            case "hobby" -> FactResponse.builder().name(character.getHobby().getName()).build();
            case "profession" -> FactResponse.builder().name(character.getProfession().getName()).build();
            case "phobia" -> FactResponse.builder().name(character.getPhobia().getName()).build();
            case "equipment" -> FactResponse.builder().name(character.getEquipment().getName()).build();
            case "bag" -> FactResponse.builder().name(character.getBag().getName()).build();
            default -> throw new IllegalArgumentException("Invalid fact type");
        };
    }

    private CharacterResponse toCharacterResponse(Character character) {
        return CharacterResponse.builder()
                .id(character.getId())
                .name(character.getName())
                .notes(character.getNotes())
                .sex(character.getSex())
                .isActive(character.getIsActive())
                .user(character.getUser())
                .age(character.getAge())
                .bag(character.getBag())
                .bodyType(character.getBodyType())
                .health(character.getHealth())
                .profession(character.getProfession())
                .phobia(character.getPhobia())
                .trait(character.getTrait())
                .equipment(character.getEquipment())
                .hobby(character.getHobby())
                .build();
    }

    private Character toCharacter(CharacterRequest characterRequest) {
        return Character.builder()
                .age(characterRequest.getAge())
                .name(characterRequest.getName())
                .notes(characterRequest.getNotes())
                .isActive(characterRequest.getIsActive())
                .sex(characterRequest.getSex())
                .build();
    }
}
