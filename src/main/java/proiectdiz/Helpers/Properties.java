package proiectdiz.Helpers;
import org.apache.catalina.User;
import proiectdiz.Log.Log;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Properties {


    public static String n;
    public static List<String> available_servers= new ArrayList<>();
    public static List<String> Used_servers= new ArrayList<>();



    public static String l;
    public static void AddServers(String server){
        available_servers.add(server);
    }
    public static void AddUsedServer(String server){
        Used_servers.add(server);
    }
    public static void setUsed_servers(String servers){
        String[] server_array= servers.split(",");


        Used_servers = new ArrayList<>(Arrays.asList(server_array));
    }
    public static void removeUsedServer(String server){
        Used_servers.remove(server);
    }

    public static List<String> getUsed_servers() {
        return Used_servers;
    }

    public static List<String> getAvailableServers(){
        return available_servers;
    }

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
