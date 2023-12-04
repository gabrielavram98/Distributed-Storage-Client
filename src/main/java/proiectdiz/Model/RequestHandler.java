package proiectdiz.Model;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Helpers.ValidationCheck;
import proiectdiz.Service.MACAppender;
import proiectdiz.Service.ProcessSecret;

import java.util.ArrayList;

public class RequestHandler {

    public static HttpStatus Handle(String requestBody) throws Exception {
        JsonNode requestBodyJSON= JsonHandler.StringToJson(requestBody);
        if(ValidationCheck.Validate(requestBodyJSON)!=0){

            throw new Exception("Error in Validating the request"+requestBody);
        }
        int secretLenght=requestBodyJSON.get("Data").get("DataContent").toString().length();
        assert requestBodyJSON != null;
        byte[] mac=MACAppender.CreateMAC(requestBodyJSON);
        byte[] stringSecret=requestBodyJSON.get("Data").get("DataContent").toString().getBytes();
        byte[] secret= new byte[32+secretLenght];
        System.arraycopy(mac,0,secret,0,mac.length);

        System.arraycopy(stringSecret,0,secret,mac.length,stringSecret.length);

               //MACAppender.CreateMAC(requestBodyJSON)+requestBodyJSON.get("Data").get("DataContent").toString().getBytes();
        ProcessSecret.Process(secret);
        return HttpStatus.ACCEPTED;

    }




}
