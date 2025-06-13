const mysql = require('mysql2');
require('dotenv').config();
const express = require('express');
const cors = require('cors');


const db = mysql.createConnection({
    host : process.env.bd_host,
    user : process.env.bd_user,
    password : process.env.bd_password,
    database : process.env.bd_name
});

db.connect(err => {
    if(err) throw err;
    console.log("Conectado a la BD en MYSQL");
    console.log("host : " , process.env.BD_HOST);
    console.log("user : " , process.env.BD_NAME);
    console.log("password : " , process.env.BD_PASSWORD);
    console.log("name : " , process.env.BD_NAME);
});

module.exports = db;