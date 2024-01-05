package proiectdiz.Model;

import proiectdiz.Log.Log;
import proiectdiz.Service.KeyRequestor;

import java.util.*;

public class KeyHolder {
    private static List<QuantecKey> KeyList= new ArrayList<QuantecKey>();
    private static Map<String,String> keyMapper= new HashMap<>();

    public static QuantecKey getKey(String guid){
        QuantecKey key = null;
        for(QuantecKey keyFound:KeyList){
            if(keyFound.get_keyId().equals(guid)){
               key= keyFound;
               break;

            }
        }
        return key;


    }
    //Map.Entry<String, Integer> entry : myMap.entrySet()
    public static QuantecKey  getKeyByUUID(String uuid){
        QuantecKey key=null;
        for(Map.Entry<String,String> entry: keyMapper.entrySet()){
            if(entry.getKey().equals(uuid)){
                key=getKey(entry.getValue());
                break;
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

    public static String[] getKeysUUID() throws InterruptedException {
        //Map<Integer,String> keys= new HashMap<>();
        String[] guid= new String[Properties.getN()];
        KeyRequestor[] requests= new KeyRequestor[Properties.getN()];
        for(int i = 0; i< Properties.getN(); i++) {
            try{
                guid[i]= UUID.randomUUID().toString();
                KeyRequestor req= new KeyRequestor(guid[i],i);
                requests[i]=req;
            }
            catch (Exception e){
                Log.ErrorLog(e.getMessage());}
        }
        for(int i = 0; i< Properties.getN(); i++){
            requests[i].start();

        }
        for(int i = 0; i< Properties.getN(); i++){
            requests[i].join();

        }
        return guid;


    }



}
