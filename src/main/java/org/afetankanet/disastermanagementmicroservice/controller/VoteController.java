package org.afetankanet.disastermanagementmicroservice.controller;

import org.afetankanet.disastermanagementmicroservice.entity.Vote;
import org.afetankanet.disastermanagementmicroservice.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/votes")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @PostMapping
    public ResponseEntity<Vote> castVote(@RequestParam Long votedUserId, @RequestParam Long helpBoxId, @RequestParam int score) {
        Vote vote = voteService.castVote(votedUserId, helpBoxId, score);
        return ResponseEntity.ok(vote);
    }

}