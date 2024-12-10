package com.example.is_coursework.controllers;

import com.example.is_coursework.interfaces.CurrentUser;
import com.example.is_coursework.messages.RoomMessage;
import com.example.is_coursework.models.User;
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

    @PostMapping("/createRoom")
    public RoomMessage createRoom(@CurrentUser User user) {
        return roomService.createRoom(user);
    }

    @GetMapping("/{roomId}")
    public RoomMessage getRoom(@CurrentUser User user, @PathVariable Long roomId) {
        return roomService.getRoom(user, roomId);
    }

    @PostMapping("/{joinCode}/join")
    public RoomMessage joinRoom(@CurrentUser User user, @PathVariable String joinCode) {
        return roomService.joinRoom(user, joinCode);
    }
}
