package proiectdiz.Model;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Helpers.ValidationCheck;
import proiectdiz.Service.MACAppender;
import proiectdiz.Service.ProcessSecret;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static java.lang.Character.reverseBytes;

public class RequestHandler {

    public static HttpStatus Handle(String requestBody) throws Exception {
        JsonNode requestBodyJSON= JsonHandler.StringToJson(requestBody);
        if(ValidationCheck.Validate(requestBodyJSON)!=0){

            throw new Exception("Error in Validating the request"+requestBody);
        }
        int secretLenght=requestBodyJSON.get("Data").get("DataContent").toString().length();
        assert requestBodyJSON != null;
        byte[] mac=MACAppender.CreateMAC(requestBodyJSON);
        byte[] stringSecret=requestBodyJSON.get("Data").get("DataContent").toString().getBytes(StandardCharsets.UTF_8);
        byte[] secret= new byte[32+secretLenght];
        System.arraycopy(mac,0,secret,0,mac.length);

        System.arraycopy(stringSecret,0,secret,mac.length,stringSecret.length);
       // byte[] secret2= new byte[secret.length-32];
       // System.arraycopy(secret,32,secret2,0,secret.length-32);
       // String secrettoStr= new String(secret2,StandardCharsets.UTF_8);

        ProcessSecret.Process(secret);
        return HttpStatus.ACCEPTED;

    }




}
