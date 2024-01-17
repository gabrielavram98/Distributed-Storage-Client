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
                String filename=file.getOriginalFilename();
                InputStream inputStream = file.getInputStream();


                byte[] fileBytes = inputStream.readAllBytes();
                String fileContent = new String(fileBytes);
                RequestHandler.Handle(fileContent,"Browser");
                RequestHandler.setFilename(filename);
                System.out.println(fileContent);

                inputStream.close();
                return "result";

            } catch (Exception e) {
                Log.ErrorLog(e.getMessage());
                model.addAttribute("ErrorMessage",e.getMessage());
                return "error";
            }
        } else {
            return "noFile";
        }



    }



    @GetMapping("/downloadFile")
    public ResponseEntity<String> generateAndDownloadFile( ) {


        String content = RequestHandler.returnUUID();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, RequestHandler.returnFilename());
        return ResponseEntity.ok().headers(headers).body(content);
    }

    @GetMapping("/uploadUUIDpage")
    public String upleadUUIDpage(){
        return "uploadUUIDPage";
    }

    @PostMapping("/uploadUUIDform")
    public String uploadUUIDform(@RequestParam("fileInput") MultipartFile file, Model model){
        if (!file.isEmpty()) {
            try {
                String filename=file.getName();
                InputStream inputStream = file.getInputStream();
                byte[] fileBytes = inputStream.readAllBytes();
                String fileContent = new String(fileBytes);

                RequestHandler.HandleRequestForFileDownload(fileContent);
                System.out.println(fileContent);
                inputStream.close();
                return "resultFileBack";

            } catch (Exception e) {
                Log.ErrorLog(e.getMessage());
                model.addAttribute("ErrorMessage",e.getMessage());
                return "error";
            }
        } else {
            return "noFile";
        }



    }




}

