package com.techfirm.stock.service;

import com.techfirm.stock.model.Location;
import com.techfirm.stock.model.ProductCategory;
import com.techfirm.stock.repository.LocationRepository;
import com.techfirm.stock.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
    public List<Location> getAllLocation(){
        return locationRepository.findAll();
    }
    public Optional<Location> getLocation(Integer id){
        return locationRepository.findById(id);
    }
    public Location createLocation(Location location){
        return locationRepository.save(location);
    }
    public Optional<Location> updateLocation(Location location){
        locationRepository.findById(location.getId());
        if(location.getId() == null){
            throw  new IllegalArgumentException("Location  id must not be null");
        }
        return Optional.of(locationRepository.save(location));
    }
    public void deleteLocation(Integer id){locationRepository.findById(id);
    }
}
