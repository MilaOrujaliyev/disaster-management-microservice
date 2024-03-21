package org.afetankanet.disastermanagementmicroservice.repository;

import org.afetankanet.disastermanagementmicroservice.entity.HelpBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelpBoxRepository extends JpaRepository<HelpBox,Long> {
    List<HelpBox> findByUserId(Long userId);
    boolean existsByUserIdAndActive(Long userId, Boolean active);
}
