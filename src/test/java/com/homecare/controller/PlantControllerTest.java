package com.homecare.controller;

import com.homecare.entity.Plant;
import com.homecare.repository.PlantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
public class PlantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPlants() throws Exception {
        mockMvc.perform(get("/api/plants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreatePlant() throws Exception {
        Plant plant = new Plant();
        plant.setName("Test Plant");
        plant.setType("tropical");
        plant.setWateringFrequency("weekly");
        plant.setSunlightNeeds("medium");
        plant.setCareNotes("Test care notes");

        mockMvc.perform(post("/api/plants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plant)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Plant"))
                .andExpect(jsonPath("$.type").value("tropical"));
    }

    @Test
    public void testGetPlantById() throws Exception {
        Plant plant = new Plant();
        plant.setName("Test Plant");
        plant.setType("tropical");
        plant.setWateringFrequency("weekly");
        plant.setSunlightNeeds("medium");
        plant = plantRepository.save(plant);

        mockMvc.perform(get("/api/plants/" + plant.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Plant"));
    }

    @Test
    public void testUpdatePlant() throws Exception {
        Plant plant = new Plant();
        plant.setName("Test Plant");
        plant.setType("tropical");
        plant.setWateringFrequency("weekly");
        plant.setSunlightNeeds("medium");
        plant = plantRepository.save(plant);

        plant.setName("Updated Plant");
        plant.setCareNotes("Updated care notes");

        mockMvc.perform(put("/api/plants/" + plant.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Plant"));
    }

    @Test
    public void testDeletePlant() throws Exception {
        Plant plant = new Plant();
        plant.setName("Test Plant");
        plant.setType("tropical");
        plant.setWateringFrequency("weekly");
        plant.setSunlightNeeds("medium");
        plant = plantRepository.save(plant);

        mockMvc.perform(delete("/api/plants/" + plant.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testWaterPlant() throws Exception {
        Plant plant = new Plant();
        plant.setName("Test Plant");
        plant.setType("tropical");
        plant.setWateringFrequency("weekly");
        plant.setSunlightNeeds("medium");
        plant = plantRepository.save(plant);

        mockMvc.perform(post("/api/plants/" + plant.getId() + "/water"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastWatered").exists());
    }

    @Test
    public void testGetPlantsByType() throws Exception {
        Plant plant = new Plant();
        plant.setName("Test Plant");
        plant.setType("tropical");
        plant.setWateringFrequency("weekly");
        plant.setSunlightNeeds("medium");
        plantRepository.save(plant);

        mockMvc.perform(get("/api/plants/type/tropical"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("tropical"));
    }

    @Test
    public void testSearchPlants() throws Exception {
        Plant plant = new Plant();
        plant.setName("Test Plant");
        plant.setType("tropical");
        plant.setWateringFrequency("weekly");
        plant.setSunlightNeeds("medium");
        plantRepository.save(plant);

        mockMvc.perform(get("/api/plants/search?name=Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Plant"));
    }

    @Test
    public void testGetPlantStats() throws Exception {
        Plant plant = new Plant();
        plant.setName("Test Plant");
        plant.setType("tropical");
        plant.setWateringFrequency("weekly");
        plant.setSunlightNeeds("medium");
        plantRepository.save(plant);

        mockMvc.perform(get("/api/plants/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPlants").exists())
                .andExpect(jsonPath("$.plantsNeedingWatering").exists());
    }
}
