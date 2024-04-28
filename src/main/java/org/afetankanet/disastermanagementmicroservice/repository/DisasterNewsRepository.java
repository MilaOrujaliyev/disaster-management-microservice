package org.afetankanet.disastermanagementmicroservice.repository;

import org.afetankanet.disastermanagementmicroservice.entity.DisasterNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DisasterNewsRepository extends JpaRepository<DisasterNews,Long> {

}
