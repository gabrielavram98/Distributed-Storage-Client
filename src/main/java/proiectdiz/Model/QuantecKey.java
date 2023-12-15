package proiectdiz.Model;

public class QuantecKey {
    String _keyId;
    String key;
    String _key_extenstion;
    String key_container_extension;
    public QuantecKey(String _keyId,String _key_extenstion,String key, String key_container_extension){
        this._key_extenstion=_key_extenstion;
        this.key=key;
        this.key_container_extension=key_container_extension;
        this._keyId=_keyId;
    }

    public QuantecKey(){};



}


 /*
    {
        "keys": [
        {
            "key_ID": "123456",
                "key_ID_extension": "string",
                "key": "string",
                "key_extension": "string"
        }
  ],
        "key_container_extension": "string"
    }
    */