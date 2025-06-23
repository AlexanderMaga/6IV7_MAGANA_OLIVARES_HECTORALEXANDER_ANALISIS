package rsaa;

import java.math.BigInteger;
import java.util.Random;

public class RSAal {
    int tamPrimo;
    public BigInteger n, p, q;   
    public BigInteger fi;   
    public BigInteger e, d;
 
    public RSAal(int tamPrimo){
        this.tamPrimo = tamPrimo;
    }

    public void generarPrimos(){
        p = new BigInteger(tamPrimo, 10, new Random());
        do {
            q = new BigInteger(tamPrimo, 10, new Random());
        } while(q.compareTo(p)==0);
    }

    public void generarClaves(){
        n = p.multiply(q);
        fi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        do {
            e = new BigInteger(2 * tamPrimo, new Random());
        } while ((e.compareTo(fi) != -1) || (e.gcd(fi).compareTo(BigInteger.ONE) != 0));

        d = e.modInverse(fi);
    }

    public BigInteger[] cifrar(String mensaje){
        byte[] bytes = mensaje.getBytes();
        BigInteger[] cifrado = new BigInteger[bytes.length];

        for(int i = 0; i < bytes.length; i++){
            cifrado[i] = BigInteger.valueOf(bytes[i]).modPow(e, n);
        }

        return cifrado;
    }

    public String descifrar(BigInteger[] cifrado){
        char[] descifrado = new char[cifrado.length];

        for(int i = 0; i < cifrado.length; i++){
            descifrado[i] = (char) cifrado[i].modPow(d, n).intValue();
        }

        return new String(descifrado);
    }
}
