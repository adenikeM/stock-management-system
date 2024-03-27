package com.techfirm.stock.controller;

import com.techfirm.stock.model.Location;
import com.techfirm.stock.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.techfirm.stock.exception.ErrorResponse.buildErrorResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@Slf4j
@RequestMapping("api")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocation() {
        return ResponseEntity.ok().body(locationService.getAllLocation());
    }

    @GetMapping("/v2/locations")
    public ResponseEntity<List<Location>> getAllLocation2(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "0") Integer pageSize){
        Page<Location> locations = locationService.getAllLocation2(page, pageSize);
        return ResponseEntity.ok(locations.getContent());
    }


    @GetMapping("/locations/{id}")
    public ResponseEntity<?> getLocationByID(@PathVariable Long id) {
        log.info("Get Location id by " + id);
        if (id < 1) {
            ResponseEntity.badRequest().body(
                    buildErrorResponse("Location id cannot be less than 1", BAD_REQUEST));
        }
        return locationService.getLocationById(id)
                              .map(location -> ResponseEntity.ok().body(location))
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/locations")
    public ResponseEntity<?> createLocation(@RequestBody Location location) {
        log.info("Request to create location => {}", location);
        if (location.getId() != null) {
            log.info("product location => {}", location);
            return ResponseEntity.badRequest()
                                 .body(buildErrorResponse("ID should be null, Id = "
                                         + location.getId(), HttpStatus.BAD_REQUEST));
        }
        return ResponseEntity.ok().body(locationService.createLocation(location));
    }

    @PutMapping("/locations")
    public ResponseEntity<?> updateLocation(@RequestBody Location location) {
        if (location.getId() == null) {
            return ResponseEntity.badRequest()
                                 .body(buildErrorResponse("ID cannot be null, Id = "
                                         + location.getId(), HttpStatus.BAD_REQUEST));
        }
        Optional<Location> updatedLocation = locationService.updateLocation(location);
        if (updatedLocation.isPresent()) {
            return ResponseEntity.ok(updatedLocation);
        } else {
            return ResponseEntity.badRequest().body(buildErrorResponse(
                    "Location with id " + location.getId() + "doesn't exist, Enter correct location id", BAD_REQUEST));
        }
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<Location> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
