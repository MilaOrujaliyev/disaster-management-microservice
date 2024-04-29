package org.afetankanet.disastermanagementmicroservice.repository;

import org.afetankanet.disastermanagementmicroservice.entity.DisasterNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DisasterNewsRepository extends JpaRepository<DisasterNews,Long> {
  //  List<DisasterNews> findByQueryCriteria(String queryCriteria);

    Page<DisasterNews> findByQueryCriteria(String queryCriteria, Pageable pageable);
}
