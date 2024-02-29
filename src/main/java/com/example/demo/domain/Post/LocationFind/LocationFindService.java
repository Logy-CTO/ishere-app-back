package com.example.demo.domain.Post.LocationFind;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationFindService {

    private final LocationFindRepository locationFindRepository;

    public List<LocationFind> getAllLocations() {
        return locationFindRepository.findAll();
    }
}