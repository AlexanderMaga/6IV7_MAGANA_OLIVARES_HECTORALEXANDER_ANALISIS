const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const db = require('./bd');

router.post('/register', async (req, res) => {
    const { email, password } = req.body;
    try {
        const hashed = await bcrypt.hash(password, 10);
        db.query(
            'INSERT INTO usuarios (email, password) VALUES (?, ?)',
            [email, hashed],
            (err, result) => {
                if (err) {
                    console.log('ERROR AL REGISTRAR AL USUARIO', err);
                    return res.status(500).send('Error al registrar');
                }
                console.log("Usuario registrado con el ID", result.insertId);
                res.status(200).send('Usuario registrado correctamente');
            }
        );
    } catch (err) {
        console.error('Error en el servidor:', err);
        res.status(500).send('Error del servidor');
    }
});

module.exports = router;
