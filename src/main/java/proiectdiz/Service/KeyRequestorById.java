package proiectdiz.Service;


import proiectdiz.Config.WebClientConfig;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Log.Log;
import proiectdiz.Model.DataFormat.SAE_Masters;
import proiectdiz.Model.DataFormat.SAE_Slaves;
import proiectdiz.Model.Properties;
import proiectdiz.Sender.KeyRequestService;

import java.util.Optional;
import java.util.UUID;

public class KeyRequestorById extends Thread {

    private String key_uuid;
    private String server_number;
    public KeyRequestorById(String uuid, String servernumber){
        this.key_uuid=uuid;
        this.server_number=servernumber;


    }
    private String response;
    public void start(){
        WebClientConfig keySenderWebConfig = new WebClientConfig();
        KeyRequestService _keyRequestorById= new KeyRequestService( keySenderWebConfig.webClientBuilder());
        Optional<String> request= JsonHandler.CreateGetKeyByIdRequest(key_uuid);

        if(request.isPresent()){
            String uuid= UUID.randomUUID().toString();
            response=_keyRequestorById.getKeys(request.get(), "/" + SAE_Masters.values()[Integer.parseInt(server_number)] + "/" + "/api/v1/keys/" + SAE_Slaves.values()[Integer.parseInt(server_number)] + "/dec_keys");

            Log.InfoLog(response);

        }





    }
    public String getResponse(){
        return response;
    }


}
