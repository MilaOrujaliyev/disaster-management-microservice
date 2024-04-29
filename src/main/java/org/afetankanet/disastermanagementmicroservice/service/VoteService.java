package org.afetankanet.disastermanagementmicroservice.service;

import org.afetankanet.disastermanagementmicroservice.entity.HelpBox;
import org.afetankanet.disastermanagementmicroservice.entity.User;
import org.afetankanet.disastermanagementmicroservice.entity.Vote;
import org.afetankanet.disastermanagementmicroservice.repository.HelpBoxRepository;
import org.afetankanet.disastermanagementmicroservice.repository.UserRepository;
import org.afetankanet.disastermanagementmicroservice.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HelpBoxRepository helpBoxRepository;

    public Vote castVote(Vote vote) {

        HelpBox helpBox = helpBoxRepository.findById(vote.getHelpBox().getId())
                .orElseThrow(() -> new RuntimeException("Yardım kutusu bulunamadı"));
        vote.setHelpBox(helpBox);

        User votedUser = userRepository.findById(helpBox.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Oy verilen kullanıcı bulunamadı"));
        vote.setVotedUser(votedUser);


        voteRepository.save(vote);

        userService.updateTrustScore(helpBox.getUser().getId());

        return vote;
    }

    public int getScoreByHelpBoxId(Long helpBoxId){
        HelpBox helpBox = helpBoxRepository.findById(helpBoxId)
                .orElseThrow(() -> new RuntimeException("HelpBox bulunamadı"));
        return helpBox.getUser().getTrustScore();
    }
}
