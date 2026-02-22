package com.campus.rsvp.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class ApiConsoleClient {
    private static final String BASE_URL = "http://localhost:8081";
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public static void main(String[] args) throws Exception {
        System.out.println("Campus RSVP API Console Tester");
        System.out.println("Make sure the Spring Boot API is running first.");

        int venueId = createVenue();
        int attendeeId = createAttendee();
        int eventId = createEvent(venueId);
        int rsvpId = createRsvp(eventId, attendeeId);

        updateRsvp(rsvpId, eventId, attendeeId);
        updateEvent(eventId, venueId);

        getAll("venues");
        getAll("attendees");
        getAll("events");
        getAll("rsvps");

        delete("rsvps", rsvpId);
        delete("events", eventId);
        delete("attendees", attendeeId);
        delete("venues", venueId);

        System.out.println("Done.");
    }

    private static int createVenue() throws IOException, InterruptedException {
        String body = """
                {
                  "venueId": 0,
                  "name": "Student Union Ballroom",
                  "building": "Student Union",
                  "room": "101",
                  "capacity": 300,
                  "outdoor": false
                }
                """;
        String response = post("/api/venues", body);
        int id = extractId(response, "venueId");
        System.out.println("Created venue: " + id);
        return id;
    }

    private static int createAttendee() throws IOException, InterruptedException {
        String body = """
                {
                  "attendeeId": 0,
                  "firstName": "Jamie",
                  "lastName": "Lee",
                  "email": "jamie.lee@example.edu",
                  "major": "Computer Science",
                  "classYear": 2027
                }
                """;
        String response = post("/api/attendees", body);
        int id = extractId(response, "attendeeId");
        System.out.println("Created attendee: " + id);
        return id;
    }

    private static int createEvent(int venueId) throws IOException, InterruptedException {
        String body = """
                {
                  "eventId": 0,
                  "title": "Campus Career Fair",
                  "description": "Meet employers and network.",
                  "eventDate": "2026-03-20",
                  "startTime": "10:00:00",
                  "endTime": "14:00:00",
                  "venueId": %d,
                  "organizer": "Career Services",
                  "category": "Career"
                }
                """.formatted(venueId);
        String response = post("/api/events", body);
        int id = extractId(response, "eventId");
        System.out.println("Created event: " + id);
        return id;
    }

    private static int createRsvp(int eventId, int attendeeId) throws IOException, InterruptedException {
        String body = """
                {
                  "rsvpId": 0,
                  "eventId": %d,
                  "attendeeId": %d,
                  "status": "Going",
                  "checkedIn": false
                }
                """.formatted(eventId, attendeeId);
        String response = post("/api/rsvps", body);
        int id = extractId(response, "rsvpId");
        System.out.println("Created RSVP: " + id);
        return id;
    }

    private static void updateEvent(int eventId, int venueId) throws IOException, InterruptedException {
        String body = """
                {
                  "eventId": %d,
                  "title": "Campus Career Fair (Updated)",
                  "description": "Meet employers and network.",
                  "eventDate": "2026-03-20",
                  "startTime": "10:00:00",
                  "endTime": "14:30:00",
                  "venueId": %d,
                  "organizer": "Career Services",
                  "category": "Career"
                }
                """.formatted(eventId, venueId);
        post("/api/events", body);
        System.out.println("Updated event: " + eventId);
    }

    private static void updateRsvp(int rsvpId, int eventId, int attendeeId) throws IOException, InterruptedException {
        String body = """
                {
                  "rsvpId": %d,
                  "eventId": %d,
                  "attendeeId": %d,
                  "status": "Interested",
                  "checkedIn": true
                }
                """.formatted(rsvpId, eventId, attendeeId);
        post("/api/rsvps", body);
        System.out.println("Updated RSVP: " + rsvpId);
    }

    private static void getAll(String resource) throws IOException, InterruptedException {
        String response = get("/api/" + resource);
        System.out.println("GET /api/" + resource + " => " + response);
    }

    private static void delete(String resource, int id) throws IOException, InterruptedException {
        delete("/api/" + resource + "/" + id);
        System.out.println("Deleted " + resource + ": " + id);
    }

    private static String get(String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();
        return send(request);
    }

    private static String post(String path, String body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(10))
                .build();
        return send(request);
    }

    private static void delete(String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .DELETE()
                .timeout(Duration.ofSeconds(10))
                .build();
        send(request);
    }

    private static String send(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new IOException("HTTP " + response.statusCode() + ": " + response.body());
        }
        return response.body();
    }

    private static int extractId(String json, String field) {
        String token = "\"" + field + "\":";
        int index = json.indexOf(token);
        if (index == -1) {
            throw new IllegalStateException("Missing field " + field + " in response: " + json);
        }
        int start = index + token.length();
        int end = start;
        while (end < json.length() && Character.isWhitespace(json.charAt(end))) {
            end++;
        }
        int numStart = end;
        while (end < json.length() && Character.isDigit(json.charAt(end))) {
            end++;
        }
        if (numStart == end) {
            throw new IllegalStateException("Invalid field " + field + " in response: " + json);
        }
        return Integer.parseInt(json.substring(numStart, end));
    }
}
