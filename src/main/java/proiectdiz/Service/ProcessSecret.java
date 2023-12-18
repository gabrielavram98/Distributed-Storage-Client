package proiectdiz.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.reactive.function.client.WebClient;
import proiectdiz.Config.WebClientConfig;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Sender.KeyRequestService;
import proiectdiz.Sender.SenderService;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

public class ProcessSecret {
    public static void Process(byte[] secret){
        SecretDevider devider= new SecretDevider();
        Polynom parts= devider.Devide(secret);
        String[] shares= JsonHandler.BodyBuilder(parts);

        for(int i=0;i<parts.getY().length;i++){
            WebClientConfig keySenderWebConfig= new WebClientConfig();
            KeyRequestService _keyRequestService= new KeyRequestService(keySenderWebConfig.webClientBuilder());
            ObjectMapper objectMapper= new ObjectMapper();
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("number","3");
            node.put("size",1024);
            _keyRequestService.getKeys(node.asText());
        }

        WebClientConfig webconfig= new WebClientConfig();
        SenderService send= new SenderService( webconfig.webClientBuilder());
        send.sendJsonToReceiver(shares[0]);

        
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
