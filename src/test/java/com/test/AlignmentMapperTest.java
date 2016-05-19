package com.test;

import genomics.coordinates.translator.GenomeAlignmentMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;

public class AlignmentMapperTest {

  /**
   * to Test the alignment map with provided input
   * 
   * 
   * @throws JsonParseException
   * @throws IOException
   */
  @Test
  public void testProvidedInput() throws JsonParseException, IOException {
    GenomeAlignmentMapper.loadAlignmentMapperValues(ClassLoader.getSystemResourceAsStream("alignment1.json"));
    Map<String, Long> OffsetMap = new HashMap<String, Long>() {
      {
        put("1", new Long(200));
        put("2", new Long(-280));
      }
    };
    Map<String, String> NameMap = new HashMap<String, String>() {
      {
        put("1", "2");
        put("2", "7");
      }
    };

    Assert.assertEquals(OffsetMap, GenomeAlignmentMapper.coOrdinatesOffsetMap);
    Assert.assertEquals(NameMap, GenomeAlignmentMapper.coOrdinatesNameMap);

  }
  
  
  
  /**
   * to Test the alignment map with  input which has missing target sections
   * 
   * 
   * @throws JsonParseException
   * @throws IOException
   */
  @Test
  public void testMissingTarget() throws JsonParseException, IOException {
    GenomeAlignmentMapper.loadAlignmentMapperValues(ClassLoader.getSystemResourceAsStream("alignment2.json"));
    Map<String, Long> OffsetMap = new HashMap<String, Long>() {
      {
        put("2", new Long(-280));
      }
    };
    Map<String, String> NameMap = new HashMap<String, String>() {
      {
        put("2", "7");
      }
    };

    Assert.assertEquals(OffsetMap, GenomeAlignmentMapper.coOrdinatesOffsetMap);
    Assert.assertEquals(NameMap, GenomeAlignmentMapper.coOrdinatesNameMap);

  }
  
  /**
   * to Test the alignment map with  input which has missing source sections
   * 
   * 
   * @throws JsonParseException
   * @throws IOException
   */
  @Test
  public void testMissingSource() throws JsonParseException, IOException {
    GenomeAlignmentMapper.loadAlignmentMapperValues(ClassLoader.getSystemResourceAsStream("alignment3.json"));
    Map<String, Long> OffsetMap = new HashMap<String, Long>() {
      {
        put("2", new Long(-280));
      }
    };
    Map<String, String> NameMap = new HashMap<String, String>() {
      {
        put("2", "7");
      }
    };

    Assert.assertEquals(OffsetMap, GenomeAlignmentMapper.coOrdinatesOffsetMap);
    Assert.assertEquals(NameMap, GenomeAlignmentMapper.coOrdinatesNameMap);

  }
  
  /**
   * to Test the empty alignment file
   * 
   * 
   * @throws JsonParseException
   * @throws IOException
   */
  @Test
  public void testEmpty() throws JsonParseException, IOException {
    GenomeAlignmentMapper.loadAlignmentMapperValues(ClassLoader.getSystemResourceAsStream("alignment4.json"));
    Map<String, Long> OffsetMap = new HashMap<String, Long>() ;
    Map<String, String> NameMap = new HashMap<String, String>() ;

    Assert.assertEquals(OffsetMap, GenomeAlignmentMapper.coOrdinatesOffsetMap);
    Assert.assertEquals(NameMap, GenomeAlignmentMapper.coOrdinatesNameMap);

  }

}
