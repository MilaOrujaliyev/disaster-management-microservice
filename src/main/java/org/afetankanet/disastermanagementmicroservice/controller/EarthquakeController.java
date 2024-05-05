package org.afetankanet.disastermanagementmicroservice.controller;

import org.afetankanet.disastermanagementmicroservice.entity.Earthquake;
import org.afetankanet.disastermanagementmicroservice.service.EarthquakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/earthquakes")
public class EarthquakeController {

    @Autowired
    private EarthquakeService earthquakeService;

    @GetMapping("/last-24-hours")
    public ResponseEntity<Page<Earthquake>> getLast24HoursEarthquakes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(earthquakeService.findEarthquakesByDays(1, page, size));
    }

    @GetMapping("/last-2-days")
    public ResponseEntity<Page<Earthquake>> getLast2DaysEarthquakes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(earthquakeService.findEarthquakesByDays(2, page, size));
    }

    @GetMapping("/last-3-days")
    public ResponseEntity<Page<Earthquake>> getLast3DaysEarthquakes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(earthquakeService.findEarthquakesByDays(3, page, size));
    }

    @GetMapping("/last-4-days")
    public ResponseEntity<Page<Earthquake>> getLast4DaysEarthquakes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(earthquakeService.findEarthquakesByDays(4, page, size));
    }

    @GetMapping("/last-5-days")
    public ResponseEntity<Page<Earthquake>> getLast5DaysEarthquakes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(earthquakeService.findEarthquakesByDays(5, page, size));
    }


    @GetMapping("/earthquakes-by-province")
    public ResponseEntity<Page<Earthquake>> getEarthquakesByProvince(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String province) {
        return ResponseEntity.ok(earthquakeService.findEarthquakesByProvince(province, page, size));
    }

    @GetMapping("/earthquakes-magnitude-0-to-2.9")
    public ResponseEntity<Page<Earthquake>> getEarthquakesMagnitude0To2_9(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitude(0.0, 2.9, page, size));
    }
    @GetMapping("/earthquakes-magnitude-3-to-3.9")
    public ResponseEntity<Page<Earthquake>> getEarthquakesMagnitude3To3_9(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitude(3.0, 3.9, page, size));
    }
    @GetMapping("/earthquakes-magnitude-4-to-4.9")
    public ResponseEntity<Page<Earthquake>> getEarthquakesMagnitude4To4_9(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitude(4.0, 4.9, page, size));
    }
    @GetMapping("/earthquakes-magnitude-5-to-5.9")
    public ResponseEntity<Page<Earthquake>> getEarthquakesMagnitude5To5_9(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitude(5.0, 5.9, page, size));
    }
    @GetMapping("/earthquakes-magnitude-6-to-6.9")
    public ResponseEntity<Page<Earthquake>> getEarthquakesMagnitude6To6_9(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitude(6.0, 6.9, page, size));
    }
    @GetMapping("/earthquakes-magnitude-7-to-7.9")
    public ResponseEntity<Page<Earthquake>> getEarthquakesMagnitude7To7_9(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitude(7.0, 7.9, page, size));
    }
    @GetMapping("/earthquakes-magnitude-8-and-above")
    public ResponseEntity<Page<Earthquake>> getEarthquakesMagnitude8AndAbove(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitude(8.0, Double.MAX_VALUE, page, size));
    }

    //last 24 minutes-Earthquakes according to magnitude-range
    @GetMapping("/last 24 minutes-magnitude-range/0-2.9")
    public ResponseEntity<List<Earthquake>> getEarthquakesMagnitude0To2_9() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(1);
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitudeAndDate(0, 2.9, startDate, endDate));
    }

    @GetMapping("/last 24 minutes-magnitude-range/3-3.9")
    public ResponseEntity<List<Earthquake>> getEarthquakesMagnitude3To3_9() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(1);
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitudeAndDate(3, 3.9, startDate, endDate));
    }

    @GetMapping("/last 24 minutes-magnitude-range/4-4.9")
    public ResponseEntity<List<Earthquake>> getEarthquakesMagnitude4To4_9() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(1);
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitudeAndDate(4, 4.9, startDate, endDate));
    }

    @GetMapping("/last 24 minutes-magnitude-range/5-5.9")
    public ResponseEntity<List<Earthquake>> getEarthquakesMagnitude5To5_9() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(1);
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitudeAndDate(5, 5.9, startDate, endDate));
    }

    @GetMapping("/last 24 minutes-magnitude-range/6-6.9")
    public ResponseEntity<List<Earthquake>> getEarthquakesMagnitude6To6_9() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(1);
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitudeAndDate(6, 6.9, startDate, endDate));
    }

    @GetMapping("/last 24 minutes-magnitude-range/7-7.9")
    public ResponseEntity<List<Earthquake>> getEarthquakesMagnitude7To7_9() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(1);
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitudeAndDate(7, 7.9, startDate, endDate));
    }

    @GetMapping("/last 24 minutes-magnitude-range/8-")
    public ResponseEntity<List<Earthquake>> getEarthquakesMagnitude8AndAbove() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(1);
        return ResponseEntity.ok(earthquakeService.findEarthquakesByMagnitudeAndDate(8, Double.MAX_VALUE, startDate, endDate));
    }

    //last-5-earthquakes for map
    @GetMapping("/last-5-earthquakes")
    public ResponseEntity<List<Earthquake>> getLast5Earthquakes() {
        return ResponseEntity.ok(earthquakeService.findTop5RecentEarthquakes());
    }

    //latest-earthquake
    @GetMapping("/latest-earthquake")
    public ResponseEntity<Earthquake> getLatestEarthquake() {
        return ResponseEntity.ok(earthquakeService.findMostRecentEarthquake());
    }
}

