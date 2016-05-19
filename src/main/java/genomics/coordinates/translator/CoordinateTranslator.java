package genomics.coordinates.translator;

import genomics.coordinates.GenomeCoOrdinates;
import genomics.coordinates.utils.JsonParserUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoordinateTranslator {
  
  
  /** Load the provided file, else load the default file in the classpath
   * 
   * @param fileName
   * @throws JsonParseException
   * @throws IOException
   */
  public static void translateCoOrdinates(String fileName) throws JsonParseException, IOException{
    InputStream coOrdsFile = null;
    if(fileName != null && !"".equalsIgnoreCase(fileName)){
      coOrdsFile = new  FileInputStream(new File(fileName));
    }else{
      coOrdsFile = ClassLoader.getSystemResourceAsStream("source.json");
    }
    translateCoOrdinates(coOrdsFile);
  }
  
  /**
   * Initialise the parser and parse Json string
   * 
   * @param file
   * @throws JsonParseException
   * @throws IOException
   */
  public static void translateCoOrdinates(InputStream file) throws JsonParseException, IOException{
    if(file != null){
      JsonParser parser;
      parser = JsonParserUtils.getJsonParser().createParser (file);
      parseJSONString(parser);
    }
  }
    
  /**Parse the Json as strem, but load each dictionary mapping in memory and parse the chromosome values
   * This doesn't load the entire json file in memory for performance reasons 
   * 
   * @param parser
   * @throws JsonParseException
   * @throws IOException
   */
  private static void  parseJSONString(JsonParser parser ) throws JsonParseException, IOException{
    ObjectMapper objectMapper = new ObjectMapper();
    while(!parser.isClosed()){
      //To avoid loading entire file in memory (for performance reasons) , we streaming read the JSON file
      JsonToken token = parser.nextToken();
      //Since source  file is list(array) of fragments, we load each fragment in memory and translate it 
      if(token != null && JsonToken.START_ARRAY.equals(token)){
        System.out.print(token.asString());
        while (parser.nextToken() != JsonToken.END_ARRAY) {
          GenomeCoOrdinates data = objectMapper.treeToValue(parser.readValueAsTree(), GenomeCoOrdinates.class);
          if(data != null &&  data.getChromosome() != null){
            Long offset = GenomeAlignmentMapper.getChromosomeOffset(data.getChromosome());
            if(offset != null){
              data.setPosition(offset+data.getPosition());
              data.setChromosome(GenomeAlignmentMapper.getChromosomeNewName(data.getChromosome()));
              System.out.println(objectMapper.writeValueAsString(data)+",");
            }
          }
        }
        System.out.print(parser.getCurrentToken().asString());
      }
    }
  }

}
