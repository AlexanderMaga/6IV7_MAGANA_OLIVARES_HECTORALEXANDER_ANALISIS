package rsaa;


import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;

public class VentanaCifrado extends JFrame {

    RSAal rsa;
    JTextField entradaTexto;
    JTextArea areaSalida;
    JTextArea areaClave;

    public VentanaCifrado() {
        setTitle("Cifrado RSA");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        rsa = new RSAal(8); // máximo 3 dígitos
        rsa.generarPrimos();
        rsa.generarClaves();

        JPanel panelEntrada = new JPanel();
        panelEntrada.add(new JLabel("Texto a cifrar:"));
        entradaTexto = new JTextField(20);
        panelEntrada.add(entradaTexto);
        JButton botonCifrar = new JButton("Cifrar");
        panelEntrada.add(botonCifrar);

        areaClave = new JTextArea(6, 40);
        areaClave.setEditable(false);
        mostrarClaves();

        areaSalida = new JTextArea(5, 40);
        areaSalida.setEditable(false);

        JButton abrirDescifrado = new JButton("Abrir ventana de Descifrado");

        botonCifrar.addActionListener(e -> {
            String texto = entradaTexto.getText();
            if (!texto.isEmpty()) {
                BigInteger[] cifrado = rsa.cifrar(texto);
                StringBuilder sb = new StringBuilder();
                for (BigInteger c : cifrado) {
                    sb.append(c.toString()).append(" ");
                }
                areaSalida.setText(sb.toString().trim());
            }
        });

        abrirDescifrado.addActionListener(e -> {
            new VentanaDescifrado(rsa).setVisible(true);
        });

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.add(new JScrollPane(areaClave));
        panelCentro.add(new JScrollPane(areaSalida));
        panelCentro.add(abrirDescifrado);

        add(panelEntrada, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        setVisible(true);
    }

    private void mostrarClaves() {
        areaClave.setText("Claves generadas:\n");
        areaClave.append("p: " + rsa.p + "\n");
        areaClave.append("q: " + rsa.q + "\n");
        areaClave.append("n: " + rsa.n + "\n");
        areaClave.append("fi: " + rsa.fi + "\n");
        areaClave.append("e: " + rsa.e + "\n");
        areaClave.append("d: " + rsa.d + "\n");
    }
}
