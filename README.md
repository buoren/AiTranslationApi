# Translation API

A Spring Boot REST API with Vue.js frontend, containerized with Docker.

## Prerequisites

- Docker
- Docker Compose
- Java 17 or higher
- Node.js 16 or higher
- npm

## Building the Application

### Option 1: Using Docker (Recommended)

1. Clone the repository
2. Run the following command to build and start both the backend and frontend services:
   ```bash
   docker-compose up --build
   ```

### Option 2: Manual Build

#### Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Build the frontend:
   ```bash
   npm run build
   ```

#### Backend Setup
1. Return to the root directory:
   ```bash
   cd ..
   ```
2. Build the backend using Gradle:
   ```bash
   ./gradlew build
   ```
3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

## Running the Application

The application will be available at:
- Frontend: http://localhost:8080
- Backend API: http://localhost:8080/api

## Development

### Frontend Development
To run the frontend in development mode:
```bash
cd frontend
npm run serve
```
This will start the Vue.js development server with hot-reload enabled.

### Backend Development
The backend is a Spring Boot application using Gradle. The main package is `nl.vaguely.translation`.

## API Endpoints

- GET /api/status - Returns "Hello World" with a 200 status code

## Project Structure

```
.
├── frontend/              # Vue.js frontend
│   ├── src/              # Source files
│   ├── public/           # Static files
│   └── package.json      # Frontend dependencies
├── src/                  # Spring Boot backend
│   ├── main/
│   │   ├── java/        # Java source files
│   │   └── resources/   # Configuration files
│   └── test/            # Test files
├── build.gradle          # Gradle build configuration
└── docker-compose.yml    # Docker configuration
```