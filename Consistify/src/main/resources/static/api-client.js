(function () {
const API_BASE = "";

  async function request(path, options = {}) {
    const response = await fetch(`${API_BASE}${path}`, {
      credentials: "include",
      headers: {
        Accept: "application/json",
        ...(options.body ? { "Content-Type": "application/json" } : {}),
        ...(options.headers || {}),
      },
      ...options,
      body: options.body ? JSON.stringify(options.body) : undefined,
    });

    if (response.status === 401) {
      throw Object.assign(new Error("Unauthorized"), { code: 401 });
    }

    if (!response.ok) {
      let detail = "Request failed";
      try {
        detail = await response.text();
      } catch {}
      throw new Error(detail || "Request failed");
    }

    const contentType = response.headers.get("content-type") || "";
    if (contentType.includes("application/json")) {
      return response.json();
    }

    return response.text();
  }

  async function requireAuth() {
    try {
      const me = await request("/api/auth/me");

      const userNameNode = document.getElementById("userName");
      if (userNameNode) {
        userNameNode.textContent = me?.name || "";
      }

      return me;
    } catch (error) {
      if (error.code === 401) {
        window.location.href = "login.html";
        return null;
      }
      throw error;
    }
  }

  async function logout() {
    await request("/logout", { method: "POST" });
    window.location.href = "login.html";
  }

  function setStatus(node, message, type = "") {
    if (!node) return;
    node.textContent = message;
    node.classList.remove("error", "success");
    if (type) node.classList.add(type);
  }

  window.consistifyApi = {
    API_BASE,
    request,
    requireAuth,
    logout,
    setStatus,
  };
})();
