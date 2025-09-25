package com.homecare.config;

import com.homecare.entity.Plant;
import com.homecare.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {
    
    @Autowired
    private PlantRepository plantRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Only seed data if no plants exist
        long plantCount = plantRepository.count();
        System.out.println("Current plant count in database: " + plantCount);
        
        if (plantCount == 0) {
            System.out.println("Database is empty, seeding sample data...");
            seedPlants();
        } else {
            System.out.println("Database already contains " + plantCount + " plants, skipping seed data.");
        }
    }
    
    private void seedPlants() {
        // Sample plants data
        Plant plant1 = new Plant();
        plant1.setName("Fiddle Leaf Fig");
        plant1.setType("tropical");
        plant1.setWateringFrequency("weekly");
        plant1.setSunlightNeeds("medium");
        plant1.setCareNotes("Keep away from direct sunlight. Water when top inch of soil is dry.");
        plant1.setImageUrl("https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=300&fit=crop&crop=center");
        plant1.setLastWatered(LocalDate.now().minusDays(3));
        plant1.setNextWatering(LocalDate.now().plusDays(4));
        plant1.setAddedDate(LocalDate.now().minusDays(30));
        
        Plant plant2 = new Plant();
        plant2.setName("Snake Plant");
        plant2.setType("succulent");
        plant2.setWateringFrequency("every-2-weeks");
        plant2.setSunlightNeeds("low");
        plant2.setCareNotes("Very low maintenance. Can survive in low light conditions.");
        plant2.setImageUrl("https://images.unsplash.com/photo-1593482892296-61358a3a5b1e?w=400&h=300&fit=crop&crop=center");
        plant2.setLastWatered(LocalDate.now().minusDays(5));
        plant2.setNextWatering(LocalDate.now().plusDays(9));
        plant2.setAddedDate(LocalDate.now().minusDays(25));
        
        Plant plant3 = new Plant();
        plant3.setName("Monstera Deliciosa");
        plant3.setType("tropical");
        plant3.setWateringFrequency("weekly");
        plant3.setSunlightNeeds("medium");
        plant3.setCareNotes("Loves humidity. Mist leaves occasionally.");
        plant3.setImageUrl("https://images.unsplash.com/photo-1509423350716-97f2360af5e0?w=400&h=300&fit=crop&crop=center");
        plant3.setLastWatered(LocalDate.now().minusDays(2));
        plant3.setNextWatering(LocalDate.now().plusDays(5));
        plant3.setAddedDate(LocalDate.now().minusDays(20));
        
        Plant plant4 = new Plant();
        plant4.setName("Aloe Vera");
        plant4.setType("succulent");
        plant4.setWateringFrequency("every-2-weeks");
        plant4.setSunlightNeeds("high");
        plant4.setCareNotes("Great for healing. Keep in bright, indirect light.");
        plant4.setImageUrl("https://images.unsplash.com/photo-1509423350716-97f2360af5e0?w=400&h=300&fit=crop&crop=center");
        plant4.setLastWatered(LocalDate.now().minusDays(8));
        plant4.setNextWatering(LocalDate.now().plusDays(6));
        plant4.setAddedDate(LocalDate.now().minusDays(22));
        
        Plant plant5 = new Plant();
        plant5.setName("Peace Lily");
        plant5.setType("flowering");
        plant5.setWateringFrequency("weekly");
        plant5.setSunlightNeeds("low");
        plant5.setCareNotes("Beautiful white flowers. Great for low light areas.");
        plant5.setImageUrl("https://images.unsplash.com/photo-1593482892296-61358a3a5b1e?w=400&h=300&fit=crop&crop=center");
        plant5.setLastWatered(LocalDate.now().minusDays(1));
        plant5.setNextWatering(LocalDate.now().plusDays(6));
        plant5.setAddedDate(LocalDate.now().minusDays(18));
        
        Plant plant6 = new Plant();
        plant6.setName("Rubber Plant");
        plant6.setType("tropical");
        plant6.setWateringFrequency("weekly");
        plant6.setSunlightNeeds("medium");
        plant6.setCareNotes("Large glossy leaves. Wipe leaves regularly to keep them shiny.");
        plant6.setImageUrl("https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=300&fit=crop&crop=center");
        plant6.setLastWatered(LocalDate.now().minusDays(4));
        plant6.setNextWatering(LocalDate.now().plusDays(3));
        plant6.setAddedDate(LocalDate.now().minusDays(15));
        
        Plant plant7 = new Plant();
        plant7.setName("Spider Plant");
        plant7.setType("herb");
        plant7.setWateringFrequency("weekly");
        plant7.setSunlightNeeds("medium");
        plant7.setCareNotes("Produces baby plants. Great for beginners.");
        plant7.setImageUrl("https://images.unsplash.com/photo-1509423350716-97f2360af5e0?w=400&h=300&fit=crop&crop=center");
        plant7.setLastWatered(LocalDate.now().minusDays(2));
        plant7.setNextWatering(LocalDate.now().plusDays(5));
        plant7.setAddedDate(LocalDate.now().minusDays(12));
        
        Plant plant8 = new Plant();
        plant8.setName("Pothos");
        plant8.setType("tropical");
        plant8.setWateringFrequency("weekly");
        plant8.setSunlightNeeds("low");
        plant8.setCareNotes("Trailing vine. Very easy to care for.");
        plant8.setImageUrl("https://images.unsplash.com/photo-1593482892296-61358a3a5b1e?w=400&h=300&fit=crop&crop=center");
        plant8.setLastWatered(LocalDate.now().minusDays(3));
        plant8.setNextWatering(LocalDate.now().plusDays(4));
        plant8.setAddedDate(LocalDate.now().minusDays(10));
        
        Plant plant9 = new Plant();
        plant9.setName("ZZ Plant");
        plant9.setType("succulent");
        plant9.setWateringFrequency("monthly");
        plant9.setSunlightNeeds("low");
        plant9.setCareNotes("Extremely low maintenance. Perfect for busy people.");
        plant9.setImageUrl("https://images.unsplash.com/photo-1509423350716-97f2360af5e0?w=400&h=300&fit=crop&crop=center");
        plant9.setLastWatered(LocalDate.now().minusDays(10));
        plant9.setNextWatering(LocalDate.now().plusDays(20));
        plant9.setAddedDate(LocalDate.now().minusDays(8));
        
        Plant plant10 = new Plant();
        plant10.setName("Boston Fern");
        plant10.setType("fern");
        plant10.setWateringFrequency("every-2-days");
        plant10.setSunlightNeeds("medium");
        plant10.setCareNotes("Loves humidity. Mist daily and keep soil moist.");
        plant10.setImageUrl("https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=300&fit=crop&crop=center");
        plant10.setLastWatered(LocalDate.now().minusDays(1));
        plant10.setNextWatering(LocalDate.now().plusDays(1));
        plant10.setAddedDate(LocalDate.now().minusDays(6));
        
        Plant plant11 = new Plant();
        plant11.setName("Jade Plant");
        plant11.setType("succulent");
        plant11.setWateringFrequency("every-2-weeks");
        plant11.setSunlightNeeds("high");
        plant11.setCareNotes("Symbol of good luck. Let soil dry between waterings.");
        plant11.setImageUrl("https://images.unsplash.com/photo-1509423350716-97f2360af5e0?w=400&h=300&fit=crop&crop=center");
        plant11.setLastWatered(LocalDate.now().minusDays(7));
        plant11.setNextWatering(LocalDate.now().plusDays(7));
        plant11.setAddedDate(LocalDate.now().minusDays(5));
        
        Plant plant12 = new Plant();
        plant12.setName("English Ivy");
        plant12.setType("herb");
        plant12.setWateringFrequency("weekly");
        plant12.setSunlightNeeds("medium");
        plant12.setCareNotes("Climbing vine. Great for hanging baskets.");
        plant12.setImageUrl("https://images.unsplash.com/photo-1593482892296-61358a3a5b1e?w=400&h=300&fit=crop&crop=center");
        plant12.setLastWatered(LocalDate.now().minusDays(2));
        plant12.setNextWatering(LocalDate.now().plusDays(5));
        plant12.setAddedDate(LocalDate.now().minusDays(4));
        
        Plant plant13 = new Plant();
        plant13.setName("Cactus Mix");
        plant13.setType("cactus");
        plant13.setWateringFrequency("monthly");
        plant13.setSunlightNeeds("high");
        plant13.setCareNotes("Desert plants. Very little water needed.");
        plant13.setImageUrl("https://images.unsplash.com/photo-1509423350716-97f2360af5e0?w=400&h=300&fit=crop&crop=center");
        plant13.setLastWatered(LocalDate.now().minusDays(15));
        plant13.setNextWatering(LocalDate.now().plusDays(15));
        plant13.setAddedDate(LocalDate.now().minusDays(3));
        
        Plant plant14 = new Plant();
        plant14.setName("Lavender");
        plant14.setType("herb");
        plant14.setWateringFrequency("weekly");
        plant14.setSunlightNeeds("high");
        plant14.setCareNotes("Fragrant herb. Great for aromatherapy.");
        plant14.setImageUrl("https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=300&fit=crop&crop=center");
        plant14.setLastWatered(LocalDate.now().minusDays(3));
        plant14.setNextWatering(LocalDate.now().plusDays(4));
        plant14.setAddedDate(LocalDate.now().minusDays(2));
        
        Plant plant15 = new Plant();
        plant15.setName("Orchid");
        plant15.setType("flowering");
        plant15.setWateringFrequency("weekly");
        plant15.setSunlightNeeds("medium");
        plant15.setCareNotes("Elegant flowers. Water with ice cubes once a week.");
        plant15.setImageUrl("https://images.unsplash.com/photo-1593482892296-61358a3a5b1e?w=400&h=300&fit=crop&crop=center");
        plant15.setLastWatered(LocalDate.now().minusDays(2));
        plant15.setNextWatering(LocalDate.now().plusDays(5));
        plant15.setAddedDate(LocalDate.now().minusDays(1));
        
        // Save all plants
        plantRepository.save(plant1);
        plantRepository.save(plant2);
        plantRepository.save(plant3);
        plantRepository.save(plant4);
        plantRepository.save(plant5);
        plantRepository.save(plant6);
        plantRepository.save(plant7);
        plantRepository.save(plant8);
        plantRepository.save(plant9);
        plantRepository.save(plant10);
        plantRepository.save(plant11);
        plantRepository.save(plant12);
        plantRepository.save(plant13);
        plantRepository.save(plant14);
        plantRepository.save(plant15);
        
        // Verify data was saved
        long finalCount = plantRepository.count();
        System.out.println("Sample plant data seeded successfully! Total plants in database: " + finalCount);
    }
}
