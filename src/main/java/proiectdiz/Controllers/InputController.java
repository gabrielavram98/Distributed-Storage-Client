package proiectdiz.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proiectdiz.Log.Log;
import proiectdiz.Model.PropertiesApp;
import proiectdiz.Model.RequestHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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



    @PostMapping("/upload-file")
    public String processFile(@RequestParam("fileInput") MultipartFile file, Model model) {
        if (!file.isEmpty()) {
            try {
                // Get the input stream of the uploaded file
                InputStream inputStream = file.getInputStream();

                // Read the content of the file (you can use different mechanisms based on your needs)
                byte[] fileBytes = inputStream.readAllBytes();
                String fileContent = new String(fileBytes);
                RequestHandler.Handle(fileContent,"Browser");
                System.out.println(fileContent);
                // Process or use the file content as needed

                // Close the input stream
                inputStream.close();

                // Redirect to a success page or return a response
               // model.addAttribute("processedText", uuid);
                return "result";

            } catch (Exception e) {
                Log.ErrorLog(e.getMessage());

                model.addAttribute("ErrorMessage",e.getMessage());
                return "error";
            }
        } else {
            // Handle the case where no file is uploaded
            return "noFile";
        }



    }



    @GetMapping("/downloadFile")
    public ResponseEntity<String> generateAndDownloadFile( ) {


        String content = RequestHandler.returnUUID();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=SecredIdentifier.txt");
        return ResponseEntity.ok().headers(headers).body(content);
    }



}

