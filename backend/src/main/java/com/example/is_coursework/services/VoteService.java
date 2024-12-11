package com.example.is_coursework.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class VoteService {
//
//    @Autowired
//    private VoteRepository voteRepository;
//
//    @Autowired
//    private CharacterRepository characterRepository;
//
//    @Autowired
//    private RoomRepository roomRepository;
//
//    @Transactional
//    public Vote castVote(Long roomId, Long characterId, Long targetCharacterId, int roundNumber) {
//        Character character = characterRepository.findById(characterId)
//                .orElseThrow(() -> new IllegalArgumentException("Character not found"));
//
//        Character targetCharacter = characterRepository.findById(targetCharacterId)
//                .orElseThrow(() -> new IllegalArgumentException("Target character not found"));
//
//        Room room = roomRepository.findById(roomId)
//                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
//
//        if (characterId.equals(targetCharacterId)) {
//            throw new IllegalArgumentException("Cannot vote for yourself");
//        }
//
//        Vote vote = new Vote();
//        vote.setRoom(room);
//        vote.setCharacter(character);
//        vote.setTargetCharacter(targetCharacter);
//        vote.setRoundNumber(roundNumber);
//
//        return voteRepository.save(vote);
//    }
//
//    public List<Vote> getVotesByRoomAndRound(Long roomId, int roundNumber) {
//        return voteRepository.findByRoomIdAndRoundNumber(roomId, roundNumber);
//    }
//}
