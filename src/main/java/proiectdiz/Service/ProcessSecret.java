package proiectdiz.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import proiectdiz.Config.WebClientConfig;
import proiectdiz.Encrypt.AESEncrypt;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Model.DataFormat.SAE_Slaves;
import proiectdiz.Model.DataFormat.SAE_Masters;
import proiectdiz.Model.KeyHolder;
import proiectdiz.Model.QuantecKey;
import proiectdiz.Sender.KeyRequestService;
import proiectdiz.Sender.SenderService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.awt.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class ProcessSecret {
    public static void Process(byte[] secret) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        SecretDevider devider= new SecretDevider();
        Polynom parts= devider.Devide(secret);
        String[] shares= JsonHandler.BodyBuilder(parts);

        for(int i=0;i<parts.getY().length;i++){
            WebClientConfig keySenderWebConfig= new WebClientConfig();
            KeyRequestService _keyRequestService= new KeyRequestService(keySenderWebConfig.webClientBuilder());
            ObjectMapper objectMapper= new ObjectMapper();
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("number","1");
            node.put("size",1024);
            String key_uuid= UUID.randomUUID().toString();

            _keyRequestService.getKeys(node.asText(), "/"+SAE_Masters.values()[i]+"/"+"/api/v1/keys/"+ SAE_Slaves.values()[i]+"/", key_uuid);
            QuantecKey key= KeyHolder.getKeyByUUID(key_uuid);
            AESEncrypt _encryptor= new AESEncrypt(key.getKey(),shares[i]);
            byte[] _encrypted_=_encryptor.Encrypt();
            WebClientConfig webconfig= new WebClientConfig();
            SenderService send= new SenderService( webconfig.webClientBuilder());
            String shareToSend = new String(_encrypted_, StandardCharsets.UTF_8);
            node=JsonNodeFactory.instance.objectNode();
            node.put("share",shareToSend);
            send.sendJsonToReceiver(shareToSend,"/server1");

        }



        
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
