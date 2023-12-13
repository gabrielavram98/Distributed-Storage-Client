package proiectdiz.Helpers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
//import org.json.JSONObject;
import proiectdiz.Log.Log;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

}
