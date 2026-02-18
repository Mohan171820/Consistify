const practiceStatus = document.getElementById("practiceStatus");

function renderRows(rows) {
  const body = document.getElementById("practiceRows");
  body.innerHTML = "";

  if (!rows.length) {
    body.innerHTML = '<tr><td colspan="5" class="muted">No sessions found.</td></tr>';
    return;
  }

  rows.forEach((row) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${row.practiceDate}</td>
      <td>${row.skillName}</td>
      <td>${row.durationMinutes} min</td>
      <td>${row.effortLevel}</td>
      <td>${row.notes || "—"}</td>
    `;
    body.appendChild(tr);
  });
}

async function loadSkills() {
  const response = await consistifyApi.request("/api/v1/skills?page=0&size=100");
  const list = Array.isArray(response.content) ? response.content : [];
  const select = document.getElementById("skillSelectPage");
  select.innerHTML = "";
  list.forEach((skill) => {
    const option = document.createElement("option");
    option.value = skill.id;
    option.textContent = `${skill.name} (${skill.category})`;
    select.appendChild(option);
  });
}

async function loadPractices() {
  const response = await consistifyApi.request("/api/v1/practice/my?page=0&size=20");

  console.log("Practice response:", response);

  const rows = Array.isArray(response)
    ? response
    : Array.isArray(response.content)
    ? response.content
    : [];

  renderRows(rows);
}

function wireForm() {
  const form = document.getElementById("practicePageForm");
  form.addEventListener("submit", async (event) => {
    event.preventDefault();
    const payload = {
      skillId: Number(document.getElementById("skillSelectPage").value),
      practiceDate: document.getElementById("practiceDate").value,
      durationMinutes: Number(document.getElementById("durationPage").value),
      effortLevel: document.getElementById("effortPage").value,
      notes: document.getElementById("notesPage").value.trim(),
    };

    try {
      consistifyApi.setStatus(practiceStatus, "Saving practice session...");
      await consistifyApi.request("/api/v1/practice/log", { method: "POST", body: payload });
      consistifyApi.setStatus(practiceStatus, "Practice logged successfully.", "success");
      await loadPractices();
    } catch (error) {
      consistifyApi.setStatus(practiceStatus, error.message || "Failed to save session.", "error");
    }
  });
}

async function init() {
  await consistifyApi.requireAuth();
  document.getElementById("practiceDate").value = new Date().toISOString().slice(0, 10);
  await loadSkills();
  await loadPractices();
  wireForm();
}

init();
