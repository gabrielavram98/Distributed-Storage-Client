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
import proiectdiz.Model.DataFormat.ShareJSON;
import proiectdiz.Model.KeyHolder;
import proiectdiz.Model.Properties;
import proiectdiz.Model.QuantecKey;
import proiectdiz.Sender.KeyRequestService;
import proiectdiz.Sender.SenderService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
//import java.awt.*;
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
        String[] key_ids_UUID= KeyHolder.getKeysUUID();
        QuantecKey[] keys= new QuantecKey[Properties.getN()];
        ShareJSON[] _sharesJSON= new ShareJSON[Properties.getN()];
        for(int i=0;i<Properties.getN();i++){
            QuantecKey key_= KeyHolder.getKeyByUUID(key_ids_UUID[i]);
            AESEncrypt _encryptor= new AESEncrypt(key_.getKey(),shares[i]);
            byte[] _encrypted_=_encryptor.Encrypt();
            ShareJSON sharejson= new ShareJSON(new String(_encrypted_, StandardCharsets.UTF_8),key_.get_keyId());
            _sharesJSON[i]=sharejson;
        }
        ShareManager manager= new ShareManager();
        manager.SendShares(_sharesJSON);





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

}
