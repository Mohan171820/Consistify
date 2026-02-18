// Auth guard

const statusNode = document.getElementById("formStatus");

const state = {
  dashboard: {
    totalSkills: 0,
    activeSkills: 0,
    atRiskSkills: 0,
    decayedSkills: 0,
    totalPracticeMinutes: 0,
    streakDays: 0,
    consistencyScore: 0,
  },
  skills: [],
  videos: [],
};

function updateRing(score) {
  const meter = document.getElementById("consistencyRing");
  if (!meter) return;
  const circumference = 2 * Math.PI * 50;
  const offset = circumference - (score / 100) * circumference;
  meter.style.strokeDasharray = `${circumference}`;
  meter.style.strokeDashoffset = `${offset}`;
}

function badgeClass(status) {
  if (status === "ACTIVE") return "active";
  if (status === "AT_RISK") return "risk";
  return "decayed";
}

function renderDashboard() {
  const { dashboard } = state;
  document.getElementById("totalSkills").textContent = dashboard.totalSkills;
  document.getElementById("activeSkills").textContent = dashboard.activeSkills;
  document.getElementById("atRiskSkills").textContent = dashboard.atRiskSkills;
  document.getElementById("decayedSkills").textContent = dashboard.decayedSkills;
  document.getElementById("weekMinutes").textContent = `${dashboard.totalPracticeMinutes} min`;
  document.getElementById("weekStreak").textContent = `${dashboard.streakDays} days`;
  document.getElementById("consistencyScore").textContent = `${dashboard.consistencyScore}%`;
  updateRing(dashboard.consistencyScore);
}

function renderSkills() {
  const skillList = document.getElementById("skillList");
  const select = document.getElementById("skillSelect");
  skillList.innerHTML = "";
  select.innerHTML = "";

  if (!state.skills.length) {
    skillList.innerHTML = '<p class="muted">No skill health data yet.</p>';
    return;
  }

  state.skills.forEach((skill) => {
    const row = document.createElement("article");
    row.className = "skill-item";
    const status = skill.status || "DECAYED";
    const progress = Math.max(5, 100 - Math.min(100, (skill.daysInactive || 0) * 10));
    row.innerHTML = `
      <div class="skill-head">
        <strong>${skill.skillName}</strong>
        <span class="badge ${badgeClass(status)}">${status}</span>
      </div>
      <div class="progress"><span style="width:${progress}%"></span></div>
      <p class="muted">${skill.daysInactive} day(s) inactive • streak ${skill.currentStreak ?? 0}</p>
    `;
    skillList.appendChild(row);

    const option = document.createElement("option");
    option.value = skill.skillId;
    option.textContent = skill.skillName;
    select.appendChild(option);
  });
}

function secondsToHuman(seconds) {
  const mins = Math.floor(seconds / 60);
  const hours = Math.floor(mins / 60);
  const remMins = mins % 60;
  return hours ? `${hours}h ${remMins}m` : `${remMins}m`;
}

function renderVideos() {
  const grid = document.getElementById("videoGrid");
  grid.innerHTML = "";

  if (!state.videos.length) {
    grid.innerHTML = '<p class="muted">No YouTube history yet.</p>';
    return;
  }

  state.videos.slice(0, 3).forEach((video) => {
    const card = document.createElement("article");
    card.className = "video-card";
    card.innerHTML = `
      <strong>${video.title}</strong>
      <p>Watched: ${secondsToHuman(Number(video.watchedSeconds || 0))}</p>
      <a class="ghost-btn link-btn" href="youtube-focus.html">Resume Focus Session</a>
    `;
    grid.appendChild(card);
  });
}

async function wirePracticeForm() {
  const form = document.getElementById("practiceForm");

  form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const skillId = Number(document.getElementById("skillSelect").value);
    const duration = Number(document.getElementById("durationInput").value);
    const effortLevel = document.getElementById("effortInput").value;
    const notes = document.getElementById("notesInput").value.trim();

    if (!skillId || duration < 1) {
      consistifyApi.setStatus(statusNode, "Please pick a valid skill and duration.", "error");
      return;
    }

    try {
      consistifyApi.setStatus(statusNode, "Saving session...");
      await consistifyApi.request("/api/v1/practice/log", {
        method: "POST",
        body: {
          skillId,
          practiceDate: new Date().toISOString().slice(0, 10),
          durationMinutes: duration,
          effortLevel,
          notes,
        },
      });

      consistifyApi.setStatus(statusNode, "Practice logged successfully.", "success");
      document.getElementById("notesInput").value = "";
      await loadData();
    } catch (error) {
      consistifyApi.setStatus(statusNode, error.message || "Failed to log practice.", "error");
    }
  });
}

async function loadData() {
  const [dashboard, skillHealth, history] = await Promise.all([
    consistifyApi.request("/api/v1/dashboard"),
    consistifyApi.request("/api/v1/skills/health"),
    consistifyApi.request("/api/youtube/history"),
  ]);

  state.dashboard.totalSkills = dashboard.totalSkills ?? 0;
  state.dashboard.activeSkills = dashboard.activeSkills ?? 0;
  state.dashboard.atRiskSkills = dashboard.atRiskSkills ?? 0;
  state.dashboard.decayedSkills = dashboard.decayedSkills ?? 0;
  state.dashboard.totalPracticeMinutes = dashboard.totalPracticeMinutes ?? 0;
  state.dashboard.streakDays = Math.max(0, Math.round((dashboard.totalPracticeMinutes ?? 0) / 45));
  state.dashboard.consistencyScore =
    state.dashboard.totalSkills === 0 ? 0 : Math.round((state.dashboard.activeSkills / state.dashboard.totalSkills) * 100);

  state.skills = Array.isArray(skillHealth) ? skillHealth : [];
  state.videos = Array.isArray(history) ? history : [];

  renderDashboard();
  renderSkills();
  renderVideos();
}

async function init() {
  try {
    await consistifyApi.requireAuth();
    await loadData();
    await wirePracticeForm();
  } catch (error) {
    consistifyApi.setStatus(statusNode, "Unable to load dashboard data.", "error");
  }
}

init();
