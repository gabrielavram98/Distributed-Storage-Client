package proiectdiz.Service;

import com.fasterxml.jackson.databind.JsonNode;
import proiectdiz.Helpers.PasswordGenerator;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MACAppender {

    public static byte[] CreateMAC(JsonNode Request) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey password= PasswordGenerator.GeneratePassword();
        System.out.println("Password:"+ new BigInteger (password.getEncoded()) );
        JsonNode Data = Request.get("Data");
        JsonNode DataContent= Data.get("DataContent");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(password);
        return mac.doFinal(DataContent.toString().getBytes(StandardCharsets.UTF_8));

    }
    private static byte[] CreateMAC(String Request,SecretKey password) throws NoSuchAlgorithmException, InvalidKeyException {
        //SecretKey password= PasswordGenerator.GeneratePassword();
        System.out.println("Password:"+ new BigInteger (password.getEncoded()) );
       // JsonNode Data = Request.get("Data");
       // JsonNode DataContent= Data.get("DataContent");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(password);
        return mac.doFinal(Request.getBytes(StandardCharsets.UTF_8));

    }

    public static byte[] AppendMAC(String Request, SecretKey password) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] mac= CreateMAC(Request,password);
        byte[] stringSecret=Request.getBytes(StandardCharsets.UTF_8);
        String req= new String(stringSecret,StandardCharsets.UTF_8);
        byte[] secret= new byte[mac.length+stringSecret.length];
        System.arraycopy(mac,0,secret,0,mac.length);
        System.arraycopy(stringSecret,0,secret,mac.length,stringSecret.length);
        return secret;
    }

}
