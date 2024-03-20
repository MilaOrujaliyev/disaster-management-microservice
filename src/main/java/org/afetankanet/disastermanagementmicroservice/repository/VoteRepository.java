package org.afetankanet.disastermanagementmicroservice.repository;

import org.afetankanet.disastermanagementmicroservice.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote,Long> {

    List<Vote> findByVotedUserId(Long userId);
}
