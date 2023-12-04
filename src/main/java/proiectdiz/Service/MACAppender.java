package proiectdiz.Service;

import com.fasterxml.jackson.databind.JsonNode;
import proiectdiz.Helpers.PasswordGenerator;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MACAppender {

    public static byte[] CreateMAC(JsonNode Request) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey password= PasswordGenerator.GeneratePassword();
        JsonNode Data = Request.get("Data");
        JsonNode DataContent= Data.get("DataContent");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(password);
        byte[] macBytes = mac.doFinal(DataContent.toString().getBytes());
        return macBytes;

    }

}
