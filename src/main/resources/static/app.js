const jsonHeaders = { "Content-Type": "application/json" };

function byId(id) {
  return document.getElementById(id);
}

function escapeHtml(value) {
  return String(value ?? "")
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;");
}

function formatValue(value) {
  if (typeof value === "boolean") {
    return value
      ? '<span class="pill pill-ok">Yes</span>'
      : '<span class="pill pill-bad">No</span>';
  }
  return escapeHtml(value);
}

function toLabel(key) {
  return key
    .replace(/([A-Z])/g, " $1")
    .replace(/^./, (char) => char.toUpperCase())
    .trim();
}

function renderTable(rows) {
  if (!Array.isArray(rows) || rows.length === 0) {
    return "<div>No records found.</div>";
  }

  const columns = Object.keys(rows[0]);
  const header = columns.map((col) => `<th>${toLabel(col)}</th>`).join("");
  const body = rows
    .map((row) => {
      const cells = columns
        .map((col) => `<td>${formatValue(row[col])}</td>`)
        .join("");
      return `<tr>${cells}</tr>`;
    })
    .join("");

  return `<table class="result-table"><thead><tr>${header}</tr></thead><tbody>${body}</tbody></table>`;
}

function renderSingleRecord(record) {
  if (!record || typeof record !== "object") {
    return `<div>${formatValue(record)}</div>`;
  }
  const row = Object.entries(record)
    .map(([key, value]) => `<tr><th>${toLabel(key)}</th><td>${formatValue(value)}</td></tr>`)
    .join("");
  return `<table class="result-table"><tbody>${row}</tbody></table>`;
}

function showError(targetId, message) {
  const out = byId(targetId);
  out.classList.add("error");
  out.innerHTML = `
    <div class="result-title">Something went wrong</div>
    <div class="result-message">${escapeHtml(message)}</div>
  `;
}

function showSuccess(targetId, title, message, data) {
  const out = byId(targetId);
  out.classList.remove("error");

  let view = "";
  if (Array.isArray(data)) {
    view = renderTable(data);
  } else if (data && typeof data === "object") {
    view = renderSingleRecord(data);
  } else if (data !== undefined && data !== null) {
    view = `<div>${formatValue(data)}</div>`;
  }

  out.innerHTML = `
    <div class="result-title">${escapeHtml(title)}</div>
    <div class="result-message">${escapeHtml(message)}</div>
    ${view}
  `;
}

function toApiTime(value) {
  return `${value}:00`;
}

async function apiGet(path) {
  const res = await fetch(path);
  const text = await res.text();
  if (!res.ok) {
    throw new Error(`GET failed (${res.status}). ${text || "No details."}`);
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
    throw new Error(`Save failed (${res.status}). ${text || "No details."}`);
  }
  return text ? JSON.parse(text) : null;
}

async function apiDelete(path) {
  const res = await fetch(path, { method: "DELETE" });
  const text = await res.text();
  if (!res.ok) {
    throw new Error(`Delete failed (${res.status}). ${text || "No details."}`);
  }
  return { status: res.status };
}

function bindGetAll(btnId, path, outId, label) {
  byId(btnId).addEventListener("click", async () => {
    try {
      const data = await apiGet(path);
      const count = Array.isArray(data) ? data.length : 0;
      showSuccess(outId, `${label} Loaded`, `Found ${count} record(s).`, data);
    } catch (err) {
      showError(outId, err.message);
    }
  });
}

function bindGetOne(btnId, inputId, pathPrefix, outId, label) {
  byId(btnId).addEventListener("click", async () => {
    const id = byId(inputId).value;
    if (!id) {
      showError(outId, `Please enter a ${label} ID first.`);
      return;
    }
    try {
      const data = await apiGet(`${pathPrefix}/${id}`);
      showSuccess(outId, `${label} Found`, `Showing ${label.toLowerCase()} ID ${id}.`, data);
    } catch (err) {
      showError(outId, err.message);
    }
  });
}

