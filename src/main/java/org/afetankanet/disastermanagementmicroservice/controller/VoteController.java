package org.afetankanet.disastermanagementmicroservice.controller;

import jakarta.validation.Valid;
import org.afetankanet.disastermanagementmicroservice.entity.Vote;
import org.afetankanet.disastermanagementmicroservice.service.VoteService;
import org.afetankanet.disastermanagementmicroservice.util.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/votes")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @PostMapping
    public ResponseEntity<?> castVote(@Valid @RequestBody Vote voteRequest) {
        try{
           Vote vote= voteService.castVote(voteRequest);
            return ResponseEntity.ok(vote);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }

    }

}