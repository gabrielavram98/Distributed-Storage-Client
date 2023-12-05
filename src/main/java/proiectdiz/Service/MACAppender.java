package proiectdiz.Service;

import com.fasterxml.jackson.databind.JsonNode;
import proiectdiz.Helpers.PasswordGenerator;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
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
        return mac.doFinal(DataContent.toString().getBytes(StandardCharsets.UTF_8));

    }

}
