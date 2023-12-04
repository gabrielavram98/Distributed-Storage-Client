package proiectdiz.Helpers;
import proiectdiz.Log.Log;

import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
public class PasswordGenerator {
    public static SecretKey GeneratePassword() {
        try {

            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecureRandom secureRandom = new SecureRandom();
            keyGen.init(secureRandom);
            return keyGen.generateKey();

        } catch (NoSuchAlgorithmException e) {
            Log.ErrorLog(e.getMessage());
            return null;
        }
    }


}
