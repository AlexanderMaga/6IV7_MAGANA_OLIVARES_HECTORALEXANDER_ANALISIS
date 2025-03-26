var vigenere = (function () {
    var abc = "abcdefghijklmnopqrstuvwxyz"; 

    var proceso = function (txt, clave, action) {
        var resultado = "";
        var longitud = abc.length;
        var claveExpandida = "";

        for (var i = 0; i < txt.length; i++) {
            claveExpandida += clave[i % clave.length];
        }

     
        for (var i = 0; i < txt.length; i++) {
            var charTexto = txt[i].toLowerCase();
            var charClave = claveExpandida[i].toLowerCase();

            var indexTexto = abc.indexOf(charTexto);
            var indexClave = abc.indexOf(charClave);

            if (indexTexto !== -1) {
                var newIndex;
                if (action) {
                    // Cifrar
                    newIndex = (indexTexto + indexClave) % longitud;
                } else {
                    // Descifrar
                    newIndex = (indexTexto - indexClave + longitud) % longitud;
                }
                resultado += abc[newIndex];
            } else {
                resultado += charTexto; 
            }
        }
        return resultado;
    };

    return {
        encode: function (txt, clave) {
            return proceso(txt, clave, true);
        },
        decode: function (txt, clave) {
            return proceso(txt, clave, false);
        }
    };
})();

function codificar() {
    var texto = document.getElementById("txt").value;
    var clave = document.getElementById("txtclave").value;

    if (texto === "" || clave === "") {
        alert("Por favor, ingrese el texto y la clave.");
        return;
    }

    document.getElementById("respuesta").value = vigenere.encode(texto, clave);
}

function decodificar() {
    var texto = document.getElementById("txt").value;
    var clave = document.getElementById("txtclave").value;

    if (texto === "" || clave === "") {
        alert("Por favor, ingrese el texto cifrado y la clave.");
        return;
    }

    document.getElementById("respuesta").value = vigenere.decode(texto, clave);
}

function reiniciar() {
    document.getElementById("txt").value = "";
    document.getElementById("txtclave").value = "";
    document.getElementById("respuesta").value = "";
}

function copiarTexto() {
    var resultado = document.getElementById("respuesta");
    resultado.select();
    document.execCommand("copy");
    alert("Texto copiado al portapapeles.");
}
