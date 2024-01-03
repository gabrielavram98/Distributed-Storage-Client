package proiectdiz.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import proiectdiz.Config.WebClientConfig;
import proiectdiz.Encrypt.AESEncrypt;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Helpers.LockEelement;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ProcessSecret {
    public static void Process(byte[] secret) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, ExecutionException, InterruptedException {
        SecretDevider devider= new SecretDevider();
        Polynom parts= devider.Devide(secret);
        String[] shares= JsonHandler.BodyBuilder(parts);

        //TODO: CLASA DE PRIMIT CHEI SI DE TRIMIS PARTILE
        /*
        for(int i=0;i<parts.getY().length;i++){
            WebClientConfig keySenderWebConfig= new WebClientConfig();
            KeyRequestService _keyRequestService= new KeyRequestService(keySenderWebConfig.webClientBuilder());
            ObjectMapper objectMapper= new ObjectMapper();
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("number","1");
            node.put("size",1024);
            String key_uuid= UUID.randomUUID().toString();
            LockEelement.LockMethod();
            String json= node.toPrettyString();
            int iterator=i;
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                // Simulate an asynchronous task
                _keyRequestService.getKeys(json, "/"+SAE_Masters.values()[iterator]+"/"+"/api/v1/keys/"+ SAE_Slaves.values()[iterator]+"/", key_uuid);
                return "Task completed";
            });


            try {
                String result = future.get();
                System.out.println("Result: " + result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
          //  _keyRequestService.getKeys(node.toPrettyString(), "/"+SAE_Masters.values()[i]+"/"+"/api/v1/keys/"+ SAE_Slaves.values()[i]+"/", key_uuid);

            QuantecKey key= KeyHolder.getKeyByUUID(key_uuid);
            System.out.println("Acesta este ID UL:"+key.get_keyId());
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


         */
    }

}
