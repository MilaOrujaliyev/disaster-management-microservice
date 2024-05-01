package org.afetankanet.disastermanagementmicroservice.repository;

import org.afetankanet.disastermanagementmicroservice.entity.Earthquake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EarthquakeRepository extends JpaRepository<Earthquake, Long> {

    List<Earthquake> findByCountry(String country);
    List<Earthquake> findByDateBetween(LocalDateTime start, LocalDateTime end);
    List<Earthquake> findByMagnitudeBetween(double minMagnitude, double maxMagnitude);
}

