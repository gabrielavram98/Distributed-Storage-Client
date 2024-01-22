package proiectdiz.Model;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import proiectdiz.Database.DatabaseHandler;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Helpers.PasswordGenerator;
import proiectdiz.Helpers.ValidationCheck;
import proiectdiz.Log.Log;
import proiectdiz.Service.*;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.*;

public class RequestHandler {
    private static String uuid="";
    private static String filename="";

    public static HttpStatus Handle(String requestBody) throws Exception {
        JsonNode requestBodyJSON= JsonHandler.StringToJson(requestBody);
        if(ValidationCheck.Validate(requestBodyJSON, "src\\main\\resources\\RequestSchema.json")){

            throw new Exception("Error in Validating the request"+requestBody);
        }
        int secretLenght=requestBodyJSON.get("Data").get("DataContent").toString().length();
        assert requestBodyJSON != null;
        byte[] mac=MACAppender.CreateMAC(requestBodyJSON);
        byte[] stringSecret=requestBodyJSON.get("Data").get("DataContent").toString().getBytes(StandardCharsets.UTF_8);
        byte[] secret= new byte[33+secretLenght];

        System.arraycopy(mac,0,secret,1,mac.length);

        System.arraycopy(stringSecret,0,secret,mac.length,stringSecret.length);


        return HttpStatus.ACCEPTED;

    }


    public static void Handle(String requestBody,String source) throws Exception {
        if(!ValidationCheck.isValidAESKey(requestBody) && !ValidationCheck.isValidRSAkey(requestBody)){
            throw new Exception("Error in Validating the request.Not a valid key."+requestBody);
        }
        SecretKey password= PasswordGenerator.GeneratePassword();

        byte[] secret= MACAppender.AppendMAC(requestBody,password);

        //******VERIFICARE MAC ******////
       // String Request= new String(secret, StandardCharsets.UTF_8);
       // byte[] secret2=Request.getBytes(StandardCharsets.UTF_8);
       // boolean isok=MACAppender.VerifyMac(Request,password);
       // System.out.println(isok);

        //***************************////
        //byte[] secret_b64=Base64.getEncoder().encode(secret);
        BigInteger p= BitOperator.generatePrimeP(512);
        List<String> uuid_list=ProcessSecret.Process(secret,p);
        uuid=UUID.randomUUID().toString();
        StringJoiner joiner= new StringJoiner(", ");
        for(String uuid_elem:uuid_list){
            joiner.add(uuid_elem);
        }
        byte[] keyBytes=password.getEncoded();
        String encodedkey= Base64.getEncoder().encodeToString(keyBytes);
        List<String> param_list= List.of(uuid,MACAppender.HashPassword(encodedkey),p.toString(),joiner.toString());
        DatabaseHandler db_handler= new DatabaseHandler(Properties.getUsername(), Properties.getPassword(),Properties.getConnectionString());
        db_handler.ExecuteStoredProcedure(Properties.getInsertProc(), param_list);

        //*******MOCK*******//
       // MockClass.setP(p.toString());
       // MockClass.setUuid_list(uuid_list);
       //byte[] keyBytes=password.getEncoded();
        //String encodedkey= Base64.getEncoder().encodeToString(keyBytes);
       // MockClass.setKey(encodedkey);
       ///***************************************/////







    }
    public static void HandleRequestForFileDownload(String requestBody){
        try{
            if(!ValidationCheck.isValidUUID(requestBody)){
                throw  new Exception("Error in Validating the request. Not a valid UUID: "+requestBody);
            }
            String UUID=requestBody;
            DatabaseHandler db_handler= new DatabaseHandler(Properties.getUsername(), Properties.getPassword(),Properties.getConnectionString());
            List<String> params= List.of(UUID);
            Map<String,String>results= db_handler.ExecuteStoredProcedure(Properties.getReturnProc(),params);
            BigInteger p= new BigInteger(results.get("P"));
            String password_b64= results.get("PASSWORD_b64_hash");
            List<String> uuid_list=Arrays.asList(results.get("UUID_list").split(","));
            ProcessSecret.SendDownloadRequest(uuid_list);

            System.out.println(uuid_list);





           // SecretKeySpec _secretKeySpec


        }
        catch (Exception e){
            Log.ErrorLog(e.getMessage());
        }


    }
    public static String returnUUID(){
        String toreturn=uuid;
        uuid="";

        return toreturn;
    }

    public static void setFilename(String filename) {
        RequestHandler.filename = filename;
    }

    public static String returnFilename(){
        String filename_="attachment; filename="+filename+"UniqueIdentifier.txt";

        filename="";

        return filename_;
    }

    public static void AddSharesToHolder(String share) throws Exception {

        if(!ValidationCheck.Validate(JsonHandler.StringToJson(share),"src\\main\\resources\\Share_format.json")){
            throw  new Exception("Error in Validating the request.Not valid Share format "+share);
        }
        ShareHolder.addShare(share);
        if(ShareHolder.getSharesNumber()==Properties.getL()){
            synchronized (ShareHolder.getLock()) {
                ShareHolder.setTaskCompleted();
                ShareHolder.getLock().notifyAll();
            }
        }

    }
    public static String Reconstruct(){
        List<String> encrypted_shares=ShareHolder.getShares();
        List<JsonNode> decrypted_shares= new ArrayList<>();
        for(String encr_element:encrypted_shares){
            decrypted_shares.add(ProcessSecret.DecryptShares(encr_element));
        }
        return "";

    }



}
