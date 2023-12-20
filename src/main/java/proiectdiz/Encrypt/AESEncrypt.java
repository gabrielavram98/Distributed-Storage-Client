package proiectdiz.Encrypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESEncrypt {
     private byte[] key;
     private byte[] toEncrypt;
    public AESEncrypt(String key, String toEncrypt){
        this.key=key.getBytes(StandardCharsets.UTF_8);
        this.toEncrypt=toEncrypt.getBytes(StandardCharsets.UTF_8);
    }
    public byte[] Encrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("AES");
        SecretKey secret_key= convertBytesToSecretKey(key);
        cipher.init(Cipher.ENCRYPT_MODE, secret_key);
        return cipher.doFinal(toEncrypt);


    }
    private static SecretKey convertBytesToSecretKey(byte[] keyBytes) {
        // Create a SecretKey using SecretKeySpec
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
    }
}
