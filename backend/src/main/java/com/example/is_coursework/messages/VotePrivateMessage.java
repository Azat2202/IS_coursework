package com.example.is_coursework.messages;

import com.example.is_coursework.models.Character;
import lombok.Data;

@Data
public class VotePrivateMessage {
    private Long id;

    private RoomMessage room;

    private Character character;

    private Integer roundNumber;
}
