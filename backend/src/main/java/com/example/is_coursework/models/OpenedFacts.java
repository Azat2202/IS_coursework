package com.example.is_coursework.models;

import com.example.is_coursework.models.enums.SexType;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "open_facts")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class OpenedFacts {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long character_id;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "body_type_id")
    @Column(nullable = false)
    private String bodyType;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "health_id")
    @Column(nullable = false)
    private String health;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "trait_id")
    @Column(nullable = false)
    private String trait;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "hobby_id")
    @Column(nullable = false)
    private String hobby;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "profession_id")
    @Column(nullable = false)
    private String profession;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "phobia_id")
    @Column(nullable = false)
    private String phobia;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "equipment_id")
    @Column(nullable = false)
    private String equipment;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "bag_id")
    @Column(nullable = false)
    private String bag;
}
