package pkg03des;

import java.awt.*;
import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.swing.*;

public class Main extends JFrame {
    private JTextField filePathField;
    private JButton loadButton, encryptButton, decryptButton;
    private JTextArea outputArea;
    private SecretKey secretKey;

    public Main() {
        setTitle("Cifrado DES");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Fondo de la ventana
        getContentPane().setBackground(new Color(128, 0, 128)); 

        JLabel labelArchivo = new JLabel("Archivo:");
        labelArchivo.setForeground(new Color(255, 255, 255)); 
        labelArchivo.setFont(new Font("Arial", Font.BOLD, 14)); 
        
        filePathField = new JTextField(30);

        loadButton = new JButton("Cargar Archivo");
        encryptButton = new JButton("Cifrar");
        decryptButton = new JButton("Descifrar");

        // Colores de los botones
        Color botonColor = new Color(255, 255, 255); 
        Color textoColor = Color.BLACK;

        loadButton.setBackground(botonColor);
        loadButton.setForeground(textoColor);

        encryptButton.setBackground(new Color(255, 255, 255));
        encryptButton.setForeground(new Color(0, 0, 0));
      

        decryptButton.setBackground(new Color(255, 255, 255)); 
        decryptButton.setForeground(new Color(0, 0, 0));

        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(255, 255, 255)); 
        outputArea.setForeground(Color.BLACK);

        add(labelArchivo);
        add(filePathField);
        add(loadButton);
        add(encryptButton);
        add(decryptButton);
        add(new JScrollPane(outputArea));

        loadButton.addActionListener(e -> selectFile());
        encryptButton.addActionListener(e -> encryptFile());
        decryptButton.addActionListener(e -> decryptFile());

        generateKey();
    }

    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            filePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(56);
            secretKey = keyGen.generateKey();
        } catch (Exception e) {
            outputArea.setText("Error generando clave: " + e.getMessage());
        }
    }

    private void encryptFile() {
        processFile(Cipher.ENCRYPT_MODE, "cifrado");
    }

    private void decryptFile() {
        processFile(Cipher.DECRYPT_MODE, "descifrado");
    }

    private void processFile(int mode, String extension) {
        try {
            String filePath = filePathField.getText();
            if (filePath.isEmpty()) {
                outputArea.setText("Seleccione un archivo.");
                return;
            }

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(mode, secretKey);

            File inputFile = new File(filePath);
            File outputFile = new File(filePath + "." + extension);

            FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile);
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
            fis.close();
            cos.close();

            outputArea.setText("Archivo procesado: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
