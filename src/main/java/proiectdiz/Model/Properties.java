package proiectdiz.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import proiectdiz.Log.Log;

import java.io.FileInputStream;
import java.io.InputStream;


public class Properties {


    public static String n;




    public static String l;

    private static final String CONFIG_FILE_PATH = "config.properties";

    public static String getConnectionString(){
        String conn_string="";
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            java.util.Properties properties = new java.util.Properties();
            properties.load(input);
            conn_string=properties.getProperty("database_connection_string");
        }catch (Exception e){
            Log.ErrorLog(e.getMessage());
        }
        return conn_string;
    }

    public static String getUsername(){
        String username="";
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            java.util.Properties properties = new java.util.Properties();
            properties.load(input);
            username=properties.getProperty("db_username");
        }catch (Exception e){
            Log.ErrorLog(e.getMessage());
        }
        return username;
    }
    public static String getPassword(){
        String password="";
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            java.util.Properties properties = new java.util.Properties();
            properties.load(input);
            password=properties.getProperty("db_password");
        }catch (Exception e){
            Log.ErrorLog(e.getMessage());
        }
        return password;
    }
    public static String getInsertProc(){
        String insert_proc="";
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            java.util.Properties properties = new java.util.Properties();
            properties.load(input);
            insert_proc=properties.getProperty("insert_parameters_procedure");
        }catch (Exception e){
            Log.ErrorLog(e.getMessage());
        }
        return insert_proc;
    }
    public static String getReturnProc(){
        String return_proc="";
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            java.util.Properties properties = new java.util.Properties();
            properties.load(input);
            return_proc=properties.getProperty("get_storage_parameters_by_uuid");
        }catch (Exception e){
            Log.ErrorLog(e.getMessage());
        }
        return return_proc;
    }

    public static int getN(){
        return Integer.parseInt(n);
    }

    public static void setN(String n) {
        Properties.n = n;
    }

    public static void setL(String l) {
        Properties.l = l;
    }

    public static int getL(){
        return Integer.parseInt(l);
    }
    public static String getDestination(String i){
        String destination="";
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            java.util.Properties properties = new java.util.Properties();
            properties.load(input);
            destination=properties.getProperty("destination"+i);
        }catch (Exception e){
            Log.ErrorLog(e.getMessage());
        }
        return destination;
    }
}
