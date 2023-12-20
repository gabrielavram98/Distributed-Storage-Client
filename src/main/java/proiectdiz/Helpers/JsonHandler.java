package proiectdiz.Helpers;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
//import org.json.JSONObject;
import proiectdiz.Log.Log;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import com.fasterxml.jackson.databind.node.ObjectNode;
import proiectdiz.Model.KeyHolder;
import proiectdiz.Model.QuantecKey;
import proiectdiz.Service.Polynom;

public class JsonHandler {

    public static JsonNode StringToJson(String message){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(message);
        } catch(Exception e){
            Log.ErrorLog("Error at converting to json: "+e.getMessage());
            return null;
        }

    }
    public static String JsonToString(JsonNode request) throws JsonProcessingException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(request);
        }
        catch(Exception e){
            Log.ErrorLog("Error at converting to json:"+e.getMessage());
            return null;
        }

    }
    public static String[] BodyBuilder(Polynom parts ){

        String[] sharesJSON= new String[parts.getY().length];

        UUID uuid = UUID.randomUUID();
        parts.setGuid(uuid);
        ObjectMapper objectMapper = new ObjectMapper();

        for(int i=0;i<parts.getY().length;i++){
            ObjectNode body = JsonNodeFactory.instance.objectNode();
            body.put("GUID",uuid.toString());


            ObjectNode shares = objectMapper.createObjectNode();
            //byte[] x_byte=parts.getX()[i].toByteArray();
            //String x_string = new String(x_byte, StandardCharsets.UTF_8);

            //byte[] y_bytes=parts.getY()[i].toByteArray();
            //String y_string = new String(x_byte, StandardCharsets.UTF_8);
            String x=parts.getX()[i].toString();
            String y=parts.getY()[i].toString();
           shares.put("X",x);
           shares.put("Y",y);
           body.set("Shares", shares);
            sharesJSON[i]=body.toString();



        }
        return sharesJSON;

    }
    public static void ExtractKeyEelements( String response, String destination){
            JsonNode responseJSON= StringToJson(response);
            if(responseJSON!=null){

                JsonNode keys=responseJSON.get("keys");
                for(int i=0;i<keys.size();i++){
                    JsonNode key=keys.get(0);
                    String key_ID=key.get("key_ID").asText();
                    String key_=key.get("key").asText();
                    String key_ID_extension=key.get("key_ID_extension").asText();
                    String key_container_extension= responseJSON.get("key_container_extension").asText();
                    String[] dest= destination.split("/");

                    QuantecKey newKey= new QuantecKey(key_ID,key_,key_ID_extension,key_container_extension,dest[1],dest[dest.length-1]);
                    KeyHolder.AddKey(newKey,dest[1]+"/"+dest[dest.length-1]);

                }
            }



    }
}
 /*
    {
        "keys": [
        {
            "key_ID": "123456",
                "key_ID_extension": "string",
                "key": "string",
                "key_extension": "string"
        }
  ],
        "key_container_extension": "string"
    }
    */