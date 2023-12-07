package proiectdiz.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

public class ProcessSecret {
    public static void Process(byte[] secret){
        SecretDevider devider= new SecretDevider();
        Polynom parts= devider.Devide(secret);
        Lagrange lag= new Lagrange(parts.getX(), parts.getY(),parts.getP());
        BigInteger reconstructed=lag.lagrangeInterpolation();
        System.out.println(reconstructed.toString());

    }
}
