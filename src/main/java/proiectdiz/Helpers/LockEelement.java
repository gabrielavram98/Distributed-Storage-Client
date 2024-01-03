package proiectdiz.Helpers;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class LockEelement {
    private static Lock lock= new ReentrantLock();
    public static void LockMethod(){
        lock.lock();
    }
    public static void UnlockMethod(){
        lock.unlock();
    }
}
