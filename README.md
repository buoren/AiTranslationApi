# Translation API

A Spring Boot REST API with Vue.js frontend, containerized with Docker.

## Prerequisites

- Docker
- Docker Compose

## Running the Application

1. Clone the repository
2. Run the following command to start both the backend and frontend services:
   ```bash
   docker-compose up --build
   ```

The application will be available at:
- Frontend: http://localhost
- Backend API: http://localhost:8080

## API Endpoints

- GET /status - Returns "Hello World" with a 200 status code

## Development

### Backend
The backend is a Spring Boot application using Gradle. The main package is `nl.vaguely.translation`.

### Frontend
The frontend is a Vue.js application. To develop the frontend:

1. Navigate to the frontend directory
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run serve
   ```