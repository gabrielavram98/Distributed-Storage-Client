package proiectdiz.Model;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import proiectdiz.Database.DatabaseHandler;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Helpers.PasswordGenerator;
import proiectdiz.Helpers.ValidationCheck;
import proiectdiz.Log.Log;
import proiectdiz.Service.*;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.*;

public class RequestHandler {
    private static String uuid="";

    private static String Password="";




    public static void Handle(String requestBody, String filename) throws Exception {
        if(!ValidationCheck.isValidAESKey(requestBody) && !ValidationCheck.isValidRSAkey(requestBody)){
            throw new Exception("Error in Validating the request.Not a valid key."+requestBody);
        }
        SecretKey password= PasswordGenerator.GeneratePassword();
        byte[] keyBytes=password.getEncoded();
        Password= Base64.getEncoder().encodeToString(keyBytes);
        byte[] secret= MACAppender.AppendMAC(requestBody,password);
        BigInteger p= BitOperator.generatePrimeP(512);
        List<String> uuid_list=ProcessSecret.Process(secret,p);
        uuid=UUID.randomUUID().toString();
        StringJoiner joiner= new StringJoiner(", ");
        for(String uuid_elem:uuid_list){
            joiner.add(uuid_elem);
        }
        List<String> param_list= List.of(uuid,MACAppender.HashPassword(Password),p.toString(),joiner.toString(),filename);
        DatabaseHandler db_handler= new DatabaseHandler(Properties.getUsername(), Properties.getPassword(),Properties.getConnectionString());
        db_handler.ExecuteStoredProcedure(Properties.getInsertProc(), param_list);
        ShareHolder.setFile_name(filename);








    }
    public static void HandleRequestForFileDownload(String requestBody){


        try{
            String uuid_from_file=requestBody.split("\n")[0].split(":")[1];
            String Password_from_file=requestBody.split("\n")[1].split(":")[1];

            if(!ValidationCheck.isValidUUID(uuid_from_file)){
                throw  new Exception("Error in Validating the request. Not a valid UUID: "+requestBody);
            }
            String UUID=uuid_from_file;

            //////get important data from database
            DatabaseHandler db_handler= new DatabaseHandler(Properties.getUsername(), Properties.getPassword(),Properties.getConnectionString());
            List<String> params= List.of(UUID);
            Map<String,String>results= db_handler.ExecuteStoredProcedure(Properties.getReturnProc(),params);
            BigInteger p= new BigInteger(results.get("P"));
            String password_b64_hash= results.get("PASSWORD_b64_hash");


            /////Verify password integrity
            String file_password_hash= MACAppender.HashPassword(Password_from_file);
            if(!file_password_hash.equals(password_b64_hash)){
                throw new Exception("Password has been altered. Please provide the original password");
            }
            ShareHolder.setP(p);
            ShareHolder.setPassword(Password_from_file);
            ShareHolder.setFile_name(results.get("File_name"));
            List<String> uuid_list=Arrays.asList(results.get("UUID_list").split(","));
            ProcessSecret.SendDownloadRequest(uuid_list);

            System.out.println(uuid_list);







        }
        catch (Exception e){
            Log.ErrorLog(e.getMessage());
        }


    }
    public static String returnData(){
        String toreturn="UUID:"+uuid+"\nPassword:"+Password;
        Password="";
        uuid="";

        return toreturn;
    }

   public static String returnFilenameUID(){
        String filenameuuid=ShareHolder.getFile_name()+"UniqueIdentifier";
        ShareHolder.clear();
        return filenameuuid;
   }
    public static String returnFilename(){
        return ShareHolder.getFile_name();
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
    public static String Reconstruct() throws Exception {
        List<String> encrypted_shares=ShareHolder.getShares();
        List<JsonNode> decrypted_shares= new ArrayList<>();

        for(int i=0;i<encrypted_shares.size();i++){
            decrypted_shares.add(ProcessSecret.DecryptShares(encrypted_shares.get(i)));
        }
        List<Share> ShareObjects= new ArrayList<>();
        for(int i=0;i<decrypted_shares.get(0).get("Shares").size();i++){
            Share share= new Share();

            for(int j=0;j<Properties.getL();j++){
                String guid=decrypted_shares.get(j).get("Shares").get(i).get("GUID").asText();
                if(share.getGUID()!=null)
                {
                    if(!share.getGUID().equals(guid)){
                        throw new Exception("Error at reconstructing");
                    }
                }else{
                    share.setGUID(guid);
                }

                share.Add2X(decrypted_shares.get(j).get("Shares").get(i).get("X").asText());
                share.Add2Y(decrypted_shares.get(j).get("Shares").get(i).get("Y").asText());
            }
        ShareObjects.add(share);

        }

        List<String> reconstructed_parts= new ArrayList<>();

        for(Share share:ShareObjects){
            Lagrange lag= new Lagrange(share.getX().toArray(new BigInteger[0]), share.getY().toArray(new BigInteger[0]),ShareHolder.getP(),Properties.getL());
             BigInteger reconstructed=lag.lagrangeInterpolation();

                 //byte[] reconstructedBytes1=reconstructed.subtract(ShareHolder.getP()).toByteArray();
                // String convertedString1 = new String(reconstructedBytes1, StandardCharsets.UTF_8);

                 //System.out.println(convertedString1);

                 byte[] reconstructedBytes2=reconstructed.toByteArray();
                 String convertedString2 = new String(reconstructedBytes2, StandardCharsets.UTF_8);

                 //if(isBase64(convertedString1)){
                 //    reconstructed_parts.add(convertedString1);
                // }
                // else{
                     reconstructed_parts.add(convertedString2);
                // }

        }
        String concatenated_MAC_string=String.join("",reconstructed_parts);



        if(MACAppender.VerifyMac(concatenated_MAC_string,ShareHolder.getPassword())){
            ShareHolder.clear();
            return concatenated_MAC_string.substring(44);
        }
        else{
            return null;
        }



    }
    public static boolean isBase64(String input) {
        try {
            // Decode the input as Base64
            byte[] decodedBytes = Base64.getDecoder().decode(input);

            // Encode the decoded bytes back to Base64
            String reencoded = Base64.getEncoder().encodeToString(decodedBytes);

            // Check if the reencoded string matches the original input
            return input.equals(reencoded);
        } catch (IllegalArgumentException e) {
            // If an exception occurs during decoding, it is not valid Base64
            return false;
        }
    }




}
