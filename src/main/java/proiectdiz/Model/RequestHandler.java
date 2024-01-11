package proiectdiz.Model;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Helpers.ValidationCheck;
import proiectdiz.Service.MACAppender;
import proiectdiz.Service.ProcessSecret;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

import static java.lang.Character.reverseBytes;

public class RequestHandler {

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


        ProcessSecret.Process(secret);
        return HttpStatus.ACCEPTED;

    }


    public static String Handle(String requestBody,String source) throws Exception {

        if(!ValidationCheck.isValidAESKey(requestBody) && !ValidationCheck.isValidRSAkey(requestBody)){

            throw new Exception("Error in Validating the request"+requestBody);
        }
       // int secretLenght=requestBodyJSON.get("Data").get("DataContent").toString().length();
       // assert requestBodyJSON != null;
        byte[] mac=MACAppender.CreateMAC(requestBody);
        byte[] stringSecret=requestBody.getBytes(StandardCharsets.UTF_8);
        byte[] secret= new byte[33+requestBody.length()];

        System.arraycopy(mac,0,secret,1,mac.length);

        System.arraycopy(stringSecret,0,secret,mac.length,stringSecret.length);


        ProcessSecret.Process(secret);
        return UUID.randomUUID().toString();

    }




}
