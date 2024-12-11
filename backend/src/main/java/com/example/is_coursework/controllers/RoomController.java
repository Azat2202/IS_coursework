package com.example.is_coursework.controllers;

import com.example.is_coursework.interfaces.CurrentUser;
import com.example.is_coursework.messages.PollMessagePrivateVotes;
import com.example.is_coursework.messages.RoomMessage;
import com.example.is_coursework.models.User;
import com.example.is_coursework.services.GameService;
import com.example.is_coursework.services.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
@Tag(name = "Room Controller", description = "Контроллер для создания и входа в комнату")
public class RoomController {
    private final RoomService roomService;
    private final GameService gameService;

    @PostMapping("/createRoom")
    public RoomMessage createRoom(@CurrentUser User user) {
        return roomService.createRoom(user);
    }

    @GetMapping("/{roomId}")
    public RoomMessage getRoomState(@CurrentUser User user, @PathVariable Long roomId) {
        return roomService.getRoom(user, roomId);
    }

    @PostMapping("/join/{joinCode}")
    public RoomMessage joinRoom(@CurrentUser User user, @PathVariable String joinCode) {
        return roomService.joinRoom(user, joinCode);
    }

    @PostMapping("/{roomId}/start")
    public RoomMessage startGame(@CurrentUser User user, @PathVariable Long roomId) {
        return gameService.startGame(user, roomId);
    }

    @PostMapping("/{roomId}/pool")
    public PollMessagePrivateVotes createPool(@CurrentUser User user, @PathVariable Long roomId) {
        return gameService.createPoll(user, roomId);
    }

    @PostMapping("/{roomId}/vote")
    public PollMessagePrivateVotes vote(@CurrentUser User user, @PathVariable Long roomId, @RequestParam Long characterId) {
        return gameService.vote(user, roomId, characterId);
    }

    @GetMapping("/{roomId}/open_poll")
    public PollMessagePrivateVotes getVote(@CurrentUser User user, @PathVariable Long roomId) {
        return gameService.getOpenPoll(user, roomId);
    }
}
