package genomics.coordinates.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingJsonFactory;

public class JsonParserUtils {
  
  public static JsonFactory getJsonParser(){
    JsonFactory factory = new MappingJsonFactory();
    factory.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
    return factory;
    
  }

}
