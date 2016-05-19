package com.test;



import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import genomics.coordinates.translator.CoordinateTranslator;
import genomics.coordinates.translator.GenomeAlignmentMapper;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonParseException;

public class CoOrdinatesTranslatorTest {
  
  @Before
  public void loadMapperFile() throws JsonParseException, IOException{
    GenomeAlignmentMapper.loadAlignmentMapperValues(ClassLoader.getSystemResourceAsStream("alignment1.json"));
  }
  
  
  @Rule
  public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
  
 
  /**Test 1
   * Checks against provided input and output
   * 
   * 
   * @throws JsonParseException
   * @throws IOException
   * @throws URISyntaxException
   * @throws JSONException
   */
  @Test
  public void testCorrectOutput() throws JsonParseException, IOException, URISyntaxException, JSONException {    
    CoordinateTranslator.translateCoOrdinates(ClassLoader.getSystemResourceAsStream("test1/source1.json"));
    String output = new String(Files.readAllBytes(Paths.get((ClassLoader.getSystemResource("test1/output1.json").toURI()))));
    JSONAssert.assertEquals(output, systemOutRule.getLogWithNormalizedLineSeparator(), true);
  }
  
  
  /**Test 2
   * Checks whether it is dropping the unmapped alignments
   * 
   * 
   * @throws JsonParseException
   * @throws IOException
   * @throws URISyntaxException
   * @throws JSONException
   */
  @Test
  public void testunMappedAlignments() throws JsonParseException, IOException, URISyntaxException, JSONException {    
    CoordinateTranslator.translateCoOrdinates(ClassLoader.getSystemResourceAsStream("test2/source2.json"));
    String output = new String(Files.readAllBytes(Paths.get((ClassLoader.getSystemResource("test2/output2.json").toURI()))));
    JSONAssert.assertEquals(output, systemOutRule.getLogWithNormalizedLineSeparator(), true);
  }
  
  /**Test 3
   * Checks what happens on empty file
   * 
   * 
   * @throws JsonParseException
   * @throws IOException
   * @throws URISyntaxException
   * @throws JSONException
   */
  @Test
  public void testEmptyInPut() throws JsonParseException, IOException, URISyntaxException, JSONException {    
    CoordinateTranslator.translateCoOrdinates(ClassLoader.getSystemResourceAsStream("test3/source3.json"));
    String output = new String(Files.readAllBytes(Paths.get((ClassLoader.getSystemResource("test3/output3.json").toURI()))));
    JSONAssert.assertEquals(output, systemOutRule.getLogWithNormalizedLineSeparator(), true);
  }

}
