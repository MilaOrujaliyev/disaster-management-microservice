package org.afetankanet.disastermanagementmicroservice.repository;

import org.afetankanet.disastermanagementmicroservice.entity.CommentHelpBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentHelpBoxRepository extends JpaRepository<CommentHelpBox,Long> {
}
