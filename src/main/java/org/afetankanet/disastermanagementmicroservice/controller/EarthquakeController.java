package org.afetankanet.disastermanagementmicroservice.controller;

import org.afetankanet.disastermanagementmicroservice.entity.Earthquake;
import org.afetankanet.disastermanagementmicroservice.service.EarthquakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}

