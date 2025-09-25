# Home Care Plants API Documentation

## Base URL
```
http://localhost:8080/api
```

## Authentication
No authentication required for this API.

## Content Type
All requests and responses use `application/json`.

## Endpoints

### 1. Get All Plants
**GET** `/plants`

Returns a list of all plants in the database.

**Response:**
```json
[
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
```

### 2. Get Plant by ID
**GET** `/plants/{id}`

Returns a specific plant by its ID.

**Parameters:**
- `id` (path): Plant ID

**Response:**
```json
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
```

### 3. Create Plant
**POST** `/plants`

Creates a new plant.

**Request Body:**
```json
{
  "name": "Snake Plant",
  "type": "succulent",
  "wateringFrequency": "every-2-weeks",
  "sunlightNeeds": "low",
  "careNotes": "Very low maintenance plant",
  "imageUrl": "https://example.com/snake-plant.jpg"
}
```

**Required Fields:**
- `name`: Plant name (max 100 characters)
- `type`: Plant type (max 50 characters)
- `wateringFrequency`: Watering frequency (max 50 characters)
- `sunlightNeeds`: Sunlight requirements (max 50 characters)

**Optional Fields:**
- `careNotes`: Care instructions (max 1000 characters)
- `imageUrl`: Image URL (max 500 characters)

**Response:**
```json
{
  "id": 2,
  "name": "Snake Plant",
  "type": "succulent",
  "wateringFrequency": "every-2-weeks",
  "sunlightNeeds": "low",
  "careNotes": "Very low maintenance plant",
  "imageUrl": "https://example.com/snake-plant.jpg",
  "lastWatered": "2024-01-20",
  "nextWatering": "2024-02-03",
  "addedDate": "2024-01-20",
  "createdAt": "2024-01-20T10:00:00",
  "updatedAt": "2024-01-20T10:00:00"
}
```

### 4. Update Plant
**PUT** `/plants/{id}`

Updates an existing plant.

**Parameters:**
- `id` (path): Plant ID

**Request Body:**
```json
{
  "name": "Updated Snake Plant",
  "type": "succulent",
  "wateringFrequency": "monthly",
  "sunlightNeeds": "low",
  "careNotes": "Updated care instructions"
}
```

**Response:**
```json
{
  "id": 2,
  "name": "Updated Snake Plant",
  "type": "succulent",
  "wateringFrequency": "monthly",
  "sunlightNeeds": "low",
  "careNotes": "Updated care instructions",
  "imageUrl": "https://example.com/snake-plant.jpg",
  "lastWatered": "2024-01-15",
  "nextWatering": "2024-02-15",
  "addedDate": "2024-01-01",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-20T11:00:00"
}
```

### 5. Delete Plant
**DELETE** `/plants/{id}`

Deletes a plant by ID.

**Parameters:**
- `id` (path): Plant ID

**Response:**
- Status: `204 No Content`

### 6. Water Plant
**POST** `/plants/{id}/water`

Marks a plant as watered and updates the next watering date.

**Parameters:**
- `id` (path): Plant ID

**Response:**
```json
{
  "id": 1,
  "name": "Fiddle Leaf Fig",
  "type": "tropical",
  "wateringFrequency": "weekly",
  "sunlightNeeds": "medium",
  "careNotes": "Keep away from direct sunlight",
  "imageUrl": "https://example.com/image.jpg",
  "lastWatered": "2024-01-20",
  "nextWatering": "2024-01-27",
  "addedDate": "2024-01-01",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-20T12:00:00"
}
```

### 7. Get Plants by Type
**GET** `/plants/type/{type}`

Returns all plants of a specific type.

**Parameters:**
- `type` (path): Plant type (e.g., "tropical", "succulent", "flowering")

**Response:**
```json
[
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
```

### 8. Get Plants Needing Water
**GET** `/plants/needs-watering`

Returns all plants that need watering (next watering date is today or past).

**Response:**
```json
[
  {
    "id": 1,
    "name": "Fiddle Leaf Fig",
    "type": "tropical",
    "wateringFrequency": "weekly",
    "sunlightNeeds": "medium",
    "careNotes": "Keep away from direct sunlight",
    "imageUrl": "https://example.com/image.jpg",
    "lastWatered": "2024-01-15",
    "nextWatering": "2024-01-20",
    "addedDate": "2024-01-01",
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  }
]
```

### 9. Search Plants
**GET** `/plants/search?name={name}`

Searches for plants by name (case-insensitive).

**Parameters:**
- `name` (query): Search term

**Response:**
```json
[
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
```

### 10. Get Plant Statistics
**GET** `/plants/stats`

Returns plant statistics.

**Response:**
```json
{
  "totalPlants": 15,
  "plantsNeedingWatering": 3
}
```

## Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2024-01-20T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/plants"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-01-20T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Plant not found",
  "path": "/api/plants/999"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-01-20T10:00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/api/plants"
}
```

## Plant Types
- `tropical`
- `succulent`
- `flowering`
- `herb`
- `fern`
- `cactus`

## Watering Frequencies
- `daily`
- `every-2-days`
- `weekly`
- `every-2-weeks`
- `monthly`

## Sunlight Needs
- `low` (Indirect light)
- `medium` (Bright indirect)
- `high` (Direct sunlight)

## Example Usage with JavaScript

```javascript
// Get all plants
fetch('http://localhost:8080/api/plants')
  .then(response => response.json())
  .then(plants => console.log(plants));

// Create a new plant
fetch('http://localhost:8080/api/plants', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    name: 'Monstera',
    type: 'tropical',
    wateringFrequency: 'weekly',
    sunlightNeeds: 'medium',
    careNotes: 'Loves humidity'
  })
})
.then(response => response.json())
.then(plant => console.log('Created:', plant));

// Water a plant
fetch('http://localhost:8080/api/plants/1/water', {
  method: 'POST'
})
.then(response => response.json())
.then(plant => console.log('Watered:', plant));
```
