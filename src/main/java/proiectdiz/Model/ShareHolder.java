package proiectdiz.Model;

import java.util.List;

public class ShareHolder {
    private static List<String> shares;
    private static final Object lock = new Object();
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

    public static List<String> getShares() {
        return shares;
    }
}
