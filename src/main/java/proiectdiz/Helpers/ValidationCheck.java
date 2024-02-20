package proiectdiz.Helpers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.regex.Pattern;

import com.networknt.schema.ValidationMessage;


public class ValidationCheck {
     public static boolean Validate(JsonNode request, String schema) throws Exception {
         ObjectMapper objectMapper = new ObjectMapper();

         JsonSchema schemaStream = SchemaLoader.LoadSchemaFromPath( schema );
         Set<ValidationMessage> validationMessage = null;
         if (schemaStream != null) {

             validationMessage = schemaStream.validate(request);
         }
         if(validationMessage==null)
         {

             return false;
         }
         return true;


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

        int byteLength = hexKey.length() / 2;
        return byteLength == 16 || byteLength == 24 || byteLength == 32;
    }

    public static boolean isValidUUID(String input){
        String UUID_REGEX = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern UUID_PATTERN = Pattern.compile(UUID_REGEX, Pattern.CASE_INSENSITIVE);
        return UUID_PATTERN.matcher(input).matches();
    }
}
