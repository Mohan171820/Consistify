// Auth guard

function toTimeline(practice, videos) {
  const list = [];
  practice.forEach((p) => {
    list.push({
      date: p.practiceDate,
      title: `${p.skillName} Practice`,
      detail: `${p.durationMinutes} min • ${p.effortLevel}`,
      type: "practice",
    });
  });

  videos.forEach((v) => {
    list.push({
      date: "Recent",
      title: "YouTube Focus",
      detail: `${v.title} • ${Math.round((v.watchedSeconds || 0) / 60)} min`,
      type: "video",
    });
  });

  return list;
}

function renderTimeline(items) {
  const container = document.getElementById("timelineList");
  container.innerHTML = "";
  if (!items.length) {
    container.innerHTML = '<p class="muted">No history yet.</p>';
    return;
  }

  items.forEach((item) => {
    const article = document.createElement("article");
    article.className = "timeline-item";
    article.innerHTML = `
      <p class="muted">${item.date}</p>
      <strong>${item.title}</strong>
      <p>${item.detail}</p>
    `;
    container.appendChild(article);
  });
}

async function init() {
  await consistifyApi.requireAuth();
  const [practicePage, videos] = await Promise.all([
    consistifyApi.request("/api/v1/practice/my?page=0&size=20"),
    consistifyApi.request("/api/youtube/history"),
  ]);
  const practice = Array.isArray(practicePage.content) ? practicePage.content : [];
  renderTimeline(toTimeline(practice, Array.isArray(videos) ? videos : []));
}

init();
