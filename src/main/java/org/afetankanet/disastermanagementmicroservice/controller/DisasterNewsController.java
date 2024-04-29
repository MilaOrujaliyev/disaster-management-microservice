package org.afetankanet.disastermanagementmicroservice.controller;

import org.afetankanet.disastermanagementmicroservice.entity.DisasterNews;
import org.afetankanet.disastermanagementmicroservice.service.DisasterNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/disaster-news")
public class DisasterNewsController {

    private final DisasterNewsService disasterNewsService;

    @Autowired
    public DisasterNewsController(DisasterNewsService disasterNewsService) {
        this.disasterNewsService = disasterNewsService;
    }

    @GetMapping("/paging")
    public ResponseEntity<Page<DisasterNews>> getAllDisasterNewsWithPaging(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(disasterNewsService.getAllDisasterNewsWithPaging(page, size));
    }

    @GetMapping("/disaster")
    public ResponseEntity<Page<DisasterNews>> getNewsByDisaster(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(disasterNewsService.findNewsByCriteria("afet", page, size));
    }

    @GetMapping("/flood")
    public ResponseEntity<Page<DisasterNews>> getNewsByFlood(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(disasterNewsService.findNewsByCriteria("sel", page, size));
    }

    @GetMapping("/fire")
    public ResponseEntity<Page<DisasterNews>> getNewsByFire(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(disasterNewsService.findNewsByCriteria("yangın", page, size));
    }

    @GetMapping("/earthquake")
    public ResponseEntity<Page<DisasterNews>> getNewsByEarthquake(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(disasterNewsService.findNewsByCriteria("deprem", page, size));
    }

    @GetMapping("/landslide")
    public ResponseEntity<Page<DisasterNews>> getNewsByLandslide(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(disasterNewsService.findNewsByCriteria("heyelan", page, size));
    }

    @GetMapping("/weather")
    public ResponseEntity<Page<DisasterNews>> getNewsByWeather(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(disasterNewsService.findNewsByCriteria("hava durumu", page, size));
    }
}
