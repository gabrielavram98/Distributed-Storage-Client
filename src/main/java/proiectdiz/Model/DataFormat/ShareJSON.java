package proiectdiz.Model.DataFormat;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ShareJSON {
    private String share;
    private String key_ID;
    private String iv;

    public ShareJSON(String share, String key_ID, String iv){
        this.share=share;
        this.key_ID=key_ID;
        this.iv=iv;
    }

    public String toJSON(){
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("key_ID",key_ID);
        node.put("share",share);
        node.put("IV",this.iv);
        return node.toPrettyString();
    }

}
