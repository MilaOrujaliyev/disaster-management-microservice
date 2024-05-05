package org.afetankanet.disastermanagementmicroservice.service;

import org.afetankanet.disastermanagementmicroservice.entity.Earthquake;
import org.afetankanet.disastermanagementmicroservice.repository.EarthquakeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class EarthquakeService {


    @Value("${schedule.fixedRate}")
    private long fixedRate;

    private final RestTemplate restTemplate;

    private final EarthquakeRepository earthquakeRepository;


    public EarthquakeService(EarthquakeRepository earthquakeRepository) {
        this.restTemplate = new RestTemplate();
        this.earthquakeRepository = earthquakeRepository;
    }

    @Scheduled(fixedRateString = "${schedule.fixedRate}")
    public void scheduleFetchEarthquakesAndSave() {

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime yesterday = now.minusDays(5);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String endTime = now.format(formatter);
        String startTime = yesterday.format(formatter);

        // Türkiye için varsayılan sınırlar
        double minLat = 35.000;
        double maxLat = 42.107;
        double minLon = 26.000;
        double maxLon = 45.000;

        double minMag = 0.1;
        double maxMag = 10.0;
        int minDepth = 1;
        int maxDepth = 32;

        fetchEarthquakesAndSave(
                minLat, maxLat, minLon, maxLon, minDepth, maxDepth, startTime, endTime, minMag, maxMag, "json"
        );

    }

    @Transactional
    public void fetchEarthquakesAndSave(Double minLat, Double maxLat, Double minLon, Double maxLon,
                                        Integer minDepth, Integer maxDepth, String startTime, String endTime,
                                        Double minMag, Double maxMag, String format) {
        Earthquake earthquakeDuplicate =null;

        String url = UriComponentsBuilder
                .fromHttpUrl("https://deprem.afad.gov.tr/apiv2/event/filter")
                .queryParam("start", startTime)
                .queryParam("end", endTime)
                .queryParam("minlat", minLat)
                .queryParam("maxlat", maxLat)
                .queryParam("minlon", minLon)
                .queryParam("maxlon", maxLon)
                .queryParam("mindepth", minDepth)
                .queryParam("maxdepth", maxDepth)
                .queryParam("minmag", minMag)
                .queryParam("maxmag", maxMag)
                .queryParam("format", format)
                .build()
                .toUriString();


            ResponseEntity<Earthquake[]> response = restTemplate.getForEntity(url, Earthquake[].class);

            List<Earthquake> earthquakeList = Arrays.asList(Objects.requireNonNull(response.getBody()));


            for (Earthquake earthquake : earthquakeList) {
                try {
                    earthquakeRepository.save(earthquake);
                } catch (DataIntegrityViolationException e) {
                    System.out.println("DataIntegrityViolationException: The Earthquake is already exist - EventID: " + earthquake.getEventID() + ", Date: " + earthquake.getDate());

                }
            }
    }
    public Page<Earthquake> findEarthquakesByDays(int days, int page, int size) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(days);
        Pageable sortedByDateDesc = PageRequest.of(page, size, Sort.by("date").descending());
        return earthquakeRepository.findByDateBetween(startTime, endTime, sortedByDateDesc);
    }

    public Page<Earthquake> findEarthquakesByProvince(String province, int page, int size) {
        Pageable sortedByDateDesc = PageRequest.of(page, size, Sort.by("date").descending());
        return earthquakeRepository.findByProvince(province, sortedByDateDesc);
    }

    public Page<Earthquake> findEarthquakesByMagnitude(double minMagnitude, double maxMagnitude, int page, int size) {
        Pageable sortedByDateDesc = PageRequest.of(page, size, Sort.by("date").descending());
        return earthquakeRepository.findByMagnitudeBetween(minMagnitude, maxMagnitude, sortedByDateDesc);
    }

    public List<Earthquake> findEarthquakesByMagnitudeAndDate(double minMagnitude, double maxMagnitude, LocalDateTime startDate, LocalDateTime endDate) {
        return earthquakeRepository.findByMagnitudeBetweenAndDateBetweenOrderByDateDesc(minMagnitude, maxMagnitude, startDate, endDate);
    }

    public List<Earthquake> findTop5RecentEarthquakes() {
        return earthquakeRepository.findTop5ByOrderByDateDesc();
    }

    public Earthquake findMostRecentEarthquake() {
        return earthquakeRepository.findTopByOrderByDateDesc();
    }
}
