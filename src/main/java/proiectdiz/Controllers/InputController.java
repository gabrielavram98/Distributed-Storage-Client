package proiectdiz.Controllers;

import jakarta.servlet.http.HttpServletRequest;
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
/*
    @GetMapping("/home")
    public String index(Model model){
        model.addAttribute("message", "Hello, Thymeleaf!");
        return "index";
    }


    @PostMapping("/proceseazaFormular")
    public String proceseazaFormular(HttpServletRequest request) {
        String continutTextbox = request.getParameter("numeTextbox");
        try{
            Log.TraceLog(continutTextbox);

            HttpStatus status=RequestHandler.Handle(continutTextbox);
        return "index";
        }
        catch(Exception e){
            Log.ErrorLog(e.getMessage());
            return "index";
        }
        // Acțiuni cu conținutul textbox-ului (de exemplu, salvare într-o bază de date)
       // System.out.println("Conținutul introdus: " + continutTextbox);
        //return "paginaDeRezultate";
    }

 */

}

