package com.example.is_coursework.repositories;

import com.example.is_coursework.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
