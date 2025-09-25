# Home Care Plants - Spring Boot Backend

A RESTful API backend for the Home Care Plants application built with Spring Boot, JPA, and PostgreSQL.

## Features

- **Plant Management**: Full CRUD operations for plant records
- **Database Integration**: PostgreSQL with JPA/Hibernate
- **RESTful APIs**: JSON-based API endpoints
- **CORS Support**: Configured for frontend integration
- **Validation**: Input validation using Bean Validation
- **Search & Filter**: Advanced query capabilities

## Prerequisites

Before running the application, ensure you have:

- **Java 17** or higher
- **Maven 3.6+**
- **PostgreSQL 12+** running on port 5433
- **Database**: `Home_care_plants` created

## Database Setup

1. **Install PostgreSQL** (if not already installed)
2. **Create Database**:
   ```sql
   CREATE DATABASE "Home_care_plants";
   ```
3. **Update Configuration** (if needed):
   - Edit `src/main/resources/application.properties`
   - Update database URL, username, and password as needed

## Project Structure

```
src/
├── main/
│   ├── java/com/homecare/
│   │   ├── HomeCarePlantsApplication.java    # Main application class
│   │   ├── config/
│   │   │   └── CorsConfig.java              # CORS configuration
│   │   ├── controller/
│   │   │   └── PlantController.java         # REST API endpoints
│   │   ├── entity/
│   │   │   └── Plant.java                   # Plant entity model
│   │   └── repository/
│   │       └── PlantRepository.java         # Data access layer
│   └── resources/
│       └── application.properties           # Application configuration
└── pom.xml                                  # Maven dependencies
```

## API Endpoints

### Plant Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/plants` | Get all plants |
| GET | `/api/plants/{id}` | Get plant by ID |
| POST | `/api/plants` | Create new plant |
| PUT | `/api/plants/{id}` | Update plant |
| DELETE | `/api/plants/{id}` | Delete plant |

### Plant Actions

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/plants/{id}/water` | Mark plant as watered |

### Search & Filter

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/plants/type/{type}` | Get plants by type |
| GET | `/api/plants/needs-watering` | Get plants needing water |
| GET | `/api/plants/search?name={name}` | Search plants by name |
| GET | `/api/plants/stats` | Get plant statistics |

## Plant Entity Fields

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

## Running the Application

### Method 1: Using Maven

1. **Navigate to project directory**:
   ```bash
   cd home-care-plants-backend
   ```

2. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

### Method 2: Using IDE

1. **Import project** into your IDE (IntelliJ IDEA, Eclipse, VS Code)
2. **Run** `HomeCarePlantsApplication.java`

### Method 3: Build and Run JAR

1. **Build the project**:
   ```bash
   mvn clean package
   ```

2. **Run the JAR file**:
   ```bash
   java -jar target/home-care-plants-backend-0.0.1-SNAPSHOT.jar
   ```

## Application Configuration

The application runs on **port 8080** by default. You can change this in `application.properties`:

```properties
server.port=8080
```

## CORS Configuration

The application is configured to accept requests from:
- `http://localhost:3000` (React development server)
- `http://127.0.0.1:3000`
- `http://localhost:5500` (Live Server)
- `http://127.0.0.1:5500`
- `file://` (Local file system)

## Testing the API

### Using curl

1. **Get all plants**:
   ```bash
   curl -X GET http://localhost:8080/api/plants
   ```

2. **Create a new plant**:
   ```bash
   curl -X POST http://localhost:8080/api/plants \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Snake Plant",
       "type": "succulent",
       "wateringFrequency": "every-2-weeks",
       "sunlightNeeds": "low",
       "careNotes": "Very low maintenance plant"
     }'
   ```

3. **Water a plant**:
   ```bash
   curl -X POST http://localhost:8080/api/plants/1/water
   ```

### Using Postman

1. **Import the collection** (if available)
2. **Set base URL** to `http://localhost:8080`
3. **Test endpoints** using the provided examples

## Frontend Integration

To integrate with your existing frontend:

1. **Update API calls** in `script.js` to use the backend:
   ```javascript
   // Replace local data with API calls
   fetch('http://localhost:8080/api/plants')
     .then(response => response.json())
     .then(plants => {
       // Update UI with plants data
     });
   ```

2. **Update plant operations**:
   ```javascript
   // Create plant
   fetch('http://localhost:8080/api/plants', {
     method: 'POST',
     headers: {
       'Content-Type': 'application/json',
     },
     body: JSON.stringify(plantData)
   });
   ```

## Troubleshooting

### Common Issues

1. **Database Connection Error**:
   - Ensure PostgreSQL is running
   - Check database credentials in `application.properties`
   - Verify database `Home_care_plants` exists

2. **Port Already in Use**:
   - Change port in `application.properties`
   - Or stop the process using port 8080

3. **CORS Issues**:
   - Check if frontend URL is in allowed origins
   - Verify CORS configuration in `CorsConfig.java`

4. **Build Errors**:
   - Ensure Java 17+ is installed
   - Check Maven installation
   - Run `mvn clean install`

### Logs

Check application logs for detailed error information:
- Console output when running with `mvn spring-boot:run`
- Log files in `logs/` directory (if configured)

## Development

### Adding New Features

1. **New Entity**: Create in `entity/` package
2. **New Repository**: Create in `repository/` package
3. **New Controller**: Create in `controller/` package
4. **New Configuration**: Create in `config/` package

### Database Migrations

The application uses `spring.jpa.hibernate.ddl-auto=update` which automatically updates the database schema. For production, consider using Flyway or Liquibase for proper migration management.

## Production Deployment

For production deployment:

1. **Change database configuration** to production database
2. **Set `spring.jpa.hibernate.ddl-auto=validate`**
3. **Configure proper logging**
4. **Set up monitoring and health checks**
5. **Use environment variables** for sensitive configuration

## License

This project is part of the Home Care Plants application.