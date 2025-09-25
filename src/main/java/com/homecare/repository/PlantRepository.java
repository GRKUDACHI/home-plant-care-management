package com.homecare.repository;

import com.homecare.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    
    // Find plants by type
    List<Plant> findByType(String type);
    
    // Find plants by watering frequency
    List<Plant> findByWateringFrequency(String wateringFrequency);
    
    // Find plants by sunlight needs
    List<Plant> findBySunlightNeeds(String sunlightNeeds);
    
    // Find plants that need watering (next watering date is today or past)
    @Query("SELECT p FROM Plant p WHERE p.nextWatering <= :today")
    List<Plant> findPlantsNeedingWatering(@Param("today") LocalDate today);
    
    // Find plants by name containing (case insensitive)
    List<Plant> findByNameContainingIgnoreCase(String name);
    
    // Find plants by type and sunlight needs
    List<Plant> findByTypeAndSunlightNeeds(String type, String sunlightNeeds);
    
    // Find plants added after a specific date
    List<Plant> findByAddedDateAfter(LocalDate date);
    
    // Find plants added between two dates
    List<Plant> findByAddedDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Count plants by type
    long countByType(String type);
    
    // Count plants that need watering
    @Query("SELECT COUNT(p) FROM Plant p WHERE p.nextWatering <= :today")
    long countPlantsNeedingWatering(@Param("today") LocalDate today);
    
    // Find plants with care notes
    @Query("SELECT p FROM Plant p WHERE p.careNotes IS NOT NULL AND p.careNotes != ''")
    List<Plant> findPlantsWithCareNotes();
    
    // Find plants without care notes
    @Query("SELECT p FROM Plant p WHERE p.careNotes IS NULL OR p.careNotes = ''")
    List<Plant> findPlantsWithoutCareNotes();

    //count The Total Plants
    @Query("SELECT COUNT(p) FROM Plant p")
    long findTotalPlants();
}
