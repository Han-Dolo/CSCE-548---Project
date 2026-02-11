package com.campus.rsvp.api;

import com.campus.rsvp.business.BusinessManager;
import com.campus.rsvp.model.Attendee;
import com.campus.rsvp.service.AttendeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/attendees")
public class AttendeeController {
    private final AttendeeService service = new AttendeeService(new BusinessManager());

    @GetMapping
    public List<Attendee> getAll() throws SQLException {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) throws SQLException {
        Attendee attendee = service.getById(id);
        if (attendee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(attendee);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Attendee attendee) throws SQLException {
        int id = service.save(attendee);
        if (id == -1) {
            return ResponseEntity.notFound().build();
        }
        if (attendee.getAttendeeId() == 0) {
            attendee.setAttendeeId(id);
        }
        return ResponseEntity.ok(attendee);
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
