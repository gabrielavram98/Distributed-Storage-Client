package proiectdiz.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyHolder {
    private static List<QuantecKey> KeyList= new ArrayList<QuantecKey>();
    private static Map<String,String> keyMapper= new HashMap<>();

    public static QuantecKey getKey(String keyId){
        QuantecKey key = null;
        for(QuantecKey keyFound:KeyList){
            if(keyFound.get_keyId().equals(keyId)){
               key= keyFound;

            }
        }
        return key;
//TODO: Figure out a way to retrieve the right key from holder

    }

    public static void AddKey(QuantecKey key,String SlaveMaster){
        KeyList.add(key);
        keyMapper.put(key.get_keyId(),SlaveMaster);

    }


    public static void FlushKeyById(String id){
        QuantecKey key= getKey(id);
        KeyList.remove(key);
    }
}
