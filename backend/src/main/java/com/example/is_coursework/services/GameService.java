package com.example.is_coursework.services;

import com.example.is_coursework.messages.PollMessagePrivateVotes;
import com.example.is_coursework.messages.RoomMessage;
import com.example.is_coursework.models.Character;
import com.example.is_coursework.models.*;
import com.example.is_coursework.repositories.PollRepository;
import com.example.is_coursework.repositories.RoomRepository;
import com.example.is_coursework.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.webjars.NotFoundException;

import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class GameService {
    @Value("${game.settings.min_user_to_start}")
    private int minUserToStart;

    private final VoteRepository voteRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final PollRepository pollRepository;

    public RoomMessage startGame(User user, Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Room not found"));
        if (!room.getAdmin().equals(user)) {
            throw new AccessDeniedException("You are not admin of this room");
        }
        if (room.getCharacters().size() < minUserToStart) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Not enough users to start game");
        }
        if (room.getCharacters().stream().anyMatch(Predicate.not(Character::validateChoosable))) {
            throw new HttpClientErrorException(HttpStatus.PRECONDITION_FAILED, "Some characters didnt choose all fields");
        }
        room.setIsStarted(true);
        roomRepository.save(room);

        return modelMapper.map(room, RoomMessage.class);
    }

    public PollMessagePrivateVotes createPoll(User user, Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Room not found"));
        if (!room.getAdmin().equals(user)) {
            throw new AccessDeniedException("You are not admin of this room");
        }
        if (!room.getIsStarted()) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Game is not started");
        }
        if (pollRepository.findFirstByIsOpenIsTrueAndRoom(room).isPresent()) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Poll is already opened");
        }
        Poll prevPoll = pollRepository.findFirstByRoomOrderByCreationTime(room).orElse(null);
        int roundNumber = prevPoll == null ? 1 : prevPoll.getRoundNumber() + 1;
        Poll poll = Poll.builder()
                .room(room)
                .roundNumber(roundNumber)
                .isOpen(true)
                .targetCharacter(null)
                .build();
        pollRepository.save(poll);

        return modelMapper.map(poll, PollMessagePrivateVotes.class);
    }

    public PollMessagePrivateVotes getOpenPoll(User user, Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Room not found"));
        if (!room.getAdmin().equals(user) || room.getCharacters().stream().map(Character::getUser).noneMatch(user::equals)) {
            throw new AccessDeniedException("You are participant of the room");
        }
        Poll poll = pollRepository.findFirstByIsOpenIsTrueAndRoom(room).orElseThrow(() -> new NotFoundException("There is no open poll"));
        return modelMapper.map(poll, PollMessagePrivateVotes.class);
    }

    public PollMessagePrivateVotes vote(User user, Long roomId, Long targetUserId) {
        // todo remove vote against yourself
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Room not found"));
        if (!room.getAdmin().equals(user) || room.getCharacters().stream().map(Character::getUser).noneMatch(user::equals)) {
            throw new AccessDeniedException("You are participant of the room");
        }
        Poll poll = pollRepository.findFirstByIsOpenIsTrueAndRoom(room).orElseThrow(() -> new NotFoundException("There is no open poll"));
        Character targetCharacter = room.getCharacters().stream()
                .filter(character -> character.getUser().getId().equals(targetUserId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Target user not found"));
        Character userCharacter = room.getCharacters().stream()
                .filter(character -> character.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not found in characters of room"));


        Vote vote = poll.getVotes().stream()
                .filter(v -> v.getCharacter().getUser().equals(user))
                .findFirst()
                .orElse(Vote.builder()
                        .character(userCharacter)
                        .poll(poll)
                        .room(room)
                        .build());
        vote.setTargetCharacter(targetCharacter);
        voteRepository.save(vote);
        // todo: idk if poll will bee updated automatically
        return modelMapper.map(poll, PollMessagePrivateVotes.class);
    }
}
