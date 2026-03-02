# CSCE 548 Project 3 - Overview

## AI Prompt Used
Use this section to paste the exact prompt you used with ChatGPT (or another AI tool) to generate the frontend.

Example prompt:
"Generate a simple HTML/CSS/JavaScript frontend for a Spring Boot API at http://localhost:8080. Include CRUD forms and buttons for venues, attendees, events, and rsvps. Include GET all, GET by id, and RSVP by event id."

## Changes Made to Generated Output
List the manual changes you made after AI generation.

Example items:
1. Added missing endpoint support for `GET /api/rsvps/event/{eventId}`.
2. Fixed event time formatting to match API (`HH:mm:ss`).
3. Added delete inputs and output panels for each table.
4. Improved error handling to show HTTP status and response text.

## AI Tool Effectiveness Analysis
Document what worked and what did not.

Example analysis:
1. The AI generated a useful starting structure quickly.
2. It missed one required subset endpoint initially and needed manual updates.
3. It assumed PUT for updates, but this API uses POST for create/update upsert.
4. Error display and form validation needed manual improvements.

## How the Client Meets Project Requirements
1. Client invokes all required GET methods for all tables:
   - `GET /api/venues`, `GET /api/venues/{id}`
   - `GET /api/attendees`, `GET /api/attendees/{id}`
   - `GET /api/events`, `GET /api/events/{id}`
   - `GET /api/rsvps`, `GET /api/rsvps/{id}`, `GET /api/rsvps/event/{eventId}`
2. Graduate credit requirement (two-way communication):
   - Create/Update: `POST` endpoints for all tables
   - Delete: `DELETE` endpoints for all tables

## Test Evidence Summary
Record what you tested and result.

Example:
1. Created test venue, attendee, event, and RSVP from the frontend.
2. Updated each created record from the frontend.
3. Fetched all records and fetched each record by id.
4. Queried RSVP subset by event id.
5. Deleted created records and verified removal.

## Screenshots Included in PDF
List the screenshots you captured:
1. Frontend loaded at `http://localhost:8080/`.
2. Successful GET all for each table.
3. Successful GET by id for each table.
4. Successful RSVP subset query by event id.
5. Successful create/update/delete operations.
6. (Optional) error handling example (404, validation failure).
