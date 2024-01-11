package proiectdiz.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proiectdiz.Log.Log;
import proiectdiz.Model.PropertiesApp;
import proiectdiz.Model.RequestHandler;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class InputController {
    @Autowired
    private PropertiesApp propertiesApp;

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

    @GetMapping("/text-input")
    public String showTextInputForm() {
        return "text-input";
    }

    @PostMapping("/process-text")
    public String processText(@RequestParam("userInput") String userInput, Model model) throws Exception {

        //String processedText = takeInput(userInput);
        String uuid=RequestHandler.Handle(userInput,"Browser");
        model.addAttribute("processedText", uuid);
        return "result";
    }

    @PostMapping("/upload-file")
    public String processFile(@RequestParam("fileInput") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // Get the input stream of the uploaded file
                InputStream inputStream = file.getInputStream();

                // Read the content of the file (you can use different mechanisms based on your needs)
                byte[] fileBytes = inputStream.readAllBytes();
                String fileContent = new String(fileBytes);
                String uuid=RequestHandler.Handle(fileContent,"Browser");
                System.out.println(fileContent);
                // Process or use the file content as needed

                // Close the input stream
                inputStream.close();

                // Redirect to a success page or return a response
                return "result";
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception (e.g., show an error page)
                return "error";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            // Handle the case where no file is uploaded
            return "noFile";
        }



    }

    private String takeInput(String userInput) {

        return "Text procesat: " + userInput;
    }



}