function bindDelete(btnId, inputId, pathPrefix, outId, label) {
  byId(btnId).addEventListener("click", async () => {
    const id = byId(inputId).value;
    if (!id) {
      showError(outId, `Please enter a ${label} ID first.`);
      return;
    }
    try {
      await apiDelete(`${pathPrefix}/${id}`);
      showSuccess(outId, `${label} Deleted`, `${label} ID ${id} was deleted.`, null);
    } catch (err) {
      showError(outId, err.message);
    }
  });
}

function initConnectionPanel() {
  byId("btnHealth").addEventListener("click", async () => {
    try {
      const events = await apiGet("/api/events");
      const count = Array.isArray(events) ? events.length : 0;
      showSuccess("healthOut", "Connection Successful", `API is online. ${count} event(s) available.`, null);
    } catch (err) {
      showError("healthOut", err.message);
    }
  });
}

function initVenues() {
  bindGetAll("venuesGetAll", "/api/venues", "venuesOut", "Venues");
  bindGetOne("venuesGetOne", "venuesGetId", "/api/venues", "venuesOut", "Venue");
  bindDelete("venuesDelete", "venuesDeleteId", "/api/venues", "venuesOut", "Venue");

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
      const action = payload.venueId === 0 ? "created" : "updated";
      showSuccess("venuesOut", "Venue Saved", `Venue ${action} successfully.`, data);
    } catch (err) {
      showError("venuesOut", err.message);
    }
  });
}

function initAttendees() {
  bindGetAll("attendeesGetAll", "/api/attendees", "attendeesOut", "Attendees");
  bindGetOne("attendeesGetOne", "attendeesGetId", "/api/attendees", "attendeesOut", "Attendee");
  bindDelete("attendeesDelete", "attendeesDeleteId", "/api/attendees", "attendeesOut", "Attendee");

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
      const action = payload.attendeeId === 0 ? "created" : "updated";
      showSuccess("attendeesOut", "Attendee Saved", `Attendee ${action} successfully.`, data);
    } catch (err) {
      showError("attendeesOut", err.message);
    }
  });
}

function initEvents() {
  bindGetAll("eventsGetAll", "/api/events", "eventsOut", "Events");
  bindGetOne("eventsGetOne", "eventsGetId", "/api/events", "eventsOut", "Event");
  bindDelete("eventsDelete", "eventsDeleteId", "/api/events", "eventsOut", "Event");

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
      const action = payload.eventId === 0 ? "created" : "updated";
      showSuccess("eventsOut", "Event Saved", `Event ${action} successfully.`, data);
    } catch (err) {
      showError("eventsOut", err.message);
    }
  });
}

function initRsvps() {
  bindGetAll("rsvpsGetAll", "/api/rsvps", "rsvpsOut", "RSVPs");
  bindGetOne("rsvpsGetOne", "rsvpsGetId", "/api/rsvps", "rsvpsOut", "RSVP");
  bindDelete("rsvpsDelete", "rsvpsDeleteId", "/api/rsvps", "rsvpsOut", "RSVP");

  byId("rsvpsGetByEvent").addEventListener("click", async () => {
    const eventId = byId("rsvpsEventId").value;
    if (!eventId) {
      showError("rsvpsOut", "Please enter an event ID first.");
      return;
    }
    try {
      const data = await apiGet(`/api/rsvps/event/${eventId}`);
      const count = Array.isArray(data) ? data.length : 0;
      showSuccess("rsvpsOut", "RSVPs by Event", `Found ${count} RSVP(s) for event ID ${eventId}.`, data);
    } catch (err) {
      showError("rsvpsOut", err.message);
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
      const action = payload.rsvpId === 0 ? "created" : "updated";
      showSuccess("rsvpsOut", "RSVP Saved", `RSVP ${action} successfully.`, data);
    } catch (err) {
      showError("rsvpsOut", err.message);
    }
  });
}

initConnectionPanel();
initVenues();
initAttendees();
initEvents();
initRsvps();
