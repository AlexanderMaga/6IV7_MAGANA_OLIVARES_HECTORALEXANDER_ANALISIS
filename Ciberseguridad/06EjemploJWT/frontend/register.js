document.getElementById("registerForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value;
  const cpassword = document.getElementById("cpassword").value;

  if (!email || !password || !cpassword) {
    alert("Por favor, completa todos los campos.");
    return;
  }

  if (password !== cpassword) {
    alert("Las contraseñas no coinciden.");
    return;
  }

  try {
    const res = await fetch("http://localhost:3000/api/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password })
    });

    const data = await res.json();

    if (res.ok) {
      alert("Usuario registrado con éxito");
      localStorage.setItem("token", data.token);
      window.location.href = "index.html";
    } else {
      alert(data.message || "Error al registrar");
    }
  } catch (err) {
    console.error("Error en la petición de registro:", err);
    alert("Error en la red o en el servidor");
  }
});
