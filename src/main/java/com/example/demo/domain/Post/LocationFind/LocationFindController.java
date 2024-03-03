package com.example.demo.domain.Post.LocationFind;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationFindController {

    private final LocationFindService locationFindService;

    @GetMapping("/locationFind")
    public ResponseEntity<List<LocationFind>> getAllLocations() {
        List<LocationFind> locations = locationFindService.getAllLocations();
        return ResponseEntity.ok(locations);
    }
}