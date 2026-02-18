
const settingsStatus = document.getElementById("settingsStatus");

async function init() {
  const user = await consistifyApi.requireAuth();
  if (!user) return;

  document.getElementById("displayName").value = user.name || "";
  document.getElementById("email").value = user.email || "";

  document
    .getElementById("settingsForm")
    .addEventListener("submit", async (e) => {
      e.preventDefault();

      try {
        await consistifyApi.request("/api/users/update", {
          method: "PUT",
          body: {
            name: document.getElementById("displayName").value.trim(),
            email: document.getElementById("email").value.trim(),
          },
        });

        consistifyApi.setStatus(
          settingsStatus,
          "Profile updated successfully.",
          "success"
        );
      } catch (error) {
        consistifyApi.setStatus(
          settingsStatus,
          error.message || "Update failed.",
          "error"
        );
      }
    });

  document
    .getElementById("logoutBtn")
    .addEventListener("click", async () => {
      await consistifyApi.logout();
    });
}

init();
