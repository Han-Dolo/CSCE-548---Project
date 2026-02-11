package com.campus.rsvp.api;

import com.campus.rsvp.business.BusinessManager;
import com.campus.rsvp.model.Event;
import com.campus.rsvp.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService service = new EventService(new BusinessManager());

    @GetMapping
    public List<Event> getAll() throws SQLException {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) throws SQLException {
        Event event = service.getById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Event event) throws SQLException {
        int id = service.save(event);
        if (id == -1) {
            return ResponseEntity.notFound().build();
        }
        if (event.getEventId() == 0) {
            event.setEventId(id);
        }
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) throws SQLException {
        boolean deleted = service.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
