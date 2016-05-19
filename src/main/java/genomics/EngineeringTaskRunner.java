package genomics;

import genomics.coordinates.translator.CoordinateTranslator;
import genomics.coordinates.translator.GenomeAlignmentMapper;

import java.io.IOException;
import java.net.URISyntaxException;

import com.fasterxml.jackson.core.JsonParseException;

public class EngineeringTaskRunner {
  
  /**
   * Please provide the external file paths if you want to run the program against different input
   * 
   * @param args
   * @throws JsonParseException
   * @throws IOException
   * @throws URISyntaxException
   */
  public static void main(String[] args) throws JsonParseException, IOException, URISyntaxException{
    System.out.println("Beginning Co-ordinates Translator");
    
    GenomeAlignmentMapper.loadAlignmentMapperValues("");    
    CoordinateTranslator.translateCoOrdinates("");
    
    
    System.out.println();
    System.out.println("End");
    
  }

}
