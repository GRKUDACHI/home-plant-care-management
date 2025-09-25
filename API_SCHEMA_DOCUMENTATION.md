# Home Care Plants API - Complete Schema Documentation

## Overview
This document provides comprehensive schema documentation for the Home Care Plants REST API, including data models, request/response schemas, and validation rules.

---

## Table of Contents
1. [Data Models](#data-models)
2. [API Endpoints Schema](#api-endpoints-schema)
3. [Request/Response Schemas](#requestresponse-schemas)
4. [Validation Rules](#validation-rules)
5. [Error Schemas](#error-schemas)
6. [Database Schema](#database-schema)

---

## Data Models

### Plant Entity Schema

```json
{
  "Plant": {
    "type": "object",
    "properties": {
      "id": {
        "type": "integer",
        "format": "int64",
        "description": "Auto-generated unique identifier",
        "example": 1
      },
      "name": {
        "type": "string",
        "maxLength": 100,
        "description": "Plant name",
        "example": "Fiddle Leaf Fig",
        "required": true
      },
      "type": {
        "type": "string",
        "maxLength": 50,
        "description": "Plant type category",
        "enum": ["tropical", "succulent", "flowering", "herb", "fern", "cactus"],
        "example": "tropical",
        "required": true
      },
      "wateringFrequency": {
        "type": "string",
        "maxLength": 50,
        "description": "How often the plant needs watering",
        "enum": ["daily", "every-2-days", "weekly", "every-2-weeks", "monthly"],
        "example": "weekly",
        "required": true
      },
      "sunlightNeeds": {
        "type": "string",
        "maxLength": 50,
        "description": "Sunlight requirements",
        "enum": ["low", "medium", "high"],
        "example": "medium",
        "required": true
      },
      "careNotes": {
        "type": "string",
        "maxLength": 1000,
        "description": "Additional care instructions",
        "example": "Keep away from direct sunlight. Water when top inch of soil is dry.",
        "required": false
      },
      "imageUrl": {
        "type": "string",
        "maxLength": 500,
        "format": "uri",
        "description": "URL to plant image",
        "example": "https://example.com/plant-image.jpg",
        "required": false
      },
      "lastWatered": {
        "type": "string",
        "format": "date",
        "description": "Date when plant was last watered",
        "example": "2024-01-15",
        "required": false
      },
      "nextWatering": {
        "type": "string",
        "format": "date",
        "description": "Date when plant should be watered next",
        "example": "2024-01-22",
        "required": false
      },
      "addedDate": {
        "type": "string",
        "format": "date",
        "description": "Date when plant was added to collection",
        "example": "2024-01-01",
        "required": true
      },
      "createdAt": {
        "type": "string",
        "format": "date-time",
        "description": "Record creation timestamp",
        "example": "2024-01-01T10:00:00",
        "required": false
      },
      "updatedAt": {
        "type": "string",
        "format": "date-time",
        "description": "Record last update timestamp",
        "example": "2024-01-01T10:00:00",
        "required": false
      }
    },
    "required": ["name", "type", "wateringFrequency", "sunlightNeeds", "addedDate"]
  }
}
```

### Plant Statistics Schema

```json
{
  "PlantStats": {
    "type": "object",
    "properties": {
      "totalPlants": {
        "type": "integer",
        "format": "int64",
        "description": "Total number of plants in the collection",
        "example": 15
      },
      "plantsNeedingWatering": {
        "type": "integer",
        "format": "int64",
        "description": "Number of plants that need watering",
        "example": 3
      }
    },
    "required": ["totalPlants", "plantsNeedingWatering"]
  }
}
```

---

## API Endpoints Schema

### Base Information
- **Base URL**: `http://localhost:8080/api`
- **Content-Type**: `application/json`
- **Authentication**: None required

### Endpoint Summary

| Method | Endpoint | Description | Response Type |
|--------|----------|-------------|---------------|
| GET | `/plants` | Get all plants | Plant[] |
| GET | `/plants/{id}` | Get plant by ID | Plant |
| POST | `/plants` | Create new plant | Plant |
| PUT | `/plants/{id}` | Update plant | Plant |
| DELETE | `/plants/{id}` | Delete plant | No Content |
| POST | `/plants/{id}/water` | Water plant | Plant |
| GET | `/plants/type/{type}` | Get plants by type | Plant[] |
| GET | `/plants/needs-watering` | Get plants needing water | Plant[] |
| GET | `/plants/search?name={name}` | Search plants by name | Plant[] |
| GET | `/plants/stats` | Get plant statistics | PlantStats |

---

## Request/Response Schemas

### 1. GET /api/plants

#### Response Schema
```json
{
  "type": "array",
  "items": {
    "$ref": "#/definitions/Plant"
  },
  "example": [
    {
      "id": 1,
      "name": "Fiddle Leaf Fig",
      "type": "tropical",
      "wateringFrequency": "weekly",
      "sunlightNeeds": "medium",
      "careNotes": "Keep away from direct sunlight",
      "imageUrl": "https://example.com/image.jpg",
      "lastWatered": "2024-01-15",
      "nextWatering": "2024-01-22",
      "addedDate": "2024-01-01",
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00"
    }
  ]
}
```

### 2. GET /api/plants/{id}

#### Path Parameters
```json
{
  "id": {
    "type": "integer",
    "format": "int64",
    "description": "Plant ID",
    "required": true,
    "example": 1
  }
}
```

#### Response Schema
```json
{
  "$ref": "#/definitions/Plant"
}
```

### 3. POST /api/plants

#### Request Schema
```json
{
  "type": "object",
  "properties": {
    "name": {
      "type": "string",
      "maxLength": 100,
      "description": "Plant name",
      "example": "Snake Plant",
      "required": true
    },
    "type": {
      "type": "string",
      "enum": ["tropical", "succulent", "flowering", "herb", "fern", "cactus"],
      "description": "Plant type",
      "example": "succulent",
      "required": true
    },
    "wateringFrequency": {
      "type": "string",
      "enum": ["daily", "every-2-days", "weekly", "every-2-weeks", "monthly"],
      "description": "Watering frequency",
      "example": "every-2-weeks",
      "required": true
    },
    "sunlightNeeds": {
      "type": "string",
      "enum": ["low", "medium", "high"],
      "description": "Sunlight needs",
      "example": "low",
      "required": true
    },
    "careNotes": {
      "type": "string",
      "maxLength": 1000,
      "description": "Care instructions",
      "example": "Very low maintenance plant",
      "required": false
    },
    "imageUrl": {
      "type": "string",
      "format": "uri",
      "maxLength": 500,
      "description": "Image URL",
      "example": "https://example.com/snake-plant.jpg",
      "required": false
    }
  },
  "required": ["name", "type", "wateringFrequency", "sunlightNeeds"]
}
```

#### Response Schema
```json
{
  "$ref": "#/definitions/Plant"
}
```

### 4. PUT /api/plants/{id}

#### Path Parameters
```json
{
  "id": {
    "type": "integer",
    "format": "int64",
    "description": "Plant ID",
    "required": true,
    "example": 1
  }
}
```

#### Request Schema
```json
{
  "type": "object",
  "properties": {
    "name": {
      "type": "string",
      "maxLength": 100,
      "description": "Plant name",
      "example": "Updated Snake Plant"
    },
    "type": {
      "type": "string",
      "enum": ["tropical", "succulent", "flowering", "herb", "fern", "cactus"],
      "description": "Plant type",
      "example": "succulent"
    },
    "wateringFrequency": {
      "type": "string",
      "enum": ["daily", "every-2-days", "weekly", "every-2-weeks", "monthly"],
      "description": "Watering frequency",
      "example": "monthly"
    },
    "sunlightNeeds": {
      "type": "string",
      "enum": ["low", "medium", "high"],
      "description": "Sunlight needs",
      "example": "low"
    },
    "careNotes": {
      "type": "string",
      "maxLength": 1000,
      "description": "Care instructions",
      "example": "Updated care instructions"
    },
    "imageUrl": {
      "type": "string",
      "format": "uri",
      "maxLength": 500,
      "description": "Image URL",
      "example": "https://example.com/updated-image.jpg"
    }
  }
}
```

#### Response Schema
```json
{
  "$ref": "#/definitions/Plant"
}
```

### 5. DELETE /api/plants/{id}

#### Path Parameters
```json
{
  "id": {
    "type": "integer",
    "format": "int64",
    "description": "Plant ID",
    "required": true,
    "example": 1
  }
}
```

#### Response
- **Status Code**: 204 No Content
- **Body**: Empty

### 6. POST /api/plants/{id}/water

#### Path Parameters
```json
{
  "id": {
    "type": "integer",
    "format": "int64",
    "description": "Plant ID",
    "required": true,
    "example": 1
  }
}
```

#### Response Schema
```json
{
  "$ref": "#/definitions/Plant"
}
```

### 7. GET /api/plants/type/{type}

#### Path Parameters
```json
{
  "type": {
    "type": "string",
    "enum": ["tropical", "succulent", "flowering", "herb", "fern", "cactus"],
    "description": "Plant type",
    "required": true,
    "example": "tropical"
  }
}
```

#### Response Schema
```json
{
  "type": "array",
  "items": {
    "$ref": "#/definitions/Plant"
  }
}
```

### 8. GET /api/plants/needs-watering

#### Response Schema
```json
{
  "type": "array",
  "items": {
    "$ref": "#/definitions/Plant"
  }
}
```

### 9. GET /api/plants/search

#### Query Parameters
```json
{
  "name": {
    "type": "string",
    "description": "Search term for plant name",
    "required": true,
    "example": "snake"
  }
}
```

#### Response Schema
```json
{
  "type": "array",
  "items": {
    "$ref": "#/definitions/Plant"
  }
}
```

### 10. GET /api/plants/stats

#### Response Schema
```json
{
  "$ref": "#/definitions/PlantStats"
}
```

---

## Validation Rules

### Plant Entity Validation

| Field | Validation Rules | Error Messages |
|-------|------------------|----------------|
| `name` | Required, Max 100 characters | "Plant name is required", "Plant name must not exceed 100 characters" |
| `type` | Required, Max 50 characters, Must be valid enum | "Plant type is required", "Plant type must not exceed 50 characters" |
| `wateringFrequency` | Required, Max 50 characters, Must be valid enum | "Watering frequency is required", "Watering frequency must not exceed 50 characters" |
| `sunlightNeeds` | Required, Max 50 characters, Must be valid enum | "Sunlight needs is required", "Sunlight needs must not exceed 50 characters" |
| `careNotes` | Optional, Max 1000 characters | "Care notes must not exceed 1000 characters" |
| `imageUrl` | Optional, Max 500 characters, Valid URI format | "Image URL must not exceed 500 characters" |

### Enum Values

#### Plant Types
- `tropical`
- `succulent`
- `flowering`
- `herb`
- `fern`
- `cactus`

#### Watering Frequencies
- `daily`
- `every-2-days`
- `weekly`
- `every-2-weeks`
- `monthly`

#### Sunlight Needs
- `low` (Indirect light)
- `medium` (Bright indirect)
- `high` (Direct sunlight)

---

## Error Schemas

### 400 Bad Request
```json
{
  "type": "object",
  "properties": {
    "timestamp": {
      "type": "string",
      "format": "date-time",
      "description": "Error timestamp",
      "example": "2024-01-20T10:00:00"
    },
    "status": {
      "type": "integer",
      "description": "HTTP status code",
      "example": 400
    },
    "error": {
      "type": "string",
      "description": "Error type",
      "example": "Bad Request"
    },
    "message": {
      "type": "string",
      "description": "Error message",
      "example": "Validation failed"
    },
    "path": {
      "type": "string",
      "description": "Request path",
      "example": "/api/plants"
    }
  }
}
```

### 404 Not Found
```json
{
  "type": "object",
  "properties": {
    "timestamp": {
      "type": "string",
      "format": "date-time",
      "description": "Error timestamp",
      "example": "2024-01-20T10:00:00"
    },
    "status": {
      "type": "integer",
      "description": "HTTP status code",
      "example": 404
    },
    "error": {
      "type": "string",
      "description": "Error type",
      "example": "Not Found"
    },
    "message": {
      "type": "string",
      "description": "Error message",
      "example": "Plant not found"
    },
    "path": {
      "type": "string",
      "description": "Request path",
      "example": "/api/plants/999"
    }
  }
}
```

### 500 Internal Server Error
```json
{
  "type": "object",
  "properties": {
    "timestamp": {
      "type": "string",
      "format": "date-time",
      "description": "Error timestamp",
      "example": "2024-01-20T10:00:00"
    },
    "status": {
      "type": "integer",
      "description": "HTTP status code",
      "example": 500
    },
    "error": {
      "type": "string",
      "description": "Error type",
      "example": "Internal Server Error"
    },
    "message": {
      "type": "string",
      "description": "Error message",
      "example": "An unexpected error occurred"
    },
    "path": {
      "type": "string",
      "description": "Request path",
      "example": "/api/plants"
    }
  }
}
```

---

## Database Schema

### PostgreSQL Table Definition

```sql
CREATE TABLE plants (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    watering_frequency VARCHAR(50) NOT NULL,
    sunlight_needs VARCHAR(50) NOT NULL,
    care_notes TEXT,
    image_url VARCHAR(500),
    last_watered DATE,
    next_watering DATE,
    added_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

### Indexes

```sql
-- Performance indexes
CREATE INDEX idx_plants_type ON plants(type);
CREATE INDEX idx_plants_watering_frequency ON plants(watering_frequency);
CREATE INDEX idx_plants_sunlight_needs ON plants(sunlight_needs);
CREATE INDEX idx_plants_next_watering ON plants(next_watering);
CREATE INDEX idx_plants_name ON plants(name);

-- Composite indexes for common queries
CREATE INDEX idx_plants_type_sunlight ON plants(type, sunlight_needs);
CREATE INDEX idx_plants_watering_due ON plants(next_watering) WHERE next_watering <= CURRENT_DATE;
```

### Constraints

```sql
-- Check constraints for enum values
ALTER TABLE plants ADD CONSTRAINT chk_plant_type 
    CHECK (type IN ('tropical', 'succulent', 'flowering', 'herb', 'fern', 'cactus'));

ALTER TABLE plants ADD CONSTRAINT chk_watering_frequency 
    CHECK (watering_frequency IN ('daily', 'every-2-days', 'weekly', 'every-2-weeks', 'monthly'));

ALTER TABLE plants ADD CONSTRAINT chk_sunlight_needs 
    CHECK (sunlight_needs IN ('low', 'medium', 'high'));

-- Length constraints
ALTER TABLE plants ADD CONSTRAINT chk_name_length 
    CHECK (LENGTH(name) <= 100);

ALTER TABLE plants ADD CONSTRAINT chk_care_notes_length 
    CHECK (LENGTH(care_notes) <= 1000);

ALTER TABLE plants ADD CONSTRAINT chk_image_url_length 
    CHECK (LENGTH(image_url) <= 500);
```

---

## API Testing Examples

### cURL Commands

#### Get All Plants
```bash
curl -X GET "http://localhost:8080/api/plants" \
  -H "Accept: application/json"
```

#### Create Plant
```bash
curl -X POST "http://localhost:8080/api/plants" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Monstera Deliciosa",
    "type": "tropical",
    "wateringFrequency": "weekly",
    "sunlightNeeds": "medium",
    "careNotes": "Loves humidity and bright indirect light"
  }'
```

#### Update Plant
```bash
curl -X PUT "http://localhost:8080/api/plants/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Monstera",
    "type": "tropical",
    "wateringFrequency": "weekly",
    "sunlightNeeds": "medium",
    "careNotes": "Updated care instructions"
  }'
```

#### Water Plant
```bash
curl -X POST "http://localhost:8080/api/plants/1/water" \
  -H "Accept: application/json"
```

#### Search Plants
```bash
curl -X GET "http://localhost:8080/api/plants/search?name=monstera" \
  -H "Accept: application/json"
```

### JavaScript Examples

#### Fetch All Plants
```javascript
const response = await fetch('http://localhost:8080/api/plants');
const plants = await response.json();
console.log(plants);
```

#### Create Plant
```javascript
const newPlant = {
  name: "Snake Plant",
  type: "succulent",
  wateringFrequency: "every-2-weeks",
  sunlightNeeds: "low",
  careNotes: "Very low maintenance"
};

const response = await fetch('http://localhost:8080/api/plants', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify(newPlant)
});

const createdPlant = await response.json();
console.log(createdPlant);
```

#### Water Plant
```javascript
const response = await fetch('http://localhost:8080/api/plants/1/water', {
  method: 'POST'
});

const wateredPlant = await response.json();
console.log(wateredPlant);
```

---

## OpenAPI/Swagger Specification

```yaml
openapi: 3.0.0
info:
  title: Home Care Plants API
  description: RESTful API for plant management system
  version: 1.0.0
  contact:
    name: Development Team
    email: dev@homecareplants.com
servers:
  - url: http://localhost:8080/api
    description: Development server
paths:
  /plants:
    get:
      summary: Get all plants
      description: Retrieve all plants in the collection
      responses:
        '200':
          description: Successful response
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Plant'
    post:
      summary: Create new plant
      description: Add a new plant to the collection
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/PlantRequest'
      responses:
        '201':
          description: Plant created successfully
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Plant'
        '400':
          description: Bad request
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Error'
  /plants/{id}:
    get:
      summary: Get plant by ID
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '200':
          description: Successful response
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Plant'
        '404':
          description: Plant not found
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Error'
    put:
      summary: Update plant
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: integer
            format: int64
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/PlantRequest'
      responses:
        '200':
          description: Plant updated successfully
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Plant'
        '404':
          description: Plant not found
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Error'
    delete:
      summary: Delete plant
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '204':
          description: Plant deleted successfully
        '404':
          description: Plant not found
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Error'
components:
  schemas:
    Plant:
      type: object
      properties:
        id:
          type: integer
          format: int64
          description: Auto-generated unique identifier
        name:
          type: string
          maxLength: 100
          description: Plant name
        type:
          type: string
          enum: [tropical, succulent, flowering, herb, fern, cactus]
          description: Plant type
        wateringFrequency:
          type: string
          enum: [daily, every-2-days, weekly, every-2-weeks, monthly]
          description: Watering frequency
        sunlightNeeds:
          type: string
          enum: [low, medium, high]
          description: Sunlight requirements
        careNotes:
          type: string
          maxLength: 1000
          description: Care instructions
        imageUrl:
          type: string
          format: uri
          maxLength: 500
          description: Image URL
        lastWatered:
          type: string
          format: date
          description: Last watering date
        nextWatering:
          type: string
          format: date
          description: Next watering date
        addedDate:
          type: string
          format: date
          description: Date added to collection
        createdAt:
          type: string
          format: date-time
          description: Creation timestamp
        updatedAt:
          type: string
          format: date-time
          description: Last update timestamp
      required: [name, type, wateringFrequency, sunlightNeeds, addedDate]
    PlantRequest:
      type: object
      properties:
        name:
          type: string
          maxLength: 100
          description: Plant name
        type:
          type: string
          enum: [tropical, succulent, flowering, herb, fern, cactus]
          description: Plant type
        wateringFrequency:
          type: string
          enum: [daily, every-2-days, weekly, every-2-weeks, monthly]
          description: Watering frequency
        sunlightNeeds:
          type: string
          enum: [low, medium, high]
          description: Sunlight requirements
        careNotes:
          type: string
          maxLength: 1000
          description: Care instructions
        imageUrl:
          type: string
          format: uri
          maxLength: 500
          description: Image URL
      required: [name, type, wateringFrequency, sunlightNeeds]
    Error:
      type: object
      properties:
        timestamp:
          type: string
          format: date-time
          description: Error timestamp
        status:
          type: integer
          description: HTTP status code
        error:
          type: string
          description: Error type
        message:
          type: string
          description: Error message
        path:
          type: string
          description: Request path
```

---

This comprehensive schema documentation provides all the necessary information for developers to understand and integrate with the Home Care Plants API. The schemas are defined using JSON Schema format and include OpenAPI/Swagger specifications for easy integration with API documentation tools.
