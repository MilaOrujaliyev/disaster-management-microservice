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

        User votedUser = userRepository.findById(vote.getVotedUser().getId())
                .orElseThrow(() -> new RuntimeException("Oy verilen kullanıcı bulunamadı"));
        vote.setVotedUser(votedUser);

        HelpBox helpBox = helpBoxRepository.findById(vote.getHelpBox().getId())
                .orElseThrow(() -> new RuntimeException("Yardım kutusu bulunamadı"));
        vote.setHelpBox(helpBox);

        voteRepository.save(vote);

        userService.updateTrustScore(vote.getVotedUser().getId());

        return vote;
    }
}
