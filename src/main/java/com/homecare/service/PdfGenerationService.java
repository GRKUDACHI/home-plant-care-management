package com.homecare.service;

import com.homecare.entity.Plant;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PdfGenerationService {
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    
    public CompletableFuture<byte[]> generatePlantPdf(Plant plant) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return createPlantPdf(plant);
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate PDF for plant: " + plant.getName(), e);
            }
        }, executorService);
    }
    
    private byte[] createPlantPdf(Plant plant) throws IOException {
        String htmlContent = generatePlantHtml(plant);
        
        // For now, we'll return a simple HTML content as bytes
        // In a real implementation, you would use a library like iText or wkhtmltopdf
        // to convert HTML to PDF
        return htmlContent.getBytes("UTF-8");
    }
    
    private String generatePlantHtml(Plant plant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>%s - Plant Care Guide</title>
                <style>
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        line-height: 1.6;
                        color: #333;
                        max-width: 800px;
                        margin: 0 auto;
                        padding: 20px;
                        background-color: #f8f9fa;
                    }
                    .header {
                        text-align: center;
                        background: linear-gradient(135deg, #2ecc71, #27ae60);
                        color: white;
                        padding: 30px;
                        border-radius: 10px;
                        margin-bottom: 30px;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 2.5em;
                        font-weight: 300;
                    }
                    .header p {
                        margin: 10px 0 0 0;
                        font-size: 1.2em;
                        opacity: 0.9;
                    }
                    .plant-image {
                        text-align: center;
                        margin: 20px 0;
                    }
                    .plant-image img {
                        max-width: 300px;
                        height: 200px;
                        object-fit: cover;
                        border-radius: 10px;
                        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                    }
                    .info-section {
                        background: white;
                        padding: 25px;
                        margin: 20px 0;
                        border-radius: 10px;
                        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                    }
                    .info-section h2 {
                        color: #2ecc71;
                        border-bottom: 2px solid #ecf0f1;
                        padding-bottom: 10px;
                        margin-top: 0;
                    }
                    .info-grid {
                        display: grid;
                        grid-template-columns: 1fr 1fr;
                        gap: 20px;
                        margin: 20px 0;
                    }
                    .info-item {
                        display: flex;
                        align-items: center;
                        padding: 15px;
                        background: #f8f9fa;
                        border-radius: 8px;
                    }
                    .info-item i {
                        font-size: 1.5em;
                        margin-right: 15px;
                        color: #2ecc71;
                        width: 30px;
                    }
                    .info-item .label {
                        font-weight: 600;
                        color: #555;
                        margin-right: 10px;
                    }
                    .info-item .value {
                        color: #333;
                    }
                    .care-notes {
                        background: #e8f5e8;
                        border-left: 4px solid #2ecc71;
                        padding: 20px;
                        margin: 20px 0;
                        border-radius: 0 8px 8px 0;
                    }
                    .care-notes h3 {
                        margin-top: 0;
                        color: #27ae60;
                    }
                    .footer {
                        text-align: center;
                        margin-top: 40px;
                        padding: 20px;
                        background: #34495e;
                        color: white;
                        border-radius: 10px;
                    }
                    .footer p {
                        margin: 5px 0;
                    }
                    .water-status {
                        display: inline-block;
                        padding: 8px 16px;
                        border-radius: 20px;
                        font-weight: 600;
                        margin: 10px 0;
                    }
                    .needs-watering {
                        background: #e74c3c;
                        color: white;
                    }
                    .watered-recently {
                        background: #2ecc71;
                        color: white;
                    }
                </style>
            </head>
            <body>
                <div class="header">
                    <h1>🌱 %s</h1>
                    <p>%s Plant Care Guide</p>
                </div>
                
                <div class="plant-image">
                    %s
                </div>
                
                <div class="info-section">
                    <h2>📋 Plant Information</h2>
                    <div class="info-grid">
                        <div class="info-item">
                            <i>🌿</i>
                            <div>
                                <div class="label">Type:</div>
                                <div class="value">%s</div>
                            </div>
                        </div>
                        <div class="info-item">
                            <i>💧</i>
                            <div>
                                <div class="label">Watering:</div>
                                <div class="value">%s</div>
                            </div>
                        </div>
                        <div class="info-item">
                            <i>☀️</i>
                            <div>
                                <div class="label">Light Needs:</div>
                                <div class="value">%s</div>
                            </div>
                        </div>
                        <div class="info-item">
                            <i>📅</i>
                            <div>
                                <div class="label">Added:</div>
                                <div class="value">%s</div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="info-section">
                    <h2>💧 Watering Schedule</h2>
                    <div class="info-item">
                        <i>📅</i>
                        <div>
                            <div class="label">Last Watered:</div>
                            <div class="value">%s</div>
                        </div>
                    </div>
                    <div class="info-item">
                        <i>⏰</i>
                        <div>
                            <div class="label">Next Watering:</div>
                            <div class="value">%s</div>
                        </div>
                    </div>
                    <div class="water-status %s">
                        %s
                    </div>
                </div>
                
                %s
                
                <div class="footer">
                    <p><strong>PlantCare - Your Indoor Garden Companion</strong></p>
                    <p>Generated on %s</p>
                    <p>Keep your plants healthy and happy! 🌱</p>
                </div>
            </body>
            </html>
            """,
            plant.getName(),
            plant.getName(),
            capitalizeFirst(plant.getType()),
            plant.getImageUrl() != null ? 
                String.format("<img src=\"%s\" alt=\"%s\">", plant.getImageUrl(), plant.getName()) :
                "<div style=\"width: 300px; height: 200px; background: #e8f5e8; display: flex; align-items: center; justify-content: center; border-radius: 10px; font-size: 4em; color: #2ecc71;\">🌱</div>",
            capitalizeFirst(plant.getType()),
            formatWateringFrequency(plant.getWateringFrequency()),
            formatSunlightNeeds(plant.getSunlightNeeds()),
            plant.getAddedDate() != null ? plant.getAddedDate().format(formatter) : "Unknown",
            plant.getLastWatered() != null ? plant.getLastWatered().format(formatter) : "Unknown",
            plant.getNextWatering() != null ? plant.getNextWatering().format(formatter) : "Unknown",
            isWateringDue(plant.getNextWatering()) ? "needs-watering" : "watered-recently",
            isWateringDue(plant.getNextWatering()) ? "⚠️ Needs Watering!" : "✅ Recently Watered",
            plant.getCareNotes() != null && !plant.getCareNotes().trim().isEmpty() ?
                String.format("""
                    <div class="care-notes">
                        <h3>📝 Care Notes</h3>
                        <p>%s</p>
                    </div>
                    """, plant.getCareNotes()) : "",
            java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' h:mm a"))
        );
    }
    
    private String capitalizeFirst(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    
    private String formatWateringFrequency(String frequency) {
        if (frequency == null) return "Unknown";
        switch (frequency.toLowerCase()) {
            case "daily": return "Daily";
            case "every-2-days": return "Every 2 days";
            case "weekly": return "Weekly";
            case "every-2-weeks": return "Every 2 weeks";
            case "monthly": return "Monthly";
            default: return frequency;
        }
    }
    
    private String formatSunlightNeeds(String needs) {
        if (needs == null) return "Unknown";
        switch (needs.toLowerCase()) {
            case "low": return "Low light (Indirect)";
            case "medium": return "Medium light (Bright indirect)";
            case "high": return "High light (Direct sunlight)";
            default: return needs;
        }
    }
    
    private boolean isWateringDue(java.time.LocalDate nextWatering) {
        if (nextWatering == null) return false;
        return nextWatering.isBefore(java.time.LocalDate.now()) || nextWatering.isEqual(java.time.LocalDate.now());
    }
    
    public void shutdown() {
        executorService.shutdown();
    }
}