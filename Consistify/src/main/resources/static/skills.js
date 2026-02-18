const skillStatus = document.getElementById("skillStatus");

function renderSkills(skills) {
  const container = document.getElementById("skillsList");
  container.innerHTML = "";

  if (!skills.length) {
    container.innerHTML = "<p class='muted'>No skills yet.</p>";
    return;
  }

  skills.forEach(skill => {
    const div = document.createElement("div");
    div.className = "skill-card";

    div.innerHTML = `
      <strong>${skill.name}</strong>
      <p class="muted">${skill.category}</p>
      <span class="${skill.active ? "active-badge" : "inactive-badge"}">
        ${skill.active ? "Active" : "Inactive"}
      </span>
    `;

    container.appendChild(div);
  });
}

async function loadSkills() {
  try {
    const response = await consistifyApi.request(
      "/api/v1/skills?page=0&size=50"
    );

    // Backend returns Page<SkillResponseDTO>
    const skills = Array.isArray(response.content)
      ? response.content
      : [];

    renderSkills(skills);
  } catch (error) {
    console.error("Failed to load skills:", error);
  }
}

function wireForm() {
  const form = document.getElementById("skillForm");

  form.addEventListener("submit", async (event) => {
    event.preventDefault();
const payload = {
  name: document.getElementById("skillName").value.trim(),
  category: document.getElementById("skillCategory").value.trim(),
  decayDays: 7   // default value
};


    try {
      consistifyApi.setStatus(skillStatus, "Creating skill...");

      await consistifyApi.request("/api/v1/skills", {
        method: "POST",
        body: payload,
      });

      consistifyApi.setStatus(
        skillStatus,
        "Skill added successfully.",
        "success"
      );

      form.reset();
      await loadSkills();

    } catch (error) {
      consistifyApi.setStatus(
        skillStatus,
        error.message || "Failed to create skill.",
        "error"
      );
    }
  });
}

async function init() {
  await consistifyApi.requireAuth();
  await loadSkills();
  wireForm();
}

init();
