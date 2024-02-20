package proiectdiz.Helpers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
//import org.json.JSONObject;
import proiectdiz.Log.Log;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import proiectdiz.Encrypt.KeyHolder;
import proiectdiz.Encrypt.QuantecKey;
import proiectdiz.ShamirScheme.Polynom;

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
    public static String CreateDownloadRequest(List<String> uuids){
        ObjectMapper objectMapper= new ObjectMapper();
        ObjectNode body = JsonNodeFactory.instance.objectNode();
        ArrayNode guidArray = objectMapper.createArrayNode();
        for(int i=0;i<uuids.size();i++){
            ObjectNode guid = objectMapper.createObjectNode();
            guid.put("GUID",uuids.get(i));
            guidArray.add(guid);
        }
        body.put("GUIDs",guidArray);
        return body.toString();

    }
    public static Optional<String> CreateGetKeyByIdRequest(String _keyID) {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode keyIDsArray = objectMapper.createArrayNode();
        ObjectNode keyIDObject = objectMapper.createObjectNode();
        keyIDObject.put("key_ID", _keyID);
        keyIDsArray.add(keyIDObject);
        rootNode.set("key_IDs", keyIDsArray);
        try {
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            return Optional.of(jsonString);

        } catch (Exception e) {
            Log.ErrorLog(e.getMessage());
            return Optional.empty();
        }


    }
    public static String getKeyFromJson(String response) {

        JsonNode respJSON = StringToJson(response);


        JsonNode keysNode = respJSON.get("keys").get(0);

        String key = keysNode.get("key").asText();
        return key;

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