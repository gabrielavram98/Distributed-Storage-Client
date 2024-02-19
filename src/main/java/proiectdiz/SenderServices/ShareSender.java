package proiectdiz.SenderServices;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import proiectdiz.Config.WebClientConfig;
import proiectdiz.Helpers.Properties;
import proiectdiz.Log.Log;
import proiectdiz.Model.DataFormat.ShareJSON;
import proiectdiz.SenderServices.SenderService;

public class ShareSender extends Thread {
    private ShareJSON share;
   private  int iterator;
    private String action;
    private String server;

    public ShareSender(ShareJSON share, int i,String action,String server){
        this.share=share;
        this.iterator=i;
        this.action=action;
        this.server=server;
    }

    public void run(){


        WebClientConfig webconfig= new WebClientConfig();
        SenderService sender= new SenderService( webconfig.webClientBuilder());
        HttpStatusCode response=sender.sendJsonToReceiver(share.toJSON(), "/api/"+server+action);
        System.out.println(share.toJSON());


        System.out.println(response);
        Log.InfoLog(response.toString());

    }

}
