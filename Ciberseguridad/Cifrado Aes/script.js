function showSection(section) {
    document.getElementById('encryptSection').classList.add('hidden');
    document.getElementById('decryptSection').classList.add('hidden');
    document.getElementById(section + 'Section').classList.remove('hidden');
  }
  
  // CIFRADO
  function encryptFile() {
    const file = document.getElementById('fileToEncrypt').files[0];
    const key = document.getElementById('keyToEncrypt').value;
  
    if (!file || !key || key.length < 4) {
      alert('Selecciona un archivo y una clave válida (mínimo 4 caracteres).');
      return;
    }
  
    const reader = new FileReader();
    reader.onload = function(e) {
      const content = e.target.result;
      const encrypted = CryptoJS.AES.encrypt(content, key).toString();
  
      const blob = new Blob([encrypted], { type: 'text/plain' });
      const a = document.createElement('a');
      a.href = URL.createObjectURL(blob);
      a.download = "archivo_cifrado.txt";
      a.click();
    };
    reader.readAsText(file);
  }
  
  // DESCIFRADO
  function decryptFile() {
    const file = document.getElementById('fileToDecrypt').files[0];
    const key = document.getElementById('keyToDecrypt').value;
  
    if (!file || !key || key.length < 4) {
      alert('Selecciona un archivo y una clave válida (mínimo 4 caracteres).');
      return;
    }
  
    const reader = new FileReader();
    reader.onload = function(e) {
      try {
        const encryptedContent = e.target.result;
        const decrypted = CryptoJS.AES.decrypt(encryptedContent, key).toString(CryptoJS.enc.Utf8);
  
        if (!decrypted) throw new Error("Clave incorrecta o archivo no válido.");
  
        const blob = new Blob([decrypted], { type: 'text/plain' });
        const a = document.createElement('a');
        a.href = URL.createObjectURL(blob);
        a.download = "archivo_descifrado.txt";
        a.click();
      } catch (error) {
        alert("Error al descifrar: " + error.message);
      }
    };
    reader.readAsText(file);
  }
  