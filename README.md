# Translation API

A Spring Boot REST API containerized with Docker.

## Prerequisites

- Docker
- Docker Compose
- Java 17 or higher

## Building the Application

### Using Docker (Recommended)

1. Clone the repository
2. Run the following command to build and start the API:
   ```bash
   docker-compose up --build
   ```

### Manual Build

1. Build the application using Gradle:
   ```bash
   ./gradlew build
   ```
2. Run the application:
   ```bash
   ./gradlew bootRun
   ```

## Running the Application

The API will be available at:
- http://localhost:8080/api

## Development

The backend is a Spring Boot application using Gradle. The main package is `nl.vaguely.translation`.

## API Endpoints

- GET /api/status - Returns "Hello World" with a 200 status code

## Project Structure

```
.
├── src/                  # Spring Boot backend
│   ├── main/
│   │   └── java/        # Java source files
│   └── test/            # Test files
├── build.gradle          # Gradle build configuration
└── docker-compose.yml    # Docker configuration
```