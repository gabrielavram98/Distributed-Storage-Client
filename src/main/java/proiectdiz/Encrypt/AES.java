package proiectdiz.Encrypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AES {
     private byte[] key;
     private byte[] toEncrypt;
    public AES(String key, String toEncrypt){
        this.key=key.getBytes(StandardCharsets.UTF_8);
        this.toEncrypt=toEncrypt.getBytes(StandardCharsets.UTF_8);
    }
    public byte[] Encrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
/*
        Cipher cipher = Cipher.getInstance("AES");
        Cipher cipher2 = Cipher.getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE, secret_key);
        cipher2.init(Cipher.DECRYPT_MODE, secret_key);
        byte[] encrypt=cipher.doFinal(toEncrypt);
        byte[] decrypt=cipher2.doFinal(new String(encrypt,StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8));
        String dec= new String(decrypt,StandardCharsets.UTF_8);
        //System.out.println(new String(encrypt,StandardCharsets.UTF_8));
        return cipher.doFinal(toEncrypt);

 */
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secret_key= convertBytesToSecretKey(key);

        IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));

        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        return cipher.doFinal(data.getBytes("UTF-8"));


    }
    private static SecretKey convertBytesToSecretKey(byte[] keyBytes) {
        // Create a SecretKey using SecretKeySpec
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
    }

    private static byte[] generateRandomIV() {

        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        return iv;
    }
}
