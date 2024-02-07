package proiectdiz.SenderServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import proiectdiz.Config.WebClientConfig;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Helpers.ValidationCheck;
import proiectdiz.Model.DataFormat.SAE_Masters;
import proiectdiz.Model.DataFormat.SAE_Slaves;
import proiectdiz.SenderServices.KeyRequestService;

public class KeyRequestor extends Thread{
private String key_uuid;
private int iterator;
public KeyRequestor(String uuid, int i){
    this.key_uuid=uuid;
    this.iterator=i;
}
    public void run(){



            WebClientConfig keySenderWebConfig = new WebClientConfig();
            KeyRequestService _keyRequestService = new KeyRequestService(keySenderWebConfig.webClientBuilder());
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("number", "1");
            node.put("size", 1024);


            String json = node.toPrettyString();


            String response=_keyRequestService.getKeys(json, "/" + SAE_Masters.values()[iterator] + "/" + "/api/v1/keys/" + SAE_Slaves.values()[iterator] + "/enc_keys");


        try {
            ValidationCheck.Validate(JsonHandler.StringToJson(response), "src\\\\main\\\\resources\\\\KeyFormatContainerSchema.json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JsonHandler.ExtractKeyEelements(response, key_uuid);


    }

}
