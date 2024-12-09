package com.example.is_coursework.messages;

import lombok.Data;

import java.util.List;

@Data
public class RoomMessage {
    private Long id;

    private BunkerMessage bunker;

    private CataclysmMessage cataclysm;

    private String joinCode;

    private List<CharacterPrivateMessage> characters;
}