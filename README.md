# TravelBuddyy-Backend

## 🌍 Overview

TravelBuddyy helps travelers choose nearby trips and itineraries tailored to their **age group** (students, adults, middle-aged, seniors, retirees) and **budget**. This repository contains the backend service for the TravelBuddyy application.

The backend provides:

1. REST endpoints for destinations and attractions filtered by age group and budget
2. Trip planning functionality that generates itineraries with cost breakdowns
3. In-memory database with sample data for immediate use
4. Swagger UI documentation for API exploration

## 🔧 Technologies

- **Java 17**
- **Spring Boot 3**
- **Spring Web** - RESTful API development
- **Spring Data JPA** - Data persistence layer
- **H2 Database** - In-memory database
- **Lombok** - Reduces boilerplate code
- **OpenAPI 3 (springdoc)** - API documentation

## 🏗️ Architecture

### Data Model

- **Entities**
  - `Destination` (id, name, country, shortDescription, typicalCost)
  - `Attraction` (id, destinationId, name, description, entryFee)
  - `AgeGroup` (enum: STUDENT, ADULT, MIDDLE_AGED, SENIOR, RETIREE)

### API Endpoints

- **Destinations**
  - `GET /api/destinations?ageGroup=STUDENT&budget=500` - List destinations filtered by age group and budget
  - `GET /api/destinations/{id}` - Get detailed information about a specific destination with its attractions

- **Itinerary Planning**
  - `POST /api/itinerary` - Generate an itinerary based on destination, age group, budget, and transport mode
    - Request body: `{destinationId, ageGroup, budget, transportMode}`
    - Response: Detailed itinerary with cost breakdown

### Data Initialization

The application includes a `DataLoader` component that seeds the database with sample destinations and attractions, including appropriate age group and cost information.

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Running Locally

```bash
# Clone the repository
git clone https://github.com/yourusername/TravelBuddyy-Backend.git
cd TravelBuddyy-Backend

# Build and run the application
./mvnw spring-boot:run
```

The application will be available at http://localhost:8080

API documentation is available at http://localhost:8080/swagger-ui.html

### Running with Docker

```bash
# Build and run the Docker container
docker build -t travelbuddy-backend .
docker run -p 8080:8080 travelbuddy-backend
```

### Running the Full Application (with Frontend)

For running both the frontend and backend together:

```bash
# Using Docker Compose (from the parent directory)
docker compose up --build
```

## 📝 TODO (Future Enhancements)

- Implement authentication and authorization (JWT/OAuth)
- Add user profiles and saved trips
- Integrate with external travel APIs for real-time data
- Implement caching for improved performance
- Add recommendation engine based on user preferences

## 📚 API Documentation

The API documentation is automatically generated using Swagger UI and can be accessed at `/swagger-ui.html` when the application is running.

## 🧪 Testing

Run the tests using Maven:

```bash
./mvnw test
```