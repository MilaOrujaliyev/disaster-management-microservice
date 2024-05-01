package org.afetankanet.disastermanagementmicroservice.controller;

import org.afetankanet.disastermanagementmicroservice.entity.Earthquake;
import org.afetankanet.disastermanagementmicroservice.service.EarthquakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/earthquakes")
public class EarthquakeController {

    @Autowired
    private EarthquakeService earthquakeService;

    @GetMapping("/last24hours")
    public ResponseEntity<List<Earthquake>> getRecentEarthquakes() {

        //dolduracagım
        return ResponseEntity.ok().body(null);
    }
}

