package org.afetankanet.disastermanagementmicroservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.afetankanet.disastermanagementmicroservice.entity.CommentHelpBox;
import org.afetankanet.disastermanagementmicroservice.repository.CommentHelpBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentHelpBoxService {

    @Autowired
    private CommentHelpBoxRepository commentHelpBoxRepository;

    public CommentHelpBox addComment(CommentHelpBox commentHelpBox){
        return commentHelpBoxRepository.save(commentHelpBox);
    }

    public void deleteComment(long commentId){
        Optional<CommentHelpBox> commentHelpBoxOptional=commentHelpBoxRepository.findById(commentId);

        if(!commentHelpBoxOptional.isPresent()){
            throw new EntityNotFoundException("ilgili yorum bulunamadı");
        }

        commentHelpBoxRepository.delete(commentHelpBoxOptional.get());
    }
}
