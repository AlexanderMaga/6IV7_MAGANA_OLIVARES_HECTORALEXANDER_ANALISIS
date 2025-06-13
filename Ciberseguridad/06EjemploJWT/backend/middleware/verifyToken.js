const jwt = require('jsonwebtoken');

function verificarToken(req, res, next) {
  const authHeader = req.headers['authorization'];
  const token = authHeader && authHeader.split(' ')[1]; // Soporta "Bearer <token>"

  if (!token) {
    console.warn('Token no proporcionado');
    return res.status(403).send('Token requerido');
  }

  jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
    if (err) {
      console.warn('Token inválido:', err.message);
      return res.status(401).send('Token inválido');
    }

    // Aquí confirmamos que el token es válido
    console.log('Token verificado correctamente:', decoded);
    req.user = decoded;
    next();
  });
}

module.exports = verificarToken;
