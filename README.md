# Translation API

A Spring Boot REST API for managing translations with OpenAI integration.

## Features

- RESTful API for translation management
- OpenAI integration for text translation
- PostgreSQL database with Liquibase for schema management
- Docker support for easy deployment
- Comprehensive test coverage

## Prerequisites

- Java 17
- Docker and Docker Compose
- OpenAI API key (set as environment variable `OPENAI_API_KEY`)

## Building

```bash
./gradlew clean build
```

## Running Tests

```bash
./gradlew test
```

## Running the Application

### Command Line

1. Set up environment variables:
```bash
export OPENAI_API_KEY=your-api-key-here
export SPRING_PROFILES_ACTIVE=local
```

2. Start PostgreSQL (if not using Docker):
```bash
# On macOS
brew services start postgresql

# On Linux
sudo service postgresql start
```

3. Run the application:
```bash
./gradlew bootRun
```

The API will be available at http://localhost:8080/api/translations

### Docker

```bash
docker-compose up
```

## API Endpoints

### Translations

- `GET /api/translations` - Get all translations
- `GET /api/translations/{id}` - Get translation by ID
- `POST /api/translations` - Create a new translation
- `PUT /api/translations/{id}` - Update a translation
- `DELETE /api/translations/{id}` - Delete a translation
- `POST /api/translations/translate` - Translate text using OpenAI

### Translation Request Format

```json
{
    "sourceText": "Hello",
    "sourceContext": "Greeting",
    "sourceLanguage": "en",
    "targetLanguage": "nl"
}
```

## Environment Configuration

### Local Development

Create `application-local.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/translation_db
spring.datasource.username=postgres
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Liquibase Configuration
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml
spring.liquibase.contexts=local
spring.liquibase.default-schema=public
spring.liquibase.drop-first=false
spring.liquibase.enabled=true

# OpenAI Configuration
openai.api.model=gpt-3.5-turbo
openai.api.temperature=0.7
openai.api.max-tokens=1000
```

### Production

Create `application-prod.properties`:
```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=none
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml

# OpenAI Configuration
openai.api.model=${OPENAI_MODEL}
openai.api.temperature=${OPENAI_TEMPERATURE}
openai.api.max-tokens=${OPENAI_MAX_TOKENS}
```

## Testing

The project includes:
- Unit tests for services
- Integration tests for controllers
- Mock tests for external services

Run tests with:
```bash
./gradlew test
```

## License

MIT

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