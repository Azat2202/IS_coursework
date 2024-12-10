package com.example.is_coursework.models;

import com.example.is_coursework.models.enums.SexType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private SexType sex;

    @Column(nullable = false)
    private String notes;

    @Column(nullable = false)
    private Boolean isActive;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "body_type_id", nullable = false)
    private BodyType bodyType;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "health_id", nullable = false)
    private Health health;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trait_id", nullable = false)
    private Trait trait;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hobby_id", nullable = false)
    private Hobby hobby;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profession_id", nullable = false)
    private Profession profession;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phobia_id", nullable = false)
    private Phobia phobia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bag_id", nullable = false)
    private Bag bag;
}
