package com.example.is_coursework.messages;

import com.example.is_coursework.models.Character;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class PollMessagePrivateVotes {
    private Long id;

    private RoomMessage room;

    private Integer roundNumber;

    private Timestamp creationTime;

    private Boolean isOpen;

    private Character targetCharacter;

    private List<VotePrivateMessage> votes;
}
