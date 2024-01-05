package proiectdiz.Model.DataFormat;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ShareJSON {
    private String share;
    private String key_ID;

    public ShareJSON(String share, String key_ID){
        this.share=share;
        this.key_ID=key_ID;
    }

    public String toJSON(){
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("key_ID",key_ID);
        node.put("share",share);
        return node.toPrettyString();
    }

}
