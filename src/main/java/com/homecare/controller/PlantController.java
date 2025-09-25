package com.homecare.controller;

import com.homecare.entity.Plant;
import com.homecare.repository.PlantRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plants")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000", "http://localhost:5500", "http://127.0.0.1:5500", "file://"})
public class PlantController {
    
    @Autowired
    private PlantRepository plantRepository;
    
    // Get all plants (view all palnts adat) 
    @GetMapping
    public ResponseEntity<List<Plant>> getAllPlants() {
        try {
            List<Plant> plants = plantRepository.findAll();
            return ResponseEntity.ok(plants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Get plant by ID single palnt id based
    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlantById(@PathVariable Long id) {
        try {
            Optional<Plant> plant = plantRepository.findById(id);
            if (plant.isPresent()) {
                return ResponseEntity.ok(plant.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Create new plant Add the plants (POst method for add/save data)
    @PostMapping
    public ResponseEntity<Plant> createPlant(@Valid @RequestBody Plant plant) {
        try {
            // Set default values if not provided
            if (plant.getAddedDate() == null) {
                plant.setAddedDate(LocalDate.now());
            }
            if (plant.getLastWatered() == null) {
                plant.setLastWatered(LocalDate.now());
            }
            if (plant.getNextWatering() == null) {
                plant.setNextWatering(calculateNextWatering(plant.getWateringFrequency()));
            }
            
            Plant savedPlant = plantRepository.save(plant);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPlant);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Update plant (Upadet Existing dats)
    @PutMapping("/{id}")
    public ResponseEntity<Plant> updatePlant(@PathVariable Long id, @Valid @RequestBody Plant plantDetails) {
        try {
            Optional<Plant> optionalPlant = plantRepository.findById(id);
            if (optionalPlant.isPresent()) {
                Plant plant = optionalPlant.get();
                
                // Update fields
                plant.setName(plantDetails.getName());
                plant.setType(plantDetails.getType());
                plant.setWateringFrequency(plantDetails.getWateringFrequency());
                plant.setSunlightNeeds(plantDetails.getSunlightNeeds());
                plant.setCareNotes(plantDetails.getCareNotes());
                plant.setImageUrl(plantDetails.getImageUrl());
                
                // Update next watering if watering frequency changed (update if water dara are changed )
                if (!plant.getWateringFrequency().equals(plantDetails.getWateringFrequency())) {
                    plant.setNextWatering(calculateNextWatering(plantDetails.getWateringFrequency()));
                }
                
                Plant updatedPlant = plantRepository.save(plant);
                return ResponseEntity.ok(updatedPlant);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Delete plant (Delete the plants based on id)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
        try {
            if (plantRepository.existsById(id)) {
                plantRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Water plant (update last watered date and next watering date)
    @PostMapping("/{id}/water")
    public ResponseEntity<Plant> waterPlant(@PathVariable Long id) {
        try {
            Optional<Plant> optionalPlant = plantRepository.findById(id);
            if (optionalPlant.isPresent()) {
                Plant plant = optionalPlant.get();
                plant.setLastWatered(LocalDate.now());
                plant.setNextWatering(calculateNextWatering(plant.getWateringFrequency()));
                
                Plant updatedPlant = plantRepository.save(plant);
                return ResponseEntity.ok(updatedPlant);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Get plants by type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Plant>> getPlantsByType(@PathVariable String type) {
        try {
            List<Plant> plants = plantRepository.findByType(type);
            return ResponseEntity.ok(plants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Get plants that need watering
    @GetMapping("/needs-watering")
    public ResponseEntity<List<Plant>> getPlantsNeedingWatering() {
        try {
            List<Plant> plants = plantRepository.findPlantsNeedingWatering(LocalDate.now());
            return ResponseEntity.ok(plants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Search plants by name
    @GetMapping("/search")
    public ResponseEntity<List<Plant>> searchPlants(@RequestParam String name) {
        try {
            List<Plant> plants = plantRepository.findByNameContainingIgnoreCase(name);
            return ResponseEntity.ok(plants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Get plant statistics
    @GetMapping("/stats")
    public ResponseEntity<PlantStats> getPlantStats() {
        try {
            long totalPlants = plantRepository.count();
            long plantsNeedingWatering = plantRepository.countPlantsNeedingWatering(LocalDate.now());
            
            PlantStats stats = new PlantStats();
            stats.setTotalPlants(totalPlants);
            stats.setPlantsNeedingWatering(plantsNeedingWatering);
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Helper method to calculate next watering date
    private LocalDate calculateNextWatering(String wateringFrequency) {
        LocalDate today = LocalDate.now();
        switch (wateringFrequency.toLowerCase()) {
            case "daily":
                return today.plusDays(1);
            case "every-2-days":
                return today.plusDays(2);
            case "weekly":
                return today.plusWeeks(1);
            case "every-2-weeks":
                return today.plusWeeks(2);
            case "monthly":
                return today.plusMonths(1);
            default:
                return today.plusWeeks(1); // Default to weekly
        }
    }
    
    // Inner class for plant statistics
    public static class PlantStats {
        private long totalPlants;
        private long plantsNeedingWatering;
        
        public long getTotalPlants() {
            return totalPlants;
        }
        
        public void setTotalPlants(long totalPlants) {
            this.totalPlants = totalPlants;
        }
        
        public long getPlantsNeedingWatering() {
            return plantsNeedingWatering;
        }
        
        public void setPlantsNeedingWatering(long plantsNeedingWatering) {
            this.plantsNeedingWatering = plantsNeedingWatering;
        }
    }


    //Count the total plants
    @GetMapping("/total-plants")
    public ResponseEntity<Long> getTotalPlants() {
        try {
            long totalPlants = plantRepository.findTotalPlants();
            return ResponseEntity.ok(totalPlants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }   
}
