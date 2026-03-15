# Campus Event RSVP Tracker

Campus Event RSVP Tracker is a Java n-tier application for managing campus venues, events, attendees, and RSVPs. The project includes a MySQL data layer, Java DAO/business/service layers, a Spring Boot REST API, and a browser client served from the same application.

This repository is organized for CSCE 548 Project 4 and includes:
- Full CRUD support across venues, attendees, events, and RSVPs.
- A hosted service layer in Spring Boot under `http://localhost:8080`.
- A client layer served from Spring Boot static assets.
- SQL scripts for schema creation and seed data.
- A combined deployment and system test document for submission.

## Tech Stack
- Java 17
- Maven
- Spring Boot 3.3.2
- MySQL 8+
- HTML, CSS, and vanilla JavaScript

## Repository Layout
- `src/main/java/com/campus/rsvp/dao`: data access layer
- `src/main/java/com/campus/rsvp/business`: business layer
- `src/main/java/com/campus/rsvp/service`: service layer used by the controllers
- `src/main/java/com/campus/rsvp/api`: REST API and error handling
- `src/main/resources/static`: hosted frontend
- `sql/schema.sql`: database creation script
- `sql/seed.sql`: sample data
- `Project4_Deployment_and_System_Test.md`: full deployment and verification guide

## Prerequisites
- JDK 17
- Maven 3.9+
- MySQL Server running locally or reachable by connection string
- Optional: IntelliJ IDEA or VS Code with Java support

## Database Configuration
The application reads database settings from environment variables:
- `DB_URL` default: `jdbc:mysql://localhost:3306/campus_rsvp`
- `DB_USER` default: `root`
- `DB_PASSWORD` default: empty string

Example:

```bash
export DB_URL=jdbc:mysql://localhost:3306/campus_rsvp
export DB_USER=root
export DB_PASSWORD=your_password
```

## Setup and Run
1. Start MySQL.
2. Load the schema:
   - `mysql -u root -p < sql/schema.sql`
3. Load seed data:
   - `mysql -u root -p < sql/seed.sql`
4. Build the project:
   - `mvn -q -DskipTests package`
5. Start the API and frontend host:
   - `mvn -q spring-boot:run -Dspring-boot.run.mainClass="com.campus.rsvp.api.RsvpApiApplication"`
6. Open the web client:
   - `http://localhost:8080/`

## API Endpoints
### Venues
- `GET /api/venues`
- `GET /api/venues/{id}`
- `POST /api/venues`
- `DELETE /api/venues/{id}`

### Attendees
- `GET /api/attendees`
- `GET /api/attendees/{id}`
- `POST /api/attendees`
- `DELETE /api/attendees/{id}`

### Events
- `GET /api/events`
- `GET /api/events/{id}`
- `POST /api/events`
- `DELETE /api/events/{id}`

### RSVPs
- `GET /api/rsvps`
- `GET /api/rsvps/{id}`
- `GET /api/rsvps/event/{eventId}`
- `POST /api/rsvps`
- `DELETE /api/rsvps/{id}`

`POST` acts as an upsert in this project: submit `id = 0` to create or an existing id to update.

## Frontend Capabilities
The hosted web client supports:
- Connection check
- Get all records
- Get one record by id
- Create records
- Update records
- Delete records
- Query RSVPs by event id

## Verification
Successful setup is indicated by:
- `mvn -q -DskipTests package` completes without errors.
- `http://localhost:8080/` loads the client UI.
- `http://localhost:8080/api/events` returns JSON.
- CRUD actions in the frontend return success and are reflected in the database.

## Submission Artifacts
- Full instructions and system test write-up: `Project4_Deployment_and_System_Test.md`
- PDF submission artifact: `Project4_Deployment_and_System_Test.pdf`
- Schema and seed scripts: `sql/schema.sql`, `sql/seed.sql`

## Current Verification Status
The project was built and tested successfully. Full deployment instructions and system test evidence are included in `Project4_Deployment_and_System_Test.pdf` or `Project4_Deployment_and_System_Test.md` .
