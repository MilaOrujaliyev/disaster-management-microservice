package org.afetankanet.disastermanagementmicroservice.repository;

import org.afetankanet.disastermanagementmicroservice.entity.Earthquake;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EarthquakeRepository extends JpaRepository<Earthquake, Long> {

    List<Earthquake> findByCountry(String country);
    List<Earthquake> findByDateBetween(LocalDateTime start, LocalDateTime end);
    Page<Earthquake> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<Earthquake> findByProvince(String province, Pageable pageable);
    Page<Earthquake> findByMagnitudeBetween(double minMagnitude, double maxMagnitude, Pageable pageable);
    List<Earthquake> findByMagnitudeBetweenAndDateBetweenOrderByDateDesc(double minMagnitude, double maxMagnitude, LocalDateTime startDate, LocalDateTime endDate);
    List<Earthquake> findTop5ByOrderByDateDesc();
    Earthquake findTopByOrderByDateDesc();
    List<Earthquake> findByMagnitudeBetween(double minMagnitude, double maxMagnitude);
}

