package proiectdiz.Service;


import com.sun.source.tree.ReturnTree;
import javafx.scene.control.SplitPane;
import proiectdiz.Encrypt.AES;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Model.DataFormat.ShareJSON;
import proiectdiz.Model.KeyHolder;
import proiectdiz.Model.Properties;
import proiectdiz.Model.QuantecKey;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ProcessSecret {
    public static List<String> Process(byte[] secret, BigInteger p) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, ExecutionException, InterruptedException, InvalidAlgorithmParameterException {


        List<String> uuid_list= new ArrayList<>();

        SecretDevider devider= new SecretDevider(p);
        int parts_lenght=(secret.length/64)+1;
        Polynom[] parts= new Polynom[parts_lenght];
        int counter=0;
        for(int i=0;i<secret.length;i+=64){
            int endIndex=Math.min(i+64,secret.length);
            byte[] share= new byte[endIndex-i];
            System.arraycopy(secret,i,share,0,share.length);
            parts[counter]=devider.Devide(share);


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
        ShareManager manager= new ShareManager();
        manager.SendShares(_sharesJSON);

    }


}
