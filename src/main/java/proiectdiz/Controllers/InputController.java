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
import proiectdiz.Model.ShareHolder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

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

    @PostMapping("/gatherShares")
    public HttpStatus Receive_shares_from_servers(@RequestBody String shareBody){
        try{
            Log.TraceLog(shareBody);
            RequestHandler.AddSharesToHolder(shareBody);
            return HttpStatus.ACCEPTED;
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
        return "resultFileBack";
    }

    @GetMapping("/downloadReconstructedFile")
    public ResponseEntity<String> downloadReconstructedFile( ) throws InterruptedException, ExecutionException {

        //Thread taskThread = new Thread(() -> {

        //taskThread.start();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Submitting a Callable task to the executor service
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Task starts.");
                synchronized (ShareHolder.getLock()) {
                    while (!ShareHolder.isTaskCompleted()) {
                        ShareHolder.getLock().wait();
                    }
                }


                // Your code to be executed in the task goes here
                Thread.sleep(2000);  // Simulating some task
                System.out.println("Task ends.");
                return RequestHandler.Reconstruct();
            }
        });

        System.out.println("Main thread continues.");

        // Blocking to get the result from the task
        String result = future.get();
//        System.out.println("Main thread continues.");

        // Wait for the task to complete

        String content = RequestHandler.Reconstruct();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, RequestHandler.returnFilename());
        return ResponseEntity.ok().headers(headers).body(content);
    }




}

