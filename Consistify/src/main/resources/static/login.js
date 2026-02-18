const API_BASE = "";

async function fetchCurrentUser() {
  try {
    const response = await fetch(`${API_BASE}/api/auth/me`, {
      credentials: "include"
    });

    if (response.ok) {
      window.location.href = "index.html";
    } else {
      console.log("Not authenticated");
    }
  } catch (error) {
    console.error("Auth check failed:", error);
  }
}

// Only run after OAuth success
const urlParams = new URLSearchParams(window.location.search);
if (urlParams.get("auth") === "success") {
  fetchCurrentUser();
}

// Google login button
document.getElementById("googleLogin").addEventListener("click", () => {
  window.location.href = `${API_BASE}/oauth2/authorization/google`;
});

// Run on page load
fetchCurrentUser();
const loginForm = document.getElementById("loginForm");
const submitBtn = document.getElementById("submitBtn");
const statusNode = document.getElementById("status");
const googleLogin = document.getElementById("googleLogin");

function setStatus(message, tone = "") {
  statusNode.textContent = message;
  statusNode.classList.remove("error", "success");
  if (tone) {
    statusNode.classList.add(tone);
  }
}

async function checkSessionAndRedirect() {
  try {
    const response = await fetch(`${API_BASE}/api/auth/me`, {
      credentials: "include",
      headers: { Accept: "application/json" },
    });

    if (response.ok) {
      window.location.href = "index.html";
    }
  } catch {
    // no-op
  }
}

async function handleLogin(event) {
  event.preventDefault();

  const formData = new FormData(loginForm);
  const username = String(formData.get("username") || "").trim();
  const password = String(formData.get("password") || "").trim();

  if (!username || !password) {
    setStatus("Please enter both email and password.", "error");
    return;
  }

  submitBtn.disabled = true;
  setStatus("Signing you in...");

  const payload = new URLSearchParams();
  payload.append("username", username);
  payload.append("password", password);

  if (formData.get("remember-me")) {
    payload.append("remember-me", "on");
  }

  try {
    const response = await fetch(`${API_BASE}/login`, {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      body: payload.toString(),
      redirect: "manual",
    });

    const maybeSuccess = response.status === 200 || response.status === 302 || response.type === "opaqueredirect";

    if (!maybeSuccess) {
      setStatus("Invalid credentials. Please try again.", "error");
      return;
    }

    const sessionProbe = await fetch(`${API_BASE}/api/auth/me`, {
      credentials: "include",
      headers: { Accept: "application/json" },
    });

    if (sessionProbe.ok) {
      setStatus("Login successful. Redirecting to dashboard...", "success");
      setTimeout(() => {
        window.location.href = "index.html";
      }, 500);
    } else {
      setStatus(
        "Signed in, but your browser blocked the session cookie across origins. Serve frontend from backend origin for full auth flow.",
        "error"
      );
    }
  } catch {
    setStatus("Unable to contact the server. Check backend URL and try again.", "error");
  } finally {
    submitBtn.disabled = false;
  }
}

function handleGoogleLogin() {
  window.location.href = `${API_BASE}/oauth2/authorization/google`;
}

loginForm.addEventListener("submit", handleLogin);
googleLogin.addEventListener("click", handleGoogleLogin);
checkSessionAndRedirect();
