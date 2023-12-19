package proiectdiz.Helpers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import com.networknt.schema.ValidationMessage;


public class ValidationCheck {
     public static int Validate(JsonNode request, String schema) throws Exception {
         ObjectMapper objectMapper = new ObjectMapper();

         JsonSchema schemaStream = SchemaLoader.LoadSchemaFromPath( schema );
         Set<ValidationMessage> validationMessage = null;
         if (schemaStream != null) {

             validationMessage = schemaStream.validate(request);
         }
         if(validationMessage==null)
         {
             throw new Exception("Error validating the request");
         }
         return 0;


     }
}
