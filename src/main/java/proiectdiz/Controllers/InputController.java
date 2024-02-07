package proiectdiz.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proiectdiz.Log.Log;
import proiectdiz.Helpers.Properties;
import proiectdiz.Model.PropertiesApp;
import proiectdiz.Service.RequestHandler;
import proiectdiz.Model.ShareHolder;

import java.io.InputStream;
import java.util.Map;

@Controller
public class InputController {
    @Autowired
    private PropertiesApp propertiesApp;








    @GetMapping("/text-input")
    public String showTextInputForm() {
        return "text-input";
    }



    @PostMapping("/upload-file")
    public String processFile(@RequestParam("fileInput") MultipartFile file, Model model,@RequestParam("n") int n,
                              @RequestParam("l") int l) {
        if (!file.isEmpty()) {
            try {
                Properties.setN(String.valueOf(n));
                Properties.setL(String.valueOf(l));
                String filename=file.getOriginalFilename();
                InputStream inputStream = file.getInputStream();


                byte[] fileBytes = inputStream.readAllBytes();
                String fileContent = new String(fileBytes);
                RequestHandler.Handle(fileContent,filename);
               // RequestHandler.setFilename(filename);
                //System.out.println(fileContent);

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
    public ResponseEntity<byte[]> generateAndDownloadFile( ) {
        try{
            String content = RequestHandler.returnData();
            String filename = RequestHandler.returnFilenameUID();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);

            return new ResponseEntity<>(content.getBytes(), headers, HttpStatus.OK);
        }catch (Exception e){
            Log.ErrorLog(e.getMessage());
            return null;
        }

    }

    @GetMapping("/uploadUUIDpage")
    public String upleadUUIDpage(){
        System.out.println("A intrat in /uploadUUIDPage");
        return "uploadUUIDPage";
    }

    @PostMapping("/uploadUUIDform")
    public String uploadUUIDform(@RequestParam("fileInput") MultipartFile file, Model model){
        if (!file.isEmpty()) {
            try {
                System.out.println("A intrat in /uploadUUIDForm");
                InputStream inputStream = file.getInputStream();
                byte[] fileBytes = inputStream.readAllBytes();
                String fileContent = new String(fileBytes);

                RequestHandler.HandleRequestForFileDownload(fileContent);

                inputStream.close();
                return "redirect:download";

            } catch (Exception e) {
                Log.ErrorLog(e.getMessage());
                model.addAttribute("ErrorMessage",e.getMessage());
                return "error";
            }
        } else {
            return "noFile";
        }

    }

    @GetMapping("/download")
    public String download(){
        System.out.println("A fost redirectionat in /download");
        return "resultFileBack";
    }

    @GetMapping("/downloadReconstructedFile")
    public ResponseEntity<byte[]> downloadReconstructedFileFunction( ) throws Exception {
        try{
            System.out.println("A fost redirectionat in /downloadReconstructedFile");
            synchronized (ShareHolder.getLock()) {
               if(!ShareHolder.isTaskCompleted()) {
                    System.out.println("SHARES NUMBER"+ShareHolder.getSharesNumber());
                    ShareHolder.getLock().wait();
                }
            }

            Map<String,String> result = RequestHandler.Reconstruct();


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", result.get("filename"));

            return new ResponseEntity<>(result.get("Content").getBytes(), headers, HttpStatus.OK);
        }catch (Exception e){
            Log.ErrorLog(e.getMessage());
            return null;
        }

    }




}