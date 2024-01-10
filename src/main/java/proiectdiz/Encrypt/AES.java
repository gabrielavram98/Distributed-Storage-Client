package proiectdiz.Encrypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public class AES {

    private byte[] key;
    private byte[] Text;
    private String algorithm;
    private IvParameterSpec iv;

    public AES(String key, String Text ){
        this.key=key.getBytes(StandardCharsets.UTF_8);
        this.Text=Text.getBytes(StandardCharsets.UTF_8);
        this.algorithm="AES/CBC/PKCS5Padding";
        this.iv=_generate_IV();
    }
    public String getIV(){
        return ivParameterSpecToString(this.iv);
    }

    private static String ivParameterSpecToString(IvParameterSpec ivParameterSpec) {
        byte[] ivBytes = ivParameterSpec.getIV();
        return Base64.getEncoder().encodeToString(ivBytes);
    }
    public void setIv(IvParameterSpec iv) {
        this.iv = iv;
    }


    public  String Encrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        SecretKey secret_key= convertBytesToSecretKey(this.key);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE,secret_key, this.iv);
        byte[] cipherText = cipher.doFinal(Text);
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    public String Decrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        SecretKey secret_key= convertBytesToSecretKey(this.key);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE,secret_key, this.iv);
        byte[] cipherText = cipher.doFinal(Text);
        return Base64.getEncoder()
                .encodeToString(cipherText);

    }
    private static SecretKey convertBytesToSecretKey(byte[] keyBytes) {
        // Create a SecretKey using SecretKeySpec
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
    }


    public   IvParameterSpec _generate_IV() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }



    public  String decrypt(  String cipherText, SecretKey key,
                             IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

}
