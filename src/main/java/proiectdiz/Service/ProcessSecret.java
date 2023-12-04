package proiectdiz.Service;

import java.util.List;

public class ProcessSecret {
    public static void Process(byte[] secret){
        List<String> parts= SecretDevider.Devider(secret);

    }
}
