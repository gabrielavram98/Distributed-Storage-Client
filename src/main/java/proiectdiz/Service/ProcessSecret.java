package proiectdiz.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

public class ProcessSecret {
    public static void Process(byte[] secret){
        SecretDevider devider= new SecretDevider();
        Polynom parts= devider.Devide(secret);



        Lagrange lag= new Lagrange(parts.getX(), parts.getY(),parts.getP(),parts.getK());
        BigInteger reconstructed=lag.lagrangeInterpolation();
        byte[] reconstructedBytes=reconstructed.toByteArray();
    //    byte[] reconstructed2=reconstructed.subtract(parts.getP()).toByteArray();
        String convertedString = new String(reconstructedBytes, StandardCharsets.UTF_8);
        System.out.println(convertedString);

       // String convertedString2 = new String(reconstructed2, StandardCharsets.UTF_8);
      // System.out.println(convertedString2);

    }

}
