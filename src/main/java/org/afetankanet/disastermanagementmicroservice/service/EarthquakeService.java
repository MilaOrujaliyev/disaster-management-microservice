package org.afetankanet.disastermanagementmicroservice.service;

import org.afetankanet.disastermanagementmicroservice.entity.Earthquake;
import org.afetankanet.disastermanagementmicroservice.repository.EarthquakeRepository;
import org.hibernate.exception.ConstraintViolationException;
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

        double minMag = 1.0;
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

        try{

            String baseUrl = "https://deprem.afad.gov.tr/apiv2/event/filter";
            String queryParams = String.format("?start=%s&end=%s&minlat=%.2f&maxlat=%.2f&minlon=%.2f&maxlon=%.2f&mindepth=%d&maxdepth=%d&minmag=%.1f&maxmag=%.1f&format=%s",
                    startTime, endTime, minLat, maxLat, minLon, maxLon, minDepth, maxDepth, minMag, maxMag, format);
            String fullUrl = baseUrl + queryParams;

            ResponseEntity<Earthquake[]> response = restTemplate.getForEntity(fullUrl, Earthquake[].class);

            List<Earthquake> earthquakeList = Arrays.asList(Objects.requireNonNull(response.getBody()));

            for (Earthquake earthquake : earthquakeList) {
                earthquakeRepository.save(earthquake);
            }

        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            System.out.println("ConstraintViolationException : The content is already exist ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public Page<Earthquake> findEarthquakesByDays(int days, int page, int size) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(days);
        Pageable sortedByDateDesc = PageRequest.of(page, size, Sort.by("date").descending());
        return earthquakeRepository.findByDateBetween(startTime, endTime, sortedByDateDesc);
    }



}
