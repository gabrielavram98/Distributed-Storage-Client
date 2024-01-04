package proiectdiz.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiectdiz.Log.Log;
import proiectdiz.Model.RequestHandler;

@Controller
public class InputController {

    @PostMapping("/store")
    public HttpStatus hello(@RequestBody String requestBody) {
        try{
            Log.TraceLog(requestBody);


            return RequestHandler.Handle(requestBody);
        }
        catch(Exception e){
            Log.ErrorLog(e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }




    }

    @GetMapping("/home")
    public String index(){
        return "index";
    }

}

