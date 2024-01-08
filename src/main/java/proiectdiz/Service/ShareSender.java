package proiectdiz.Service;

import proiectdiz.Config.WebClientConfig;
import proiectdiz.Log.Log;
import proiectdiz.Model.DataFormat.SAE_Masters;
import proiectdiz.Model.DataFormat.SAE_Slaves;
import proiectdiz.Model.DataFormat.ShareJSON;
import proiectdiz.Sender.SenderService;

public class ShareSender extends Thread {
    ShareJSON share;
    int iterator;

    public ShareSender(ShareJSON share, int i){
        this.share=share;
        this.iterator=i;
    }

    public void run(){


        WebClientConfig webconfig= new WebClientConfig();
        SenderService sender= new SenderService( webconfig.webClientBuilder());
        String response=sender.sendJsonToReceiver(share.toJSON(), "/api/server"+String.valueOf(iterator+1));
        System.out.println(response);
        Log.InfoLog(response);

    }

}
