package rsaa;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;

public class VentanaDescifrado extends JFrame {

    RSAal rsa;
    JTextField campoTextoCifrado;
    JTextField campoDManual;
    JTextArea salidaDescifrada;

    public VentanaDescifrado(RSAal rsaRef) {
        setTitle("Descifrado RSA");
        setSize(500, 350);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.rsa = rsaRef;

        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new GridLayout(5, 1));

        campoTextoCifrado = new JTextField();
        campoDManual = new JTextField();

        panelEntrada.add(new JLabel("Texto cifrado (números separados por espacio):"));
        panelEntrada.add(campoTextoCifrado);

        panelEntrada.add(new JLabel("Introduce el valor de d:"));
        panelEntrada.add(campoDManual);

        JButton botonDescifrar = new JButton("Descifrar");
        panelEntrada.add(botonDescifrar);

        salidaDescifrada = new JTextArea(5, 40);
        salidaDescifrada.setEditable(false);

        botonDescifrar.addActionListener(e -> {
            String cifradoTexto = campoTextoCifrado.getText().trim();
            String valorDTexto = campoDManual.getText().trim();

            if (!cifradoTexto.isEmpty() && !valorDTexto.isEmpty()) {
                try {
                    BigInteger dManual = new BigInteger(valorDTexto);
                    String[] partes = cifradoTexto.split(" ");
                    BigInteger[] cifrado = new BigInteger[partes.length];
                    for (int i = 0; i < partes.length; i++) {
                        cifrado[i] = new BigInteger(partes[i]);
                    }

                    // Usar valor d manual para descifrar
                    BigInteger[] descifrado = new BigInteger[cifrado.length];
                    for (int i = 0; i < cifrado.length; i++) {
                        descifrado[i] = cifrado[i].modPow(dManual, rsa.n);
                    }

                    char[] texto = new char[descifrado.length];
                    for (int i = 0; i < descifrado.length; i++) {
                        texto[i] = (char) descifrado[i].intValue();
                    }

                    salidaDescifrada.setText(new String(texto));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error en formato de entrada:\n" + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa el texto cifrado y el valor de d.");
            }
        });

        add(panelEntrada, BorderLayout.NORTH);
        add(new JScrollPane(salidaDescifrada), BorderLayout.CENTER);
    }
}
