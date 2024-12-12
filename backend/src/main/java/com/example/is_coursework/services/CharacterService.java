package com.example.is_coursework.services;

import com.example.is_coursework.dto.requests.GenerateFactRequest;
import com.example.is_coursework.dto.requests.OpenedFactsRequest;
import com.example.is_coursework.dto.responses.CharacterResponse;
import com.example.is_coursework.dto.responses.FactResponse;
import com.example.is_coursework.dto.responses.GenerateFactResponse;
import com.example.is_coursework.dto.responses.OpenFactsResponse;
import com.example.is_coursework.models.Character;
import com.example.is_coursework.models.*;
import com.example.is_coursework.models.enums.FactType;
import com.example.is_coursework.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private final OpenFactsRepository openFactsRepository;

    @Transactional
    public CharacterResponse createCharacter(CreateCharacterRequest characterRequest, User user) {
        if (user.getLogin().hashCode() + characterRequest.getRoomId().hashCode() != characterRequest.getCheckHash()) {
//            int res = user.getLogin().hashCode() + characterRequest.getRoomId().hashCode();
            throw new IllegalArgumentException("Wrong user or room");// got: " + res + "want" + characterRequest.getCheckHash());
        }
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

        character.setUser(user);
        characterRepository.save(character);
        OpenedFacts openedFacts = OpenedFacts.builder().character_id(character.getId()).build();
        openFactsRepository.save(openedFacts);

        return toCharacterResponse(character);
    }

    public CharacterResponse getCharacterById(Long id) {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Character not found"));
        return toCharacterResponse(character);
    }

    public FactResponse openFact(Long characterId, FactType factType) {
        Character character = characterRepository.findById(characterId).orElseThrow(
                () -> new IllegalArgumentException("Character not found"));
        OpenedFacts openedFacts = openFactsRepository.findById(characterId).orElseThrow(
                () -> new IllegalArgumentException("by character not found"));

        FactResponse response;
        switch (factType) {
            case BODY_TYPE:
                response = FactResponse.builder().name(character.getBodyType().getName()).build();
                openedFacts.setBodyType(character.getBodyType().getName());
                break;
            case HEALTH:
                response = FactResponse.builder().name(character.getHealth().getName()).build();
                openedFacts.setHealth(character.getHealth().getName());
                break;
            case TRAIT:
                response = FactResponse.builder().name(character.getTrait().getName()).build();
                openedFacts.setTrait(character.getTrait().getName());
                break;
            case HOBBY:
                response = FactResponse.builder().name(character.getHobby().getName()).build();
                openedFacts.setHobby(character.getHobby().getName());
                break;
            case PROFESSION:
                response = FactResponse.builder().name(character.getProfession().getName()).build();
                openedFacts.setProfession(character.getProfession().getName());
                break;
            case PHOBIA:
                response = FactResponse.builder().name(character.getPhobia().getName()).build();
                openedFacts.setPhobia(character.getPhobia().getName());
                break;
            case EQUIPMENT:
                response = FactResponse.builder().name(character.getEquipment().getName()).build();
                openedFacts.setEquipment(character.getEquipment().getName());
                break;
            case BAG:
                response = FactResponse.builder().name(character.getBag().getName()).build();
                openedFacts.setBag(character.getBag().getName());
                break;
            default:
                throw new IllegalArgumentException("Invalid fact type");
        }
        openFactsRepository.save(openedFacts);
        return response;
    }

    public OpenFactsResponse getOpenFacts(OpenedFactsRequest openedFactsRequest) {
        Long characterId = openedFactsRequest.getCharacterId();

        OpenedFacts openedFacts = openFactsRepository.findById(characterId).orElseThrow();
        OpenFactsResponse response = OpenFactsResponse.builder().build();
        if (openedFacts.getBag() != null) {
            response.setBag(openFact(characterId, FactType.BAG).getName());
        }
        if (openedFacts.getBodyType() != null) {
            response.setBodyType(openFact(characterId, FactType.BODY_TYPE).getName());
        }
        if (openedFacts.getHealth() != null) {
            response.setHealth(openFact(characterId, FactType.HEALTH).getName());
        }
        if (openedFacts.getTrait() != null) {
            response.setTrait(openFact(characterId, FactType.TRAIT).getName());
        }
        if (openedFacts.getHobby() != null) {
            response.setHobby(openFact(characterId, FactType.HOBBY).getName());
        }
        if (openedFacts.getProfession() != null) {
            response.setProfession(openFact(characterId, FactType.PROFESSION).getName());
        }
        if (openedFacts.getPhobia() != null) {
            response.setPhobia(openFact(characterId, FactType.PHOBIA).getName());
        }
        if (openedFacts.getEquipment() != null) {
            response.setEquipment(openFact(characterId, FactType.EQUIPMENT).getName());
        }
        return response;
    }

    public GenerateFactResponse generateFact(User user, GenerateFactRequest generateFactRequest) {

        int checkHash = user.getLogin().hashCode() + generateFactRequest.getRoomId().hashCode();
        Random random = new Random(checkHash);

        List<Bag> bags = new ArrayList<>();
        List<Bag> bags1 = bagRepository.findByLevel(1);
        bags.add(bags1.get(random.nextInt(bags1.size())));
        List<Bag> bags0 = bagRepository.findByLevel(0);
        bags.add(bags0.get(random.nextInt(bags0.size())));
        List<Bag> bags11 = bagRepository.findByLevel(-1);
        bags.add(bags11.get(random.nextInt(bags11.size())));

        List<BodyType> bodyTypes = new ArrayList<>();
        List<BodyType> bodyTypes1 = bodyTypeRepository.findByLevel(1);
        bodyTypes.add(bodyTypes1.get(random.nextInt(bodyTypes1.size())));
        List<BodyType> bodyTypes0 = bodyTypeRepository.findByLevel(0);
        bodyTypes.add(bodyTypes0.get(random.nextInt(bodyTypes0.size())));
        List<BodyType> bodyTypes11 = bodyTypeRepository.findByLevel(-1);
        bodyTypes.add(bodyTypes11.get(random.nextInt(bodyTypes11.size())));

        List<Equipment> equipments = new ArrayList<>();
        List<Equipment> equipments1 = equipmentRepository.findByLevel(1);
        equipments.add(equipments1.get(random.nextInt(equipments1.size())));
        List<Equipment> equipments0 = equipmentRepository.findByLevel(0);
        equipments.add(equipments0.get(random.nextInt(equipments0.size())));
        List<Equipment> equipments11 = equipmentRepository.findByLevel(-1);
        equipments.add(equipments11.get(random.nextInt(equipments11.size())));

        List<Health> healths = new ArrayList<>();
        List<Health> healths1 = healthRepository.findByLevel(1);
        healths.add(healths1.get(random.nextInt(healths1.size())));
        List<Health> healths0 = healthRepository.findByLevel(0);
        healths.add(healths0.get(random.nextInt(healths0.size())));
        List<Health> healths11 = healthRepository.findByLevel(-1);
        healths.add(healths11.get(random.nextInt(healths11.size())));

        List<Hobby> hobbies = new ArrayList<>();
        List<Hobby> hobbies1 = hobbyRepository.findByLevel(1);
        hobbies.add(hobbies1.get(random.nextInt(hobbies1.size())));
        List<Hobby> hobbies0 = hobbyRepository.findByLevel(0);
        hobbies.add(hobbies0.get(random.nextInt(hobbies0.size())));
        List<Hobby> hobbies11 = hobbyRepository.findByLevel(-1);
        hobbies.add(hobbies11.get(random.nextInt(hobbies11.size())));

        List<Phobia> phobias = new ArrayList<>();
        List<Phobia> phobias1 = phobiaRepository.findByLevel(1);
        phobias.add(phobias1.get(random.nextInt(phobias1.size())));
        List<Phobia> phobias0 = phobiaRepository.findByLevel(0);
        phobias.add(phobias0.get(random.nextInt(phobias0.size())));
        List<Phobia> phobias11 = phobiaRepository.findByLevel(-1);
        phobias.add(phobias11.get(random.nextInt(phobias11.size())));

        List<Profession> professions = new ArrayList<>();
        List<Profession> professions1 = professionRepository.findByLevel(1);
        professions.add(professions1.get(random.nextInt(professions1.size())));
        List<Profession> professions0 = professionRepository.findByLevel(0);
        professions.add(professions0.get(random.nextInt(professions0.size())));
        List<Profession> professions11 = professionRepository.findByLevel(-1);
        professions.add(professions11.get(random.nextInt(professions11.size())));

        List<Trait> traits = new ArrayList<>();
        List<Trait> traits1 = traitRepository.findByLevel(1);
        traits.add(traits1.get(random.nextInt(traits1.size())));
        List<Trait> traits0 = traitRepository.findByLevel(0);
        traits.add(traits0.get(random.nextInt(traits0.size())));
        List<Trait> traits11 = traitRepository.findByLevel(-1);
        traits.add(traits11.get(random.nextInt(traits11.size())));
        return GenerateFactResponse.builder()
                .bags(bags)
                .bodyTypes(bodyTypes)
                .equipments(equipments)
                .healths(healths)
                .hobbies(hobbies)
                .phobiases(phobias)
                .professions(professions)
                .traits(traits)
                .checkHash(checkHash)
                .build();
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

    private Character toCharacter(CreateCharacterRequest characterRequest) {
        return Character.builder()
                .age(characterRequest.getAge())
                .name(characterRequest.getName())
                .notes(characterRequest.getNotes())
                .isActive(characterRequest.getIsActive())
                .sex(characterRequest.getSex())
                .build();
    }
}
