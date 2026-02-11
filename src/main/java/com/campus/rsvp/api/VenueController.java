package com.campus.rsvp.api;

import com.campus.rsvp.business.BusinessManager;
import com.campus.rsvp.model.Venue;
import com.campus.rsvp.service.VenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {
    private final VenueService service = new VenueService(new BusinessManager());

    @GetMapping
    public List<Venue> getAll() throws SQLException {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) throws SQLException {
        Venue venue = service.getById(id);
        if (venue == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(venue);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Venue venue) throws SQLException {
        int id = service.save(venue);
        if (id == -1) {
            return ResponseEntity.notFound().build();
        }
        if (venue.getVenueId() == 0) {
            venue.setVenueId(id);
        }
        return ResponseEntity.ok(venue);
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
