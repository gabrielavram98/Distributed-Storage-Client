package proiectdiz.SenderServices;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import proiectdiz.Config.WebClientConfig;
import proiectdiz.Log.Log;
import proiectdiz.Model.DataFormat.ShareJSON;
import proiectdiz.SenderServices.SenderService;

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
        HttpStatusCode response=sender.sendJsonToReceiver(share.toJSON(), "/api/server"+String.valueOf(iterator+1)+action);

        System.out.println(response);
        Log.InfoLog(response.toString());

    }

}
