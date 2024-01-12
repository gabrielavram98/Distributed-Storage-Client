package proiectdiz.Model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.http.HttpStatus;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Helpers.PasswordGenerator;
import proiectdiz.Helpers.ValidationCheck;
import proiectdiz.Service.BitOperator;
import proiectdiz.Service.MACAppender;
import proiectdiz.Service.ProcessSecret;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

import java.nio.charset.StandardCharsets;

import static java.lang.Character.reverseBytes;

public class RequestHandler {
    private static String uuid="";

    public static HttpStatus Handle(String requestBody) throws Exception {
        JsonNode requestBodyJSON= JsonHandler.StringToJson(requestBody);
        if(ValidationCheck.Validate(requestBodyJSON, "src\\main\\resources\\RequestSchema.json")!=0){

            throw new Exception("Error in Validating the request"+requestBody);
        }
        int secretLenght=requestBodyJSON.get("Data").get("DataContent").toString().length();
        assert requestBodyJSON != null;
        byte[] mac=MACAppender.CreateMAC(requestBodyJSON);
        byte[] stringSecret=requestBodyJSON.get("Data").get("DataContent").toString().getBytes(StandardCharsets.UTF_8);
        byte[] secret= new byte[33+secretLenght];

        System.arraycopy(mac,0,secret,1,mac.length);

        System.arraycopy(stringSecret,0,secret,mac.length,stringSecret.length);


        //ProcessSecret.Process(secret,);
        return HttpStatus.ACCEPTED;

    }


    public static void Handle(String requestBody,String source) throws Exception {
        if(!ValidationCheck.isValidAESKey(requestBody) && !ValidationCheck.isValidRSAkey(requestBody)){
            throw new Exception("Error in Validating the request.Not a valid key."+requestBody);
        }
        SecretKey password= PasswordGenerator.GeneratePassword();

        byte[] secret= MACAppender.AppendMAC(requestBody,password);

        byte[] secret_b64=Base64.getEncoder().encode(secret);
        BigInteger p= BitOperator.generatePrimeP(512);
        List<String> uuid_list=ProcessSecret.Process(secret_b64,p);
        uuid=UUID.randomUUID().toString();
        //TODO: Adaugare in baza de date+ prelucrare la partea de storage.


    }
    public static String returnUUID(){
        String toreturn="";
        if(!uuid.equals("")){
            toreturn=uuid;
            uuid="";
        }
        return toreturn;
    }




}
