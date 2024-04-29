package org.afetankanet.disastermanagementmicroservice.controller;

import jakarta.validation.Valid;
import org.afetankanet.disastermanagementmicroservice.entity.Vote;
import org.afetankanet.disastermanagementmicroservice.service.VoteService;
import org.afetankanet.disastermanagementmicroservice.util.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @GetMapping("/getScoreByHelpBoxId/{helpBoxId}")
    public ResponseEntity<?> getScoreByHelpBoxId(@PathVariable Long helpBoxId) {
        try {
            int trustScore = voteService.getScoreByHelpBoxId(helpBoxId);
            return ResponseEntity.ok(Map.of("trustScore", trustScore));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("HelpBox'a ait güvenilirlik puanı alınırken bir hata oluştu."));
        }
    }
}
