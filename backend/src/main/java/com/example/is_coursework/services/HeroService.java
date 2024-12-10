package com.example.is_coursework.services;

import com.example.is_coursework.dto.requests.HeroRequest;
import com.example.is_coursework.dto.responses.FactResponse;
import com.example.is_coursework.dto.responses.HeroResponse;
import com.example.is_coursework.models.*;
import com.example.is_coursework.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeroService {
    private final HeroRepository heroRepository;
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
    public HeroResponse createHero(HeroRequest heroRequest, String username) {
        BodyType bodyType = bodyTypeRepository.findById(heroRequest.getBodyTypeId()).orElseThrow(
                () -> new IllegalArgumentException("BodyType not found"));
        Health health = healthRepository.findById(heroRequest.getHealthId()).orElseThrow(
                () -> new IllegalArgumentException("Health not found"));
        Trait trait = traitRepository.findById(heroRequest.getTraitId()).orElseThrow(
                () -> new IllegalArgumentException("Trait not found"));
        Hobby hobby = hobbyRepository.findById(heroRequest.getHobbyId()).orElseThrow(
                () -> new IllegalArgumentException("Hobby not found"));
        Profession profession = professionRepository.findById(heroRequest.getProfessionId()).orElseThrow(
                () -> new IllegalArgumentException("Profession not found"));
        Phobia phobia = phobiaRepository.findById(heroRequest.getPhobiaId()).orElseThrow(
                () -> new IllegalArgumentException("Phobia not found"));
        Equipment equipment = equipmentRepository.findById(heroRequest.getEquipmentId()).orElseThrow(
                () -> new IllegalArgumentException("Equipment not found"));
        Bag bag = bagRepository.findById(heroRequest.getBagId()).orElseThrow(
                () -> new IllegalArgumentException("Bag not found"));

        int totalLevel = bodyType.getLevel() + health.getLevel() + trait.getLevel() + hobby.getLevel() +
                profession.getLevel() + phobia.getLevel() + equipment.getLevel() + bag.getLevel();

        if (totalLevel != 0) {
            // TODO: hash check
            throw new IllegalArgumentException("Characteristics must be balanced.");
        }

        Hero hero = toHero(heroRequest);
        hero.setBodyType(bodyType);
        hero.setHealth(health);
        hero.setTrait(trait);
        hero.setHobby(hobby);
        hero.setProfession(profession);
        hero.setPhobia(phobia);
        hero.setEquipment(equipment);
        hero.setBag(bag);

        User user = userRepository.findByLogin(username);
        hero.setUser(user);
        heroRepository.save(hero);

        return toHeroResponse(hero);
    }

    public HeroResponse getHeroById(Long id) {
        Hero hero = heroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hero not found"));
        return toHeroResponse(hero);
    }

    public List<HeroResponse> getAllHeroes() {
        List<Hero> heroes = heroRepository.findAll();
        return heroes
                .stream()
                .map(this::toHeroResponse)
                .toList();
    }

    public FactResponse openFact(Long heroId, String factType) {
        Hero hero = heroRepository.findById(heroId).orElseThrow(
                () -> new IllegalArgumentException("Hero not found"));

        return switch (factType.toLowerCase()) {
            case "bodytype" -> FactResponse.builder().name(hero.getBodyType().getName()).build();
            case "health" -> FactResponse.builder().name(hero.getHealth().getName()).build();
            case "trait" -> FactResponse.builder().name(hero.getTrait().getName()).build();
            case "hobby" -> FactResponse.builder().name(hero.getHobby().getName()).build();
            case "profession" -> FactResponse.builder().name(hero.getProfession().getName()).build();
            case "phobia" -> FactResponse.builder().name(hero.getPhobia().getName()).build();
            case "equipment" -> FactResponse.builder().name(hero.getEquipment().getName()).build();
            case "bag" -> FactResponse.builder().name(hero.getBag().getName()).build();
            default -> throw new IllegalArgumentException("Invalid fact type");
        };
    }

    private HeroResponse toHeroResponse(Hero hero) {
        return HeroResponse.builder()
                .id(hero.getId())
                .name(hero.getName())
                .notes(hero.getNotes())
                .sex(hero.getSex())
                .isActive(hero.getIsActive())
                .user(hero.getUser())
                .age(hero.getAge())
                .bag(hero.getBag())
                .bodyType(hero.getBodyType())
                .health(hero.getHealth())
                .profession(hero.getProfession())
                .phobia(hero.getPhobia())
                .trait(hero.getTrait())
                .equipment(hero.getEquipment())
                .hobby(hero.getHobby())
                .build();
    }

    private Hero toHero(HeroRequest heroRequest) {
        return Hero.builder()
                .age(heroRequest.getAge())
                .name(heroRequest.getName())
                .notes(heroRequest.getNotes())
                .isActive(heroRequest.getIsActive())
                .sex(heroRequest.getSex())
                .build();
    }
}
