package rsaa;

import javax.swing.SwingUtilities;

public class RSAa {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaCifrado();
        });
    }
}
