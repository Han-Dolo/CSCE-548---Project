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

