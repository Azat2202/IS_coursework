package com.example.is_coursework.repositories;

import com.example.is_coursework.models.Health;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRepository extends JpaRepository<Health, Long> {
}

