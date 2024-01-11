package proiectdiz.Helpers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import com.networknt.schema.ValidationMessage;


public class ValidationCheck {
     public static int Validate(JsonNode request, String schema) throws Exception {
         ObjectMapper objectMapper = new ObjectMapper();

         JsonSchema schemaStream = SchemaLoader.LoadSchemaFromPath( schema );
         Set<ValidationMessage> validationMessage = null;
         if (schemaStream != null) {

             validationMessage = schemaStream.validate(request);
         }
         if(validationMessage==null)
         {
             throw new Exception("Error validating the request");
         }
         return 0;


     }
     public static boolean isValidRSAkey(String content){
         return isPEMEncodedRSAPrivateKey(content)||isPEMEncodedRSAPublicKey(content);
     }
    private static boolean isPEMEncodedRSAPrivateKey(String content) {
        boolean unencrypted= content.contains("-----BEGIN PRIVATE KEY-----")
                && content.contains("-----END PRIVATE KEY-----");
        boolean encrypted= content.contains("-----BEGIN ENCRYPTED PRIVATE KEY-----")
                && content.contains("-----END ENCRYPTED PRIVATE KEY-----");

        return unencrypted||encrypted;
    }
    private static boolean isPEMEncodedRSAPublicKey(String content) {

        return content.contains("-----BEGIN PUBLIC KEY-----")
                && content.contains("-----END PUBLIC KEY-----");
    }

    public static boolean isValidAESKey(String content) {


        return isHexadecimal(content) && isValidAESKeyLength(content);
    }

    private static boolean isHexadecimal(String input) {

        return input.matches("[0-9a-fA-F]+");
    }

    private static boolean isValidAESKeyLength(String hexKey) {
        // Check if the length of the hexadecimal string corresponds to a valid AES key length
        int byteLength = hexKey.length() / 2; // Each pair of hexadecimal characters represents a byte
        return byteLength == 16 || byteLength == 24 || byteLength == 32; // 128, 192, or 256 bits
    }
}
