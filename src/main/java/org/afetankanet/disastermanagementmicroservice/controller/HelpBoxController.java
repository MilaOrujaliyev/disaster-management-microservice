package org.afetankanet.disastermanagementmicroservice.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.afetankanet.disastermanagementmicroservice.entity.HelpBox;
import org.afetankanet.disastermanagementmicroservice.model.Category;
import org.afetankanet.disastermanagementmicroservice.model.City;
import org.afetankanet.disastermanagementmicroservice.service.HelpBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/helpbox")
public class HelpBoxController {

    @Autowired
    private HelpBoxService helpBoxService;

    @GetMapping("/cities")
    public List<String> getCities(){
        return Arrays.stream(City.values())
                .map(City::getDisplayName)
                .collect(Collectors.toList());
    }

    @GetMapping("/categories")
    public List<String> getAllCategories(){
        return Arrays.stream(Category.values()).map(Category::getDisplayName).collect(Collectors.toList());
    }

    @PostMapping("/createHelpBox")
    public ResponseEntity<?> createHelpBox(@Valid @RequestBody  HelpBox helpBox){
        try{
            return ResponseEntity.ok(helpBoxService.createHelpBox( helpBox));
        }catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping("/updateHelpBox/{id}")
    public ResponseEntity<?> updateHelpBox(@PathVariable Long id, @Valid @RequestBody HelpBox helpBox) {

        try{
            return ResponseEntity.ok(helpBoxService.updateHelpBox(id, helpBox));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    @GetMapping("/getAllHelpBoxes")
    public ResponseEntity<List<HelpBox>> getAllHelpBoxes(){
        return ResponseEntity.ok(helpBoxService.getAllHelpBoxes());
    }

    @GetMapping("/getHelpBoxesByUserId/{userId}")
    public ResponseEntity<List<HelpBox>> getHelpBoxesByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(helpBoxService.getHelpBoxesByUserId(userId));
    }

}
