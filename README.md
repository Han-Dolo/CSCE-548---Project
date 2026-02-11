# Campus Event RSVP Tracker (Console, Java)

This project includes:
- SQL scripts to create a 4-table database and seed it with test data.
- Java data access layer with full CRUD for each table.
- Console UI to retrieve and manage records.

## Database setup (MySQL)
1. Start your MySQL server.
2. Run the schema script:
   - `sql/schema.sql`
3. Run the seed script:
   - `sql/seed.sql`

## Java setup
Update the connection details in:
- `src/main/java/com/campus/rsvp/db/ConnectionManager.java`

## Build and run (Maven)
1. `mvn -q -e -DskipTests package`
2. `mvn -q exec:java -Dexec.mainClass="com.campus.rsvp.Main"`

If you don't have the Maven exec plugin, you can run the jar with:
- `java -cp target/campus-event-rsvp-tracker-1.0.0.jar:~/.m2/repository/com/mysql/mysql-connector-j/9.2.0/mysql-connector-j-9.2.0.jar com.campus.rsvp.Main`

## Service layer (Spring Boot REST API)
Run the API:
1. `mvn -q -DskipTests package`
2. `mvn -q spring-boot:run -Dspring-boot.run.mainClass="com.campus.rsvp.api.RsvpApiApplication"`

Base URL: `http://localhost:8080`

Endpoints:
1. `GET /api/events`
2. `GET /api/events/{id}`
3. `POST /api/events`
4. `DELETE /api/events/{id}`
5. `GET /api/venues`
6. `GET /api/venues/{id}`
7. `POST /api/venues`
8. `DELETE /api/venues/{id}`
9. `GET /api/attendees`
10. `GET /api/attendees/{id}`
11. `POST /api/attendees`
12. `DELETE /api/attendees/{id}`
13. `GET /api/rsvps`
14. `GET /api/rsvps/{id}`
15. `GET /api/rsvps/event/{eventId}`
16. `POST /api/rsvps`
17. `DELETE /api/rsvps/{id}`

Sample JSON (Event):
```json
{
  "eventId": 0,
  "title": "Campus Career Fair",
  "description": "Meet employers and network.",
  "eventDate": "2026-03-20",
  "startTime": "10:00:00",
  "endTime": "14:00:00",
  "venueId": 1,
  "organizer": "Career Services",
  "category": "Career"
}
```

## Console API tester
Run the Spring Boot API first, then run:
1. `mvn -q exec:java@run-api-tester`

This script will:
1. Create a venue, attendee, event, and RSVP
2. Update the event and RSVP
3. List all records for each table
4. Delete the records it created

## Hosting (local laptop)
1. Start MySQL and load `sql/schema.sql` and `sql/seed.sql`.
2. Start the API using the `spring-boot:run` command above.
3. Keep the terminal open so the service stays running.
4. Your service is hosted locally at `http://localhost:8080`.

If you want to access it from another device on the same network:
1. Find your laptop IP address (for example, `192.168.x.x`).
2. Open your firewall for port `8080` (if needed).
3. Access `http://<your-ip>:8080/api/events` from the other device.

## Architecture diagram
- `docs/architecture.md`

## Testing results (console)
The console tester successfully ran create, update, list, and delete operations
against the REST API.
