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


}
