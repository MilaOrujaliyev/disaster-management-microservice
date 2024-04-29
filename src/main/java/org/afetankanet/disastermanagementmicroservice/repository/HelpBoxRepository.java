package org.afetankanet.disastermanagementmicroservice.repository;

import org.afetankanet.disastermanagementmicroservice.entity.HelpBox;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HelpBoxRepository extends JpaRepository<HelpBox,Long> {
    List<HelpBox> findByUserId(Long userId, Sort sort);

    boolean existsByUserIdAndActive(Long userId, Boolean active);

    Optional<HelpBox> findById(Long id);

}
