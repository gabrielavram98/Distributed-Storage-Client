package proiectdiz.Service;


import com.fasterxml.jackson.databind.JsonNode;
import com.sun.source.tree.ReturnTree;
import javafx.scene.control.SplitPane;
import org.springframework.boot.context.properties.bind.BindHandler;
import proiectdiz.Encrypt.AES;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Log.Log;
import proiectdiz.Model.DataFormat.ShareJSON;
import proiectdiz.Model.KeyHolder;
import proiectdiz.Model.Properties;
import proiectdiz.Model.QuantecKey;
import proiectdiz.Model.ShareHolder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ProcessSecret {
    public static List<String> Process(byte[] secret, BigInteger p) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, ExecutionException, InterruptedException, InvalidAlgorithmParameterException {


        List<String> uuid_list= new ArrayList<>();

        SecretDevider devider= new SecretDevider(p);
        int parts_lenght=(secret.length/64);
        if((secret.length%64)!=0){
            parts_lenght++;
        }
        Polynom[] parts= new Polynom[parts_lenght];
        int counter=0;
        for(int i=0;i<secret.length;i+=64){
            int endIndex=Math.min(i+64,secret.length);
            byte[] share= new byte[endIndex-i];
            System.arraycopy(secret,i,share,0,share.length);

            System.out.println(new String(share, StandardCharsets.UTF_8));
            ///byte[] what="wiefbwiefbwenfw".getBytes();
            BigInteger what_big= new BigInteger(share);

            parts[counter]=devider.Devide(share);
            Polynom poly=devider.Devide(share);
            //Lagrange lag= new Lagrange( parts[counter].getX(),parts[counter].getY() ,p,Properties.getL());
            Lagrange lag= new Lagrange( poly.getX(),poly.getY() ,p,Properties.getL());
            BigInteger reconstructed=lag.lagrangeInterpolation();
           // if(what_big.signum()<0){
            //    byte[] reconstructed2=reconstructed.subtract(p).toByteArray();
            //    String convertedString2 = new String(reconstructed2, StandardCharsets.UTF_8);
            //    System.out.println(convertedString2);
           // }
           // else{
                byte[] reconstructedBytes=reconstructed.toByteArray();
                String convertedString = new String(reconstructedBytes, StandardCharsets.UTF_8);
                System.out.println(convertedString);
           // }

            counter++;


        }
        DivideAndSend(parts);
        for(int i=0;i<parts.length;i++){
            uuid_list.add(parts[i].getUUID());
        }
        counter=0;
        return uuid_list;






/*

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

    private static void DivideAndSend(  Polynom[] parts) throws InterruptedException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {


        String[] shares= JsonHandler.BodyBuilder(parts );
        String[] key_ids_UUID= KeyHolder.getKeysUUID();
        //QuantecKey[] keys= new QuantecKey[Properties.getN()];
        ShareJSON[] _sharesJSON= new ShareJSON[Properties.getN()];
        for(int i=0;i<Properties.getN();i++){
            QuantecKey key_= KeyHolder.getKeyByUUID(key_ids_UUID[i]);
            AES _encryptor= new AES(key_.getKey(),shares[i]);
            String _encrypted_=_encryptor.Encrypt();
            ShareJSON sharejson= new ShareJSON(_encrypted_,key_.get_keyId(),_encryptor.getIV());
            _sharesJSON[i]=sharejson;
        }

        ShareManager.SendShares(_sharesJSON);

    }

    public static void SendDownloadRequest(List<String> uuids) throws InterruptedException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String request=JsonHandler.CreateDownloadRequest(uuids);
        String[] key_ids_UUID= KeyHolder.getKeysUUID();
        ShareJSON[] _sharesJSON= new ShareJSON[Properties.getN()];
        for(int i=0;i<Properties.getN();i++){
            QuantecKey key_= KeyHolder.getKeyByUUID(key_ids_UUID[i]);
            AES _encryptor= new AES(key_.getKey(),request);
            String _encrypted_=_encryptor.Encrypt();
            ShareJSON sharejson= new ShareJSON(_encrypted_,key_.get_keyId(),_encryptor.getIV());
            _sharesJSON[i]=sharejson;
        }
        ShareManager.GetShares(_sharesJSON);
    }


    public static JsonNode DecryptShares(String shareString){
        JsonNode share= JsonHandler.StringToJson(shareString);
        String key_ID=share.get("key_ID").asText();
        String encrypted_share=share.get("Shares").asText();
        String IV=share.get("IV").asText();
        byte[] ivBytes = Base64.getDecoder().decode(IV);
        String servernumber=share.get("Server").asText();
        String resp="";
        try {
            KeyRequestorById requestor = new KeyRequestorById(key_ID, servernumber);

            requestor.start();

            requestor.join();
            resp = requestor.getResponse();
            String key = JsonHandler.getKeyFromJson(resp);
            AES aes= new AES(key,encrypted_share);
            aes.setIv(new IvParameterSpec(ivBytes));
            return JsonHandler.StringToJson(aes.Decrypt());
        }
        catch(Exception e){
            Log.ErrorLog(e.getMessage());
            return null;
        }

    }



}
