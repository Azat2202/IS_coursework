package com.example.is_coursework.messages;

import com.example.is_coursework.models.Character;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VoteMessage {
    private Long id;
    private Character targetCharacter;
}
