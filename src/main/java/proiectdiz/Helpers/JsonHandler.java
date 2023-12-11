package proiectdiz.Helpers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import proiectdiz.Log.Log;
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
        ObjectMapper objectMapper = new ObjectMapper();
        for(int i=0;i<parts.getY().length;i++){
            ObjectNode json1 = JsonNodeFactory.instance.objectNode();
            json1.put("GUID","fewefewf");
            ObjectNode json2 = JsonNodeFactory.instance.objectNode();
            json2.put()
        }


    }

}
