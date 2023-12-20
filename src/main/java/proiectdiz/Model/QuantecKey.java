package proiectdiz.Model;

public class QuantecKey {
    private String _keyId;
    private String key;
    private String _key_extenstion;
    private String key_container_extension;

    private String Master_SAE;
    private String Slave_SAE;
    public QuantecKey(String _keyId,String key,String _key_extenstion, String key_container_extension, String master, String slave){
        this._key_extenstion=_key_extenstion;
        this.key=key;
        this.key_container_extension=key_container_extension;
        this._keyId=_keyId;
        this.Master_SAE=master;
        this.Slave_SAE=slave;
    }

    public QuantecKey(){};

    public String get_keyId() {
        return _keyId;
    }
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