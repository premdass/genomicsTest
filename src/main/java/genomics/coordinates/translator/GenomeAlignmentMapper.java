package genomics.coordinates.translator;

import genomics.coordinates.CoOrdinates;
import genomics.coordinates.utils.JsonParserUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;

public class GenomeAlignmentMapper {

  // Map to store the Offset positions
  public static Map<String, Long> coOrdinatesOffsetMap = new HashMap<String, Long>();

  // Map to store the new chromosome names to old one
  public static Map<String, String> coOrdinatesNameMap = new HashMap<String, String>();

  public static Long getChromosomeOffset(String chromosomeName) {
    return coOrdinatesOffsetMap.get(chromosomeName);
  }

  public static String getChromosomeNewName(String chromosomeName) {
    return coOrdinatesNameMap.get(chromosomeName);
  }

  public static void printData() {
    System.out.println(coOrdinatesNameMap);
    System.out.println(coOrdinatesOffsetMap);
  }


  /**
   * This method initialises the Json parser and reads the alignment file provided. If no file is
   * provided, it uses the default file provided in the classpath For performance reasons, we dont
   * load the entore Json file in memory. Instead it streams the Json file Since alignment file is
   * list(array) of dictionaries, we load each dictionary item in memory(and not the entire file)
   * and read the required source and target positions
   * 
   * @param fileName
   * @throws IOException 
   * @throws JsonParseException 
   */
  public static void loadAlignmentMapperValues(String fileName) throws JsonParseException, IOException {
    InputStream mapperFile;
    if (fileName != null && !"".equalsIgnoreCase(fileName)) {
      mapperFile = new FileInputStream(new File(fileName));
    } else {
      //mapperFile = new File(ClassLoader.getSystemResource("alignment.json").getFile());
      mapperFile = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("alignment.json");
      
    }
    loadAlignmentMapperValues(mapperFile);
  }
  
  
  /**
   * Generate the Fileobject for the parser
   * 
   * @param mapperFile
   * @throws JsonParseException
   * @throws IOException
   */
  public static void loadAlignmentMapperValues(InputStream mapperFile) throws JsonParseException, IOException {
    JsonParser parser;
    parser = JsonParserUtils.getJsonParser().createParser(mapperFile);
    parseJSONString(parser);
  }

  /**Parse the Json and build two Maps, one containing Name Maps, and
   * other map containing the position mapping
   * 
   * @param parser
   * @throws JsonParseException
   * @throws IOException
   */
  private static void parseJSONString(JsonParser parser) throws JsonParseException, IOException {
    while (!parser.isClosed()) {
      // To avoid loading entire file in memory (for performance reasons) , we streaming read the
      // JSON file
      JsonToken token = parser.nextToken();
      // Since alignment file is list(array) of dictionaries, we load each dictionary item in
      // memory(and not the entire file) and read the required source and target positions
      if (token != null && JsonToken.START_ARRAY.equals(token)) {
        while (parser.nextToken() != JsonToken.END_ARRAY) {
          JsonNode node = parser.readValueAsTree();
          CoOrdinates source = getCoOrdinates(node.get("source"));
          CoOrdinates target = getCoOrdinates(node.get("target"));
          if (source != null && target != null) {
            coOrdinatesNameMap.put(source.getName(), target.getName());
            coOrdinatesOffsetMap.put(source.getName(), (target.getPosition() - source.getPosition()));
          }
        }
      }
    }
  }


  /**
   * Extract the chromosoem name and position value
   * @param node
   * @return
   */
  private static CoOrdinates getCoOrdinates(JsonNode node) {
    if (node != null) {
      String chromosomeName = node.get("chromosome").asText();
      long chromosomePosition = node.get("start").asLong();
      if (chromosomeName != null) {
        return new CoOrdinates(chromosomeName, chromosomePosition);
      }
    }
    return null;
  }

}
