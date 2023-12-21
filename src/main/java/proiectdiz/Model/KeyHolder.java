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
    //Map.Entry<String, Integer> entry : myMap.entrySet()
    public static QuantecKey  getKeyByUUID(String uuid){
        QuantecKey key=null;
        for(Map.Entry<String,String> entry: keyMapper.entrySet()){
            if(entry.getKey().equals(uuid)){
                key=getKey(entry.getValue());
            }
        }
        return key;
    }

    public static void AddKey(QuantecKey key, String uuid){
        KeyList.add(key);
        keyMapper.put(uuid,key.get_keyId());

    }


    public static void FlushKeyById(String id){
        QuantecKey key= getKey(id);
        KeyList.remove(key);
    }
}
