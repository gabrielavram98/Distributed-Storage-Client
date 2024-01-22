package proiectdiz.Service;

import proiectdiz.Config.WebClientConfig;
import proiectdiz.Log.Log;
import proiectdiz.Model.DataFormat.SAE_Masters;
import proiectdiz.Model.DataFormat.SAE_Slaves;
import proiectdiz.Model.DataFormat.ShareJSON;
import proiectdiz.Sender.SenderService;

public class ShareSender extends Thread {
    private ShareJSON share;
   private  int iterator;
    private String action;

    public ShareSender(ShareJSON share, int i,String action){
        this.share=share;
        this.iterator=i;
        this.action=action;
    }

    public void run(){


        WebClientConfig webconfig= new WebClientConfig();
        SenderService sender= new SenderService( webconfig.webClientBuilder());
        String response=sender.sendJsonToReceiver(share.toJSON(), "/api/server"+String.valueOf(iterator+1)+action);
        System.out.println(response);
        Log.InfoLog(response);

    }

}
