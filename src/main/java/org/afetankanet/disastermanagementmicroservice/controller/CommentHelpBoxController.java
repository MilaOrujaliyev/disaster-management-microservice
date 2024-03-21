package org.afetankanet.disastermanagementmicroservice.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.afetankanet.disastermanagementmicroservice.entity.CommentHelpBox;
import org.afetankanet.disastermanagementmicroservice.service.CommentHelpBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comments")
public class CommentHelpBoxController {

    @Autowired
    private CommentHelpBoxService commentHelpBoxService;

    @PostMapping("add")
    public ResponseEntity<CommentHelpBox> addComment(@Valid @RequestBody CommentHelpBox commentHelpBox){
        return ResponseEntity.ok(commentHelpBoxService.addComment(commentHelpBox));
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
        try{
            commentHelpBoxService.deleteComment(commentId);
            return ResponseEntity.ok().build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
