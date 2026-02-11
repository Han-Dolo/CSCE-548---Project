package com.campus.rsvp.api;

import com.campus.rsvp.business.BusinessManager;
import com.campus.rsvp.model.Rsvp;
import com.campus.rsvp.service.RsvpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/rsvps")
public class RsvpController {
    private final RsvpService service = new RsvpService(new BusinessManager());

    @GetMapping
    public List<Rsvp> getAll() throws SQLException {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) throws SQLException {
        Rsvp rsvp = service.getById(id);
        if (rsvp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rsvp);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<?> getByEvent(@PathVariable("eventId") int eventId) throws SQLException {
        List<Rsvp> rsvps = service.getByEvent(eventId);
        return ResponseEntity.ok(rsvps);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Rsvp rsvp) throws SQLException {
        int id = service.save(rsvp);
        if (id == -1) {
            return ResponseEntity.notFound().build();
        }
        if (rsvp.getRsvpId() == 0) {
            rsvp.setRsvpId(id);
        }
        return ResponseEntity.ok(rsvp);
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
