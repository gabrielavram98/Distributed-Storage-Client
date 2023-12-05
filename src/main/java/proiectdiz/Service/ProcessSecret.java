package proiectdiz.Service;

import java.math.BigInteger;
import java.util.List;

public class ProcessSecret {
    public static void Process(byte[] secret){
        SecretDevider devider= new SecretDevider();
        BigInteger[][] parts= devider.Devide(secret);


    }
}
