const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const bd = require('../bd');

// REGISTRO
router.post('/register', async (req, res) => {
    const { email, password } = req.body;
    try {
        const hashed = await bcrypt.hash(password, 10);

        bd.query(
            'INSERT INTO usuarios (email, password) VALUES (?, ?)',
            [email, hashed],
            (err, result) => {
                if (err) {
                    console.error('ERROR al registrar al usuario:', err);
                    return res.status(500).send('Error al registrar');
                }

                // Crear el token
                const token = jwt.sign(
                    { id: result.insertId, email },
                    process.env.jwt_secret,
                    { expiresIn: '1h' }
                );

                // Verificar que el token se generó
                if (!token) {
                    console.error('No se pudo generar el token');
                    return res.status(500).send('Error al generar el token');
                }

                console.log('Usuario registrado con ID:', result.insertId);
                console.log('Token generado:', token);

                res.status(201).json({ token });
            }
        );
    } catch (error) {
        console.error('Error del servidor en /register:', error);
        res.status(500).send('Error del servidor');
    }
});

// LOGIN
router.post('/login', async (req, res) => {
    const { email, password } = req.body;

    bd.query('SELECT * FROM usuarios WHERE email = ?', [email], async (err, result) => {
        if (err) {
            console.error('❌ Error en la consulta del login:', err);
            return res.status(500).send('Error en el servidor');
        }

        if (result.length === 0) {
            console.warn('Usuario no encontrado:', email);
            return res.status(404).send('Usuario no encontrado');
        }

        const user = result[0];
        const valid = await bcrypt.compare(password, user.password);

        if (!valid) {
            console.warn('Contraseña incorrecta para:', email);
            return res.status(401).send('Contraseña incorrecta');
        }

        const token = jwt.sign(
            { id: user.id, email: user.email },
            process.env.jwt_secret,
            { expiresIn: '1h' }
        );

        if (!token) {
            console.error('No se pudo generar el token de login');
            return res.status(500).send('Error al generar el token');
        }

        console.log('Login exitoso para:', email);
        console.log('Token generado:', token);

        res.json({ token });
    });
});

module.exports = router;
