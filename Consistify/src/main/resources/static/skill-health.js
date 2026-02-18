
function renderHealth(skills) {
  const container = document.getElementById("healthList");
  container.innerHTML = "";

  if (!skills.length) {
    container.innerHTML = '<p class="muted">No skill health data found.</p>';
    return;
  }

  skills.forEach((skill) => {
    const progress = Math.max(6, 100 - Math.min(100, Number(skill.daysInactive || 0) * 10));
    const badgeClass = skill.status === "ACTIVE" ? "active" : skill.status === "AT_RISK" ? "risk" : "decayed";

    const article = document.createElement("article");
    article.className = "skill-item";
    article.innerHTML = `
      <div class="skill-head">
        <strong>${skill.skillName}</strong>
        <span class="badge ${badgeClass}">${skill.status}</span>
      </div>
      <div class="progress"><span style="width:${progress}%"></span></div>
      <p class="muted">Inactive ${skill.daysInactive} day(s) • streak ${skill.currentStreak}</p>
    `;
    container.appendChild(article);
  });
}

async function init() {
  await consistifyApi.requireAuth();
  const data = await consistifyApi.request("/api/v1/skills/health");
  const skills = Array.isArray(data) ? data : [];

  const active = skills.filter((s) => s.status === "ACTIVE").length;
  const risk = skills.filter((s) => s.status === "AT_RISK").length;
  const decayed = skills.filter((s) => s.status === "DECAYED").length;
  const avgInactive = skills.length
    ? (skills.reduce((sum, s) => sum + Math.min(30, Number(s.daysInactive || 0)), 0) / skills.length).toFixed(1)
    : "0.0";

  document.getElementById("healthyCount").textContent = String(active);
  document.getElementById("riskCount").textContent = String(risk);
  document.getElementById("decayedCount").textContent = String(decayed);
  document.getElementById("avgInactive").textContent = String(avgInactive);

  renderHealth(skills);
}

init();
