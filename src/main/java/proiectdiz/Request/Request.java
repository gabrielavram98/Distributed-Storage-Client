package proiectdiz.Request;

import java.util.UUID;

public class Request {
    private String Request_ID;
    private String Client_ID;
    private String Document_ID;

    private void AddToDatabase(){

    }

    public Request(String client_ID){
        this.Client_ID=client_ID;
        Request_ID= UUID.randomUUID().toString();
        Document_ID= UUID.randomUUID().toString();
    }
}
