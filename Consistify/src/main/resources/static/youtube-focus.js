
const ytStatus = document.getElementById("youtubeStatus");

let player;
let watchStart = 0;
let totalWatched = 0;
let currentVideoId = "";
let currentTitle = "";

function secondsToHuman(seconds) {
  const mins = Math.floor(Number(seconds || 0) / 60);
  const hours = Math.floor(mins / 60);
  const remMins = mins % 60;
  return hours ? `${hours}h ${remMins}m` : `${remMins}m`;
}

function parseYoutubeId(url) {
  try {
    const parsed = new URL(url);
    if (parsed.hostname.includes("youtu.be")) {
      return parsed.pathname.slice(1);
    }
    return parsed.searchParams.get("v") || "";
  } catch {
    return "";
  }
}

function onYouTubeIframeAPIReady() {
  // required globally by YouTube API
}

function loadVideo(videoId) {
  currentVideoId = videoId;
  totalWatched = 0;

  if (player) {
    player.loadVideoById(videoId);
    return;
  }

  player = new YT.Player("player", {
    height: "390",
    width: "640",
    videoId: videoId,
    events: {
      onStateChange: onPlayerStateChange,
    },
  });
}

function onPlayerStateChange(event) {
  if (event.data === YT.PlayerState.PLAYING) {
    watchStart = Date.now();
  }

  if (
    event.data === YT.PlayerState.PAUSED ||
    event.data === YT.PlayerState.ENDED
  ) {
    if (watchStart) {
      totalWatched += Math.floor((Date.now() - watchStart) / 1000);
      watchStart = 0;
    }
  }
}

function renderHistory(items) {
  const grid = document.getElementById("youtubeHistoryGrid");
  grid.innerHTML = "";

  if (!items.length) {
    grid.innerHTML = '<p class="muted">No tracked videos yet.</p>';
    return;
  }

  let totalSeconds = 0;

  items.forEach((item) => {
    totalSeconds += Number(item.watchedSeconds || 0);

    const card = document.createElement("article");
    card.className = "video-card";

    card.innerHTML = `
      <strong>${item.title}</strong>
      <p>Watched: ${secondsToHuman(item.watchedSeconds)}</p>
      <p class="muted">Video ID: ${item.videoId}</p>
    `;

    grid.appendChild(card);
  });

  document.getElementById("focusToday").textContent =
    secondsToHuman(totalSeconds);
}

async function loadHistory() {
  const data = await consistifyApi.request("/api/youtube/history");
  renderHistory(Array.isArray(data) ? data : []);
}

function wireForm() {
  const form = document.getElementById("youtubeForm");

  form.addEventListener("submit", (event) => {
    event.preventDefault();

    const url = document.getElementById("youtubeUrl").value.trim();
    currentTitle =
      document.getElementById("youtubeTitle").value.trim() ||
      "Learning Video";

    const videoId = parseYoutubeId(url);

    if (!videoId) {
      consistifyApi.setStatus(
        ytStatus,
        "Please enter a valid YouTube URL.",
        "error"
      );
      return;
    }

    loadVideo(videoId);
    consistifyApi.setStatus(ytStatus, "Video loaded. Start watching!", "success");
  });
}

function wireSaveButton() {
  document
    .getElementById("saveWatchTime")
    .addEventListener("click", async () => {

      if (watchStart) {
        totalWatched += Math.floor((Date.now() - watchStart) / 1000);
        watchStart = 0;
      }

      if (totalWatched <= 0) {
        consistifyApi.setStatus(
          ytStatus,
          "Watch the video before saving.",
          "error"
        );
        return;
      }

      try {
        await consistifyApi.request("/api/youtube/watch-time", {
          method: "POST",
          body: {
            videoId: currentVideoId,
            title: currentTitle,
            watchedSeconds: totalWatched,
          },
        });

        consistifyApi.setStatus(
          ytStatus,
          `Saved ${secondsToHuman(totalWatched)} successfully.`,
          "success"
        );

        totalWatched = 0;
        await loadHistory();

      } catch (error) {
        consistifyApi.setStatus(
          ytStatus,
          error.message || "Failed to save watch time.",
          "error"
        );
      }
    });
}

async function init() {
  await consistifyApi.requireAuth();
  await loadHistory();
  wireForm();
  wireSaveButton();
}

init();
