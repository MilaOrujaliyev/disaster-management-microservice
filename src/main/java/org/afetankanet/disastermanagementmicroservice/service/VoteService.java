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

    public Vote castVote(Long votedUserId, Long helpBoxId, int score) {

        Vote vote = new Vote();

        User votedUser = userRepository.findById(votedUserId)
                .orElseThrow(() -> new RuntimeException("Oy verilen kullanıcı bulunamadı"));
        vote.setVotedUser(votedUser);

        HelpBox helpBox = helpBoxRepository.findById(helpBoxId)
                .orElseThrow(() -> new RuntimeException("Yardım kutusu bulunamadı"));
        vote.setHelpBox(helpBox);

        vote.setScore(score);

        voteRepository.save(vote);

        userService.updateTrustScore(votedUserId);

        return vote;
    }
}
