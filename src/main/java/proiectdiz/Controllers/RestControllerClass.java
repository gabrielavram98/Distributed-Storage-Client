package proiectdiz.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import proiectdiz.Log.Log;
import proiectdiz.Service.RequestHandler;

@RestController
public class RestControllerClass {
    @PostMapping("/gatherShares1")
    public HttpStatus Receive_shares_from_servers1(@RequestBody String shareBody){
        try{
            System.out.println("\n\n\nAM PRIMIT DE LA SERVERUL 1");
            Log.TraceLog(shareBody);
            RequestHandler.AddSharesToHolder(shareBody);
            return HttpStatus.ACCEPTED;
        }
        catch(Exception e){
            Log.ErrorLog(e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("/gatherShares2")
    public HttpStatus Receive_shares_from_servers2(@RequestBody String shareBody){
        try{
            System.out.println("\n\n\nAM PRIMIT DE LA SERVERUL 2");
            Log.TraceLog(shareBody);
            RequestHandler.AddSharesToHolder(shareBody);
            return HttpStatus.ACCEPTED;
        }
        catch(Exception e){
            Log.ErrorLog(e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
    @PostMapping("/gatherShares3")
    public HttpStatus Receive_shares_from_servers3(@RequestBody String shareBody){
        try{
            System.out.println("\n\n\nAM PRIMIT DE LA SERVERUL 3");
            Log.TraceLog(shareBody);
            RequestHandler.AddSharesToHolder(shareBody);
            return HttpStatus.ACCEPTED;
        }
        catch(Exception e){
            Log.ErrorLog(e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
    @PostMapping("/gatherShares4")
    public HttpStatus Receive_shares_from_servers4(@RequestBody String shareBody){
        try{
            System.out.println("\n\n\nAM PRIMIT DE LA SERVERUL 4");
            Log.TraceLog(shareBody);
            RequestHandler.AddSharesToHolder(shareBody);
            return HttpStatus.ACCEPTED;
        }
        catch(Exception e){
            Log.ErrorLog(e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
    @PostMapping("/gatherShares5")
    public HttpStatus Receive_shares_from_servers5(@RequestBody String shareBody){
        try{
            System.out.println("\n\n\nAM PRIMIT DE LA SERVERUL 5");
            Log.TraceLog(shareBody);
            RequestHandler.AddSharesToHolder(shareBody);
            return HttpStatus.ACCEPTED;
        }
        catch(Exception e){
            Log.ErrorLog(e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
    @PostMapping("/gatherShares6")
    public HttpStatus Receive_shares_from_servers6(@RequestBody String shareBody){
        try{
            System.out.println("\n\n\nAM PRIMIT DE LA SERVERUL 6");
            Log.TraceLog(shareBody);
            RequestHandler.AddSharesToHolder(shareBody);
            return HttpStatus.ACCEPTED;
        }
        catch(Exception e){
            Log.ErrorLog(e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
    @PostMapping("/gatherShares7")
    public HttpStatus Receive_shares_from_servers7(@RequestBody String shareBody){
        try{
            System.out.println("\n\n\nAM PRIMIT DE LA SERVERUL 7");
            Log.TraceLog(shareBody);
            RequestHandler.AddSharesToHolder(shareBody);
            return HttpStatus.ACCEPTED;
        }
        catch(Exception e){
            Log.ErrorLog(e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
