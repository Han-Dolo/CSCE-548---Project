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
The following test sequence satisfies the Project 4 requirement to verify the application through the client and database.

### Test 1: Launch Verification
1. Start MySQL.
2. Start the Spring Boot application.
3. Open `http://localhost:8080/`.
4. Click `Check Connection`.

Evidence to capture:
- Screenshot of the web client showing a successful connection message.

### Test 2: Create a Venue
1. In the Venues form, leave `venueId` as `0`.
2. Enter a unique venue name and details.
3. Click `Save Venue`.
4. Query MySQL:

```sql
SELECT * FROM venues ORDER BY venue_id DESC LIMIT 1;
```

Evidence to capture:
- Screenshot of the client success message showing the new venue id.
- Screenshot of the database row returned by MySQL.

### Test 3: Create an Attendee
1. In the Attendees form, leave `attendeeId` as `0`.
2. Enter a unique email address.
3. Click `Save Attendee`.
4. Query MySQL:

```sql
SELECT * FROM attendees ORDER BY attendee_id DESC LIMIT 1;
```

Evidence to capture:
- Screenshot of the client success message.
- Screenshot of the inserted database row.

### Test 4: Create an Event
1. In the Events form, leave `eventId` as `0`.
2. Use the new venue id from Test 2.
3. Click `Save Event`.
4. Query MySQL:

```sql
SELECT * FROM events ORDER BY event_id DESC LIMIT 1;
```

Evidence to capture:
- Screenshot of the client success message.
- Screenshot of the inserted event row.

### Test 5: Create an RSVP
1. In the RSVPs form, leave `rsvpId` as `0`.
2. Use the new event id and attendee id from Tests 3 and 4.
3. Click `Save RSVP`.
4. Query MySQL:

```sql
SELECT * FROM rsvps ORDER BY rsvp_id DESC LIMIT 1;
```

Evidence to capture:
- Screenshot of the client success message.
- Screenshot of the inserted RSVP row.

### Test 6: Get All Records
1. Use `Show All Venues`, `Show All Attendees`, `Show All Events`, and `Show All RSVPs`.
2. Compare the client data with database queries:

```sql
SELECT * FROM venues;
SELECT * FROM attendees;
SELECT * FROM events;
SELECT * FROM rsvps;
```

Evidence to capture:
- Screenshot of each client list view.
- Screenshot of each matching database query result.

### Test 7: Get One Record by ID
1. Use `Find Venue by ID`, `Find Attendee by ID`, `Find Event by ID`, and `Find RSVP by ID`.
2. Query matching database rows:

```sql
SELECT * FROM venues WHERE venue_id = ?;
SELECT * FROM attendees WHERE attendee_id = ?;
SELECT * FROM events WHERE event_id = ?;
SELECT * FROM rsvps WHERE rsvp_id = ?;
```

Evidence to capture:
- Screenshot of each client single-record result.
- Screenshot of each matching database query result.

### Test 8: Update Records
1. Reuse the ids created earlier.
2. Enter the existing id into a form.
3. Change one or more field values.
4. Click the matching `Save` button.
5. Query the updated row in MySQL.

Evidence to capture:
- Screenshot of the client success response after update.
- Screenshot of the updated database row.

### Test 9: RSVP Subset Query
1. Enter an event id into `Show RSVPs by Event ID`.
2. Compare results with:

```sql
SELECT * FROM rsvps WHERE event_id = ?;
```

Evidence to capture:
- Screenshot of the client subset result.
- Screenshot of the database query result.

### Test 10: Delete Records
Delete in this order to avoid foreign key conflicts:
1. Delete the test RSVP.
2. Delete the test Event.
3. Delete the test Attendee.
4. Delete the test Venue.

Verify each deletion:

```sql
SELECT * FROM rsvps WHERE rsvp_id = ?;
SELECT * FROM events WHERE event_id = ?;
SELECT * FROM attendees WHERE attendee_id = ?;
SELECT * FROM venues WHERE venue_id = ?;
```

Evidence to capture:
- Screenshot of the client success response for each delete.
- Screenshot of each query returning no matching row.

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
