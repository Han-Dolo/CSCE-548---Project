# CSCE 548 Project 4 Deployment and System Test

## Project Summary
This repository contains a four-layer campus event RSVP application:
1. Data layer: MySQL schema plus DAO classes.
2. Business layer: validation and orchestration in `BusinessManager`.
3. Service layer: Java service classes exposed through Spring Boot REST controllers.
4. Client layer: browser frontend hosted from Spring Boot static resources.

The system manages four entity types:
- Venues
- Attendees
- Events
- RSVPs

## Prerequisites
- macOS, Windows, or Linux
- Java Development Kit 17
- Maven 3.9 or newer
- MySQL Server 8 or newer
- GitHub access to download the repository ZIP or clone the repository
- Optional IDE: IntelliJ IDEA, Eclipse, or Visual Studio Code with Java extensions

## Download and Open the Project
1. Open the GitHub repository.
2. Choose `Code` then `Download ZIP`, or clone the repository with Git.
3. Unpack the ZIP to a local folder.
4. Open the folder in your IDE or terminal.
5. Confirm the project root contains `pom.xml`, `sql/`, and `src/`.

## Database Setup
1. Start MySQL Server.
2. Open a terminal in the project root.
3. Create the database and tables:

```bash
mysql -u root -p < sql/schema.sql
```

4. Load sample data:

```bash
mysql -u root -p < sql/seed.sql
```

5. Confirm the database exists:

```sql
SHOW DATABASES;
USE campus_rsvp;
SHOW TABLES;
SELECT COUNT(*) FROM venues;
SELECT COUNT(*) FROM attendees;
SELECT COUNT(*) FROM events;
SELECT COUNT(*) FROM rsvps;
```

Expected tables:
- `venues`
- `attendees`
- `events`
- `rsvps`

## Application Configuration
The project reads database settings from environment variables. If they are not supplied, the project defaults to a local MySQL installation:
- `DB_URL=jdbc:mysql://localhost:3306/campus_rsvp`
- `DB_USER=root`
- `DB_PASSWORD=` empty string by default

Example configuration:

```bash
export DB_URL=jdbc:mysql://localhost:3306/campus_rsvp
export DB_USER=root
export DB_PASSWORD=your_password
```

On Windows PowerShell:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/campus_rsvp"
$env:DB_USER="root"
$env:DB_PASSWORD="your_password"
```

## Build Instructions
1. Verify Java is available:

```bash
java -version
```

2. Verify Maven is available:

```bash
mvn -version
```

3. Build the project:

```bash
mvn -q -DskipTests package
```

Successful build output creates:
- `target/campus-event-rsvp-tracker-1.0.0.jar`

## Hosting the Back End
The service layer, business layer, and data layer are hosted together through the Spring Boot application.

1. Start the application:

```bash
mvn -q spring-boot:run -Dspring-boot.run.mainClass="com.campus.rsvp.api.RsvpApiApplication"
```

2. Keep the terminal running.
3. The service should be available at:
- `http://localhost:8080`

4. Verify the API by opening:
- `http://localhost:8080/api/events`

If successful, the browser should display JSON event records.

## Hosting the Front End
The client layer is hosted by Spring Boot from:
- `src/main/resources/static/index.html`

After the backend starts, open:
- `http://localhost:8080/`

If setup succeeded, the page should show:
- A connection panel
- Venue, attendee, event, and RSVP sections
- Buttons and forms for get, create, update, and delete workflows

## API Usage Summary
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

Note: `POST` is used as create/update. Use id `0` for insert and an existing id for update.

## Full System Test Procedure
The following pdf shows a full system test showing I can insert, get, update and delete: [docs/screenshots/FullSystemTest.pdf
](https://github.com/Han-Dolo/CSCE-548---Project/blob/main/docs/screenshots/FullSystemTest.pdf)

## Success Criteria
Deployment is successful if:
- MySQL scripts run without errors.
- Maven package succeeds.
- Spring Boot starts without errors.
- `http://localhost:8080/` loads.
- CRUD operations succeed from the frontend.
- Database queries match client results.

## AI-Generated Development Record
This section should document the AI-assisted development process. Because exact historical prompt logs are not stored in the repository, the prompts below are best treated as reconstructed examples based on the generated structure. Replace them with your exact prompts if you still have them.

### Reconstructed Prompt for Data Layer
"Generate Java DAO classes and MySQL schema for a campus event RSVP system with venues, attendees, events, and RSVPs. Include create, get by id, get all, update, and delete methods."

### Reconstructed Prompt for Business and Service Layers
"Generate Java business and service layer classes for the campus RSVP app. Add validation rules and expose methods used by a REST API."

### Reconstructed Prompt for Service/API Layer
"Generate a Spring Boot REST API for the campus RSVP project with endpoints for venues, attendees, events, and RSVPs. Use POST for create/update and DELETE for remove."

### Reconstructed Prompt for Client Layer
"Generate a simple HTML, CSS, and JavaScript client for a Spring Boot API that supports get all, get by id, create, update, delete, and RSVP lookup by event id."

## Manual Changes Made to Generated Output
- Updated database configuration to use environment variables instead of only hardcoded local defaults.
- Refreshed assignment-facing wording so the frontend and README reflect Project 4.
- Added a combined deployment and system test document aligned to the current repository structure.
- Preserved the existing CRUD architecture and validation rules already present in the codebase.

## AI Tool Effectiveness Analysis
- AI was effective at generating the overall n-tier structure quickly.
- The generated project already included CRUD operations, validation flow, and a usable frontend.
- The main weaknesses were deliverable quality and deployment clarity rather than missing application structure.
- The repository still required manual cleanup to make configuration, documentation, and submission artifacts realistic for another developer or grader.

## Current Verification Status in This Workspace
- Maven package was executed successfully.
- A live end-to-end system test was not completed in this workspace because the local MySQL service was not running at the time of verification.
- The screenshot steps above should be completed after starting MySQL and running the application locally.

## Files Referenced
- `README.md`
- `sql/schema.sql`
- `sql/seed.sql`
- `src/main/java/com/campus/rsvp/db/ConnectionManager.java`
- `src/main/java/com/campus/rsvp/api/RsvpApiApplication.java`
- `src/main/resources/static/index.html`
- `src/main/resources/static/app.js`
