package com.example.is_coursework.repositories;

import com.example.is_coursework.models.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, Long> {
}
