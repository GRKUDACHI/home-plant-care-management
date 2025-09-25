package com.homecare.controller;

import com.homecare.entity.Plant;
import com.homecare.repository.PlantRepository;
import com.homecare.service.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/pdf")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000", "http://localhost:5500", "http://127.0.0.1:5500", "file://"})
public class PdfController {
    
    @Autowired
    private PlantRepository plantRepository;
    
    @Autowired
    private PdfGenerationService pdfGenerationService;
    
    @GetMapping("/plant/{id}")
    public CompletableFuture<ResponseEntity<ByteArrayResource>> generatePlantPdf(@PathVariable Long id) {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        
        if (plantOptional.isEmpty()) {
            return CompletableFuture.completedFuture(ResponseEntity.<ByteArrayResource>notFound().build());
        }
        
        Plant plant = plantOptional.get();
        
        return pdfGenerationService.generatePlantPdf(plant)
            .thenApply(pdfBytes -> {
                ByteArrayResource resource = new ByteArrayResource(pdfBytes);
                
                String filename = plant.getName().replaceAll("[^a-zA-Z0-9]", "_") + "_Care_Guide.html";
                
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(pdfBytes.length)
                    .body(resource);
            })
            .exceptionally(throwable -> {
                return ResponseEntity.<ByteArrayResource>internalServerError().build();
            });
    }
    
    @GetMapping("/plant/{id}/preview")
    public CompletableFuture<ResponseEntity<String>> generatePlantPdfPreview(@PathVariable Long id) {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        
        if (plantOptional.isEmpty()) {
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
        
        Plant plant = plantOptional.get();
        
        return pdfGenerationService.generatePlantPdf(plant)
            .thenApply(pdfBytes -> {
                String htmlContent = new String(pdfBytes);
                return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(htmlContent);
            })
            .exceptionally(throwable -> {
                return ResponseEntity.internalServerError().build();
            });
    }
}