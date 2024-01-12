package proiectdiz.Helpers;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
//import org.json.JSONObject;
import proiectdiz.Log.Log;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import com.fasterxml.jackson.databind.node.ObjectNode;
import proiectdiz.Model.KeyHolder;
import proiectdiz.Model.Properties;
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
    public static String[] BodyBuilder(Polynom[] parts){

        String[] sharesJSON= new String[Properties.getN()];



        ObjectMapper objectMapper = new ObjectMapper();
         for(int i=0;i<Properties.getN();i++)
         {
            ObjectNode body = JsonNodeFactory.instance.objectNode();
            ArrayNode sharesArray = objectMapper.createArrayNode();

            for( int j=0;j<parts.length;j++){

                ObjectNode share = objectMapper.createObjectNode();
                share.put("GUID",parts[j].getUUID());
                share.put("X",parts[j].getX()[i].toString());
                share.put("Y",parts[j].getY()[i].toString());
                sharesArray.add(share);

            }
            body.put("Shares",sharesArray);
            sharesJSON[i]=body.toString();
    }

        return sharesJSON;

    }
    public static void ExtractKeyEelements( String response, String uuid){
            JsonNode responseJSON= StringToJson(response);

            if(responseJSON!=null){

                JsonNode keys=responseJSON.get("keys");
                for(int i=0;i<keys.size();i++){
                    JsonNode key=keys.get(0);
                    String key_ID=key.get("key_ID").asText();
                    String key_=key.get("key").asText();
                    String key_ID_extension=key.get("key_ID_extension").asText();
                    String key_container_extension= responseJSON.get("key_container_extension").asText();


                    QuantecKey newKey= new QuantecKey(key_ID,key_,key_ID_extension,key_container_extension);
                    KeyHolder.AddKey(newKey, uuid);

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