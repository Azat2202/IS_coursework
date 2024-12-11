package com.example.is_coursework.controllers;
//
//import com.example.is_coursework.models.Vote;
//import com.example.is_coursework.services.VoteService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/votes")
//public class VoteController {
//
//    @Autowired
//    private VoteService voteService;
//
//    @PostMapping
//    public ResponseEntity<Vote> castVote(@RequestParam Long roomId,
//                                         @RequestParam Long characterId,
//                                         @RequestParam Long targetCharacterId,
//                                         @RequestParam int roundNumber) {
//        Vote vote = voteService.castVote(roomId, characterId, targetCharacterId, roundNumber);
//        return ResponseEntity.ok(vote);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Vote>> getVotesByRoomAndRound(@RequestParam Long roomId,
//                                                             @RequestParam int roundNumber) {
//        List<Vote> votes = voteService.getVotesByRoomAndRound(roomId, roundNumber);
//        return ResponseEntity.ok(votes);
//    }
//}
//
