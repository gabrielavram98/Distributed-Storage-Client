package proiectdiz.Service;

import proiectdiz.Log.Log;
import proiectdiz.Model.DataFormat.ShareJSON;
import proiectdiz.Model.Properties;

public class ShareManager {

    public ShareManager(){}

    public static void SendShares(ShareJSON[] shares){

        try{
            int n= Properties.getN();
            ShareSender[] senders= new ShareSender[n];
            for(int i=0;i<n;i++){
                ShareSender sender= new ShareSender(shares[i],i,"/store");
                senders[i]=sender;
            }
            for(int i=0;i<n;i++){

                senders[i].start();
            }
            for(int i=0;i<n;i++){

                senders[i].join();
            }

        }catch(Exception e){
            Log.ErrorLog(e.getMessage());
        }

    }

    public static void GetShares(ShareJSON[] request ){
        try{
            int n= Properties.getN();
            ShareSender[] senders= new ShareSender[n];
            for(int i=0;i<n;i++){
                ShareSender sender= new ShareSender(request[i],i,"/get");
                senders[i]=sender;
            }
            for(int i=0;i<n;i++){

                senders[i].start();
            }
            for(int i=0;i<n;i++){

                senders[i].join();
            }

        }catch(Exception e){
            Log.ErrorLog(e.getMessage());
        }
    }


}
