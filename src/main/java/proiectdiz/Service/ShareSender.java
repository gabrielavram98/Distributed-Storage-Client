package proiectdiz.Service;

import proiectdiz.Config.WebClientConfig;
import proiectdiz.Log.Log;
import proiectdiz.Model.DataFormat.SAE_Masters;
import proiectdiz.Model.DataFormat.SAE_Slaves;
import proiectdiz.Model.DataFormat.ShareJSON;
import proiectdiz.Sender.SenderService;

public class ShareSender extends Thread {

    public void run(ShareJSON shares_to_send, int i){


        WebClientConfig webconfig= new WebClientConfig();
        SenderService sender= new SenderService( webconfig.webClientBuilder());
        String response=sender.sendJsonToReceiver(shares_to_send.toJSON(), "/api/server/"+String.valueOf(i+1));
        Log.InfoLog(response);

    }

}
