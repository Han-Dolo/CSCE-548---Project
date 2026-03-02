const jsonHeaders = { "Content-Type": "application/json" };

function byId(id) {
  return document.getElementById(id);
}

function showOutput(targetId, data, isError = false) {
  const out = byId(targetId);
  out.classList.toggle("error", isError);
  out.textContent =
    typeof data === "string" ? data : JSON.stringify(data, null, 2);
}

function toApiTime(value) {
  return `${value}:00`;
}

async function apiGet(path) {
  const res = await fetch(path);
  const text = await res.text();
  if (!res.ok) {
    throw new Error(`GET ${path} failed (${res.status}): ${text}`);
  }
  return text ? JSON.parse(text) : null;
}

async function apiPost(path, payload) {
  const res = await fetch(path, {
    method: "POST",
    headers: jsonHeaders,
    body: JSON.stringify(payload)
  });
  const text = await res.text();
  if (!res.ok) {
    throw new Error(`POST ${path} failed (${res.status}): ${text}`);
  }
  return text ? JSON.parse(text) : null;
}

async function apiDelete(path) {
  const res = await fetch(path, { method: "DELETE" });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(`DELETE ${path} failed (${res.status}): ${text}`);
  }
  return { ok: true, status: res.status };
}

function bindGetAll(btnId, path, outId) {
  byId(btnId).addEventListener("click", async () => {
    try {
      const data = await apiGet(path);
      showOutput(outId, data);
    } catch (err) {
      showOutput(outId, err.message, true);
    }
  });
}

function bindGetOne(btnId, inputId, pathPrefix, outId) {
  byId(btnId).addEventListener("click", async () => {
    const id = byId(inputId).value;
    if (!id) {
      showOutput(outId, "Missing id value.", true);
      return;
    }
    try {
      const data = await apiGet(`${pathPrefix}/${id}`);
      showOutput(outId, data);
    } catch (err) {
      showOutput(outId, err.message, true);
    }
  });
}

function bindDelete(btnId, inputId, pathPrefix, outId) {
  byId(btnId).addEventListener("click", async () => {
    const id = byId(inputId).value;
    if (!id) {
      showOutput(outId, "Missing id value.", true);
      return;
    }
    try {
      const data = await apiDelete(`${pathPrefix}/${id}`);
      showOutput(outId, data);
    } catch (err) {
      showOutput(outId, err.message, true);
    }
  });
}

function initConnectionPanel() {
  byId("btnHealth").addEventListener("click", async () => {
    try {
      const data = await apiGet("/api/events");
      showOutput("healthOut", {
        message: "API reachable",
        eventCount: Array.isArray(data) ? data.length : "n/a"
      });
    } catch (err) {
      showOutput("healthOut", err.message, true);
    }
  });
}

function initVenues() {
  bindGetAll("venuesGetAll", "/api/venues", "venuesOut");
  bindGetOne("venuesGetOne", "venuesGetId", "/api/venues", "venuesOut");
  bindDelete("venuesDelete", "venuesDeleteId", "/api/venues", "venuesOut");

  byId("venuesForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = {
      venueId: Number(byId("venueId").value || 0),
      name: byId("venueName").value,
      building: byId("venueBuilding").value,
      room: byId("venueRoom").value,
      capacity: Number(byId("venueCapacity").value),
      outdoor: byId("venueOutdoor").checked
    };
    try {
      const data = await apiPost("/api/venues", payload);
      showOutput("venuesOut", data);
    } catch (err) {
      showOutput("venuesOut", err.message, true);
    }
  });
}

function initAttendees() {
  bindGetAll("attendeesGetAll", "/api/attendees", "attendeesOut");
  bindGetOne("attendeesGetOne", "attendeesGetId", "/api/attendees", "attendeesOut");
  bindDelete("attendeesDelete", "attendeesDeleteId", "/api/attendees", "attendeesOut");

  byId("attendeesForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = {
      attendeeId: Number(byId("attendeeId").value || 0),
      firstName: byId("attendeeFirstName").value,
      lastName: byId("attendeeLastName").value,
      email: byId("attendeeEmail").value,
      major: byId("attendeeMajor").value,
      classYear: Number(byId("attendeeClassYear").value)
    };
    try {
      const data = await apiPost("/api/attendees", payload);
      showOutput("attendeesOut", data);
    } catch (err) {
      showOutput("attendeesOut", err.message, true);
    }
  });
}

function initEvents() {
  bindGetAll("eventsGetAll", "/api/events", "eventsOut");
  bindGetOne("eventsGetOne", "eventsGetId", "/api/events", "eventsOut");
  bindDelete("eventsDelete", "eventsDeleteId", "/api/events", "eventsOut");

  byId("eventsForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = {
      eventId: Number(byId("eventId").value || 0),
      title: byId("eventTitle").value,
      description: byId("eventDescription").value,
      eventDate: byId("eventDate").value,
      startTime: toApiTime(byId("eventStartTime").value),
      endTime: toApiTime(byId("eventEndTime").value),
      venueId: Number(byId("eventVenueId").value),
      organizer: byId("eventOrganizer").value,
      category: byId("eventCategory").value
    };
    try {
      const data = await apiPost("/api/events", payload);
      showOutput("eventsOut", data);
    } catch (err) {
      showOutput("eventsOut", err.message, true);
    }
  });
}

function initRsvps() {
  bindGetAll("rsvpsGetAll", "/api/rsvps", "rsvpsOut");
  bindGetOne("rsvpsGetOne", "rsvpsGetId", "/api/rsvps", "rsvpsOut");
  bindDelete("rsvpsDelete", "rsvpsDeleteId", "/api/rsvps", "rsvpsOut");

  byId("rsvpsGetByEvent").addEventListener("click", async () => {
    const eventId = byId("rsvpsEventId").value;
    if (!eventId) {
      showOutput("rsvpsOut", "Missing eventId.", true);
      return;
    }
    try {
      const data = await apiGet(`/api/rsvps/event/${eventId}`);
      showOutput("rsvpsOut", data);
    } catch (err) {
      showOutput("rsvpsOut", err.message, true);
    }
  });

  byId("rsvpsForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = {
      rsvpId: Number(byId("rsvpId").value || 0),
      eventId: Number(byId("rsvpEventId").value),
      attendeeId: Number(byId("rsvpAttendeeId").value),
      status: byId("rsvpStatus").value,
      checkedIn: byId("rsvpCheckedIn").checked
    };
    try {
      const data = await apiPost("/api/rsvps", payload);
      showOutput("rsvpsOut", data);
    } catch (err) {
      showOutput("rsvpsOut", err.message, true);
    }
  });
}

initConnectionPanel();
initVenues();
initAttendees();
initEvents();
initRsvps();
