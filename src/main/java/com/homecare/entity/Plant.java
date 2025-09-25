package com.homecare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "plants")
public class Plant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Plant name is required")
    @Size(max = 100, message = "Plant name must not exceed 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @NotBlank(message = "Plant type is required")
    @Size(max = 50, message = "Plant type must not exceed 50 characters")
    @Column(name = "type", nullable = false, length = 50)
    private String type;
    
    @NotBlank(message = "Watering frequency is required")
    @Size(max = 50, message = "Watering frequency must not exceed 50 characters")
    @Column(name = "watering_frequency", nullable = false, length = 50)
    private String wateringFrequency;
    
    @NotBlank(message = "Sunlight needs is required")
    @Size(max = 50, message = "Sunlight needs must not exceed 50 characters")
    @Column(name = "sunlight_needs", nullable = false, length = 50)
    private String sunlightNeeds;
    
    @Size(max = 1000, message = "Care notes must not exceed 1000 characters")
    @Column(name = "care_notes", length = 1000)
    private String careNotes;
    
    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;
    
    @Column(name = "last_watered")
    private LocalDate lastWatered;
    
    @Column(name = "next_watering")
    private LocalDate nextWatering;
    
    @Column(name = "added_date")
    private LocalDate addedDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Default constructor
    public Plant() {
    }
    
    // Constructor with required fields
    public Plant(String name, String type, String wateringFrequency, String sunlightNeeds) {
        this.name = name;
        this.type = type;
        this.wateringFrequency = wateringFrequency;
        this.sunlightNeeds = sunlightNeeds;
        this.addedDate = LocalDate.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getWateringFrequency() {
        return wateringFrequency;
    }
    
    public void setWateringFrequency(String wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }
    
    public String getSunlightNeeds() {
        return sunlightNeeds;
    }
    
    public void setSunlightNeeds(String sunlightNeeds) {
        this.sunlightNeeds = sunlightNeeds;
    }
    
    public String getCareNotes() {
        return careNotes;
    }
    
    public void setCareNotes(String careNotes) {
        this.careNotes = careNotes;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public LocalDate getLastWatered() {
        return lastWatered;
    }
    
    public void setLastWatered(LocalDate lastWatered) {
        this.lastWatered = lastWatered;
    }
    
    public LocalDate getNextWatering() {
        return nextWatering;
    }
    
    public void setNextWatering(LocalDate nextWatering) {
        this.nextWatering = nextWatering;
    }
    
    public LocalDate getAddedDate() {
        return addedDate;
    }
    
    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // JPA lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (addedDate == null) {
            addedDate = LocalDate.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", wateringFrequency='" + wateringFrequency + '\'' +
                ", sunlightNeeds='" + sunlightNeeds + '\'' +
                ", careNotes='" + careNotes + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", lastWatered=" + lastWatered +
                ", nextWatering=" + nextWatering +
                ", addedDate=" + addedDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
