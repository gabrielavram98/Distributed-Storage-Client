package proiectdiz.Database;

public class DatabaseHandler {
    private final String Username="gabriel";
    private final String Password="1234asdf";

    private   String connectionString="jdbc:sqlserver://localhost\\DESKTOP-8P09IFG:1433;database=ShareStorageServer";


    public DatabaseHandler(char i){
        String conn=this.connectionString;
        conn=conn+i;
        this.connectionString=conn;

    }





}
