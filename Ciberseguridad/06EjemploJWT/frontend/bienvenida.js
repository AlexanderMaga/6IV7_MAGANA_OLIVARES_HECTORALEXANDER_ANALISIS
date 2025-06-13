document.getElementById("loginForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value;

  const emailValido = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;

  if (!email || !password) {
    alert("Por favor, completa todos los campos.");
    return;
  }

  //if (!emailValido.test(email)) {
  //  alert("Por favor, ingresa un correo electrónico válido.");
  //  return;
  //}

  try {
    const response = await fetch("http://localhost:3000/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, password }),
    });

    const data = await response.json();

    if (!response.ok) {
      alert(data.msg || "Credenciales inválidas.");
      return;
    }

    localStorage.setItem("token", data.token);
    alert("Inicio de sesión exitoso.");
    window.location.href = "index.html";
  } catch (error) {
    console.error("Error en la petición de login:", error);
    alert("Error de conexión. Intenta más tarde.");
  }
});
