package com.techfirm.stock.controller;

import com.techfirm.stock.model.Location;
import com.techfirm.stock.model.ProductCategory;
import com.techfirm.stock.service.LocationService;
import com.techfirm.stock.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.techfirm.stock.exception.ErrorResponse.buildErrorResponse;
import static com.techfirm.stock.utils.Validation.validateCreateLocationRequest;
import static com.techfirm.stock.utils.Validation.validateUpdateLocation;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@Slf4j
@RequestMapping("location")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocation(){
        return ResponseEntity.ok().body(locationService.getAllLocation());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?>getLocationByID(@PathVariable Integer id){
        log.info("Get Location id by " + id);
        if(id < 1){
            ResponseEntity.badRequest().body(
                    buildErrorResponse("Location id cannot be less than 1", BAD_REQUEST));
        }
        return locationService.getLocation(id)
                                     .map(location -> ResponseEntity.ok().body(location))
                                     .orElseGet(()-> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<?>createLocation(@RequestBody Location location){
        log.info("Request to create location => {}", location);
        if(location.getId() != null){
            log.info("product location => {}", location);
           return validateCreateLocationRequest(location);
        }
        return ResponseEntity.ok().body(locationService.createLocation(location));
    }
    @PutMapping
    public ResponseEntity<?> updateLocation(@RequestBody Location location){
        if(location.getId()==null){
            return validateUpdateLocation(location);
        }
        Optional<Location> updatedLocation = locationService.updateLocation(location);
        if(updatedLocation.isPresent()){
            return ResponseEntity.ok(updatedLocation);
        }else{
            return ResponseEntity.badRequest().body(buildErrorResponse(
                    "Location with id " + location.getId() + "doesn't exist, Enter correct location id", BAD_REQUEST));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Location> deleteLocation(@PathVariable Integer id){
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
