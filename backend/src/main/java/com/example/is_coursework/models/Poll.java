package com.example.is_coursework.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Entity(name = "polls")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roomId", nullable = false)
    private Room room;

    @Column(nullable = false)
    private Integer roundNumber;

    @Column(nullable = false)
    private Timestamp creationTime;

    @Column(nullable = false)
    private Boolean isOpen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "target_to_kick", nullable = false)
    private Character targetCharacter;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_id")
    private List<Vote> votes;
}
