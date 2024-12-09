package com.example.is_coursework.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hero_id", nullable = false)
    private Hero hero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "target_hero_id", nullable = false)
    private Hero targetHero;

    @Column(nullable = false)
    private Integer roundNumber;
}
