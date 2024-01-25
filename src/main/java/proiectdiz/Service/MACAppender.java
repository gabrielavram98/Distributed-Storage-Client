package proiectdiz.Service;

import com.fasterxml.jackson.databind.JsonNode;
import proiectdiz.Helpers.PasswordGenerator;
import proiectdiz.Request.Request;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

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
    private static String CreateMAC(String Request,SecretKey password) throws NoSuchAlgorithmException, InvalidKeyException {
        //SecretKey password= PasswordGenerator.GeneratePassword();
        System.out.println("Password:"+ new BigInteger (password.getEncoded()) );
        byte[] keyBytes=password.getEncoded();
        String encodedkey= Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("encodedkey:"+encodedkey);



        byte[] keyBytes2 = Base64.getDecoder().decode(encodedkey);
        SecretKey secretKey = new SecretKeySpec(keyBytes2, 0, keyBytes2.length, "HmacSHA256");
        //SecretKey secretKey = new SecretKeySpec(secretKeyString.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
       // JsonNode Data = Request.get("Data");
       // JsonNode DataContent= Data.get("DataContent");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(password);
        Mac mac2=Mac.getInstance("HmacSHA256");
        mac2.init(secretKey);
        byte[] hash=mac.doFinal(Request.getBytes(StandardCharsets.UTF_8));
        byte[] computedHashBytes = mac2.doFinal(Request.getBytes(StandardCharsets.UTF_8));
        System.out.println("HASH SIZE="+computedHashBytes.length);
        String computedHash = Base64.getEncoder().encodeToString(computedHashBytes);
        String hashString = Base64.getEncoder().encodeToString(hash);

        //




        //mac.init(password);
        return hashString;

    }

    public static byte[] AppendMAC(String Request, SecretKey password) throws NoSuchAlgorithmException, InvalidKeyException {
        String mac= CreateMAC(Request,password);
       String mac_Request=mac+Request;
        return mac_Request.getBytes(StandardCharsets.UTF_8);
    }

    public static boolean VerifyMac(String Request, String encodedkey) throws NoSuchAlgorithmException, InvalidKeyException {

        //byte[] keyBytes=password.getEncoded();
        //String encodedb64key= Base64.getEncoder().encodeToString(keyBytes);
        //byte[] keyBytes=password.getEncoded();
        //String encodedkey= Base64.getEncoder().encodeToString(keyBytes);
        byte[] keyBytes = Base64.getDecoder().decode(encodedkey);
        SecretKey secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");


        Mac mac=Mac.getInstance("HmacSHA256");
        mac.init(secretKey);


        String b64MAC=Request.substring(0,44);
        String RequestString=Request.substring(44);
        byte [] RequestMAC=mac.doFinal(RequestString.getBytes(StandardCharsets.UTF_8));
        String RequestMACb64=Base64.getEncoder().encodeToString(RequestMAC);
        return RequestMACb64.equals(b64MAC);



    }


    public static String HashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] password_bytes= password.getBytes();
        byte[] password_digest= digest.digest(password_bytes);
        return Base64.getEncoder().encodeToString(password_digest);
    }

}
