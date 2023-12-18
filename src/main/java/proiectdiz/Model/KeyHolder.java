package proiectdiz.Model;

import java.util.ArrayList;
import java.util.List;

public class KeyHolder {
    private static List<QuantecKey> KeyList= new ArrayList<QuantecKey>();
    public static QuantecKey getKey(String keyId){
        return new QuantecKey();

    }
    public static void AddKey(QuantecKey key){
        KeyList.add(key);

    }

    public static void FlushKeys(){
        KeyList.clear();
    }
}
