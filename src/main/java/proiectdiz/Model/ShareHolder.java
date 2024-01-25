package proiectdiz.Model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ShareHolder {
    private static List<String> shares= new ArrayList<>();
    private static final Object lock = new Object();
    private static  BigInteger p=null;
    private static   String Password=null;
    private static String file_name=null;

  private static boolean taskCompleted=false;

    public static void addShare(String share){
         shares.add(share);
    }

    public static int getSharesNumber(){
        return shares.size();
    }
    public static Object getLock(){
        return lock;
    }

    public static boolean isTaskCompleted() {
        return taskCompleted;
    }

    public static void setTaskCompleted() {
        ShareHolder.taskCompleted = true;
    }
    public static void setTaskUncompleted() {
        ShareHolder.taskCompleted = false;
    }


    public static List<String> getShares() {
        return shares;
    }
    public static void clear(){
        shares.clear();
        if(p!=null){
            p=p.xor(p);
        }

        Password="";
        file_name="";
    }

    public static void setP(BigInteger p) {
        ShareHolder.p = p;
    }

    public static void setPassword(String password) {
        Password = password;
    }

    public static void setFile_name(String file_name) {
        ShareHolder.file_name = file_name;
    }

    public static String getFile_name() {
        return file_name;
    }

    public static BigInteger getP() {
        return p;
    }

    public static String getPassword() {
        return Password;
    }

}
