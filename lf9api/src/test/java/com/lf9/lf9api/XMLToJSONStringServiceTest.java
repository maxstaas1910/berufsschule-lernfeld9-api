package com.lf9.lf9api;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.w3c.dom.Document;

class XMLToJSONStringServiceTest {

  private final XMLToJSONStringService service = new XMLToJSONStringService();
  private final String TEST_XML_FILE = "src/test/resources/test_network.xml";


  @Test
  public void shouldCorrectlyConvertToJSONAndFilterDevicesAndIPV6() {
    // when
    var result = service.convertToJSONAndFilterDevicesAndIPV6(generateTestDocument());

    // then
    var correctNetworkJSONArray = generateCorrectNetworkJSONArray();
    JSONAssert.assertEquals(correctNetworkJSONArray, result, true);
  }

  private Document generateTestDocument() {
    try {
      var file = new File(TEST_XML_FILE);
      var documentBuilderFactory = DocumentBuilderFactory
          .newInstance();
      var documentBuilder = documentBuilderFactory.newDocumentBuilder();
      return documentBuilder.parse(file);
    } catch (Exception e) {
      fail("File parsing failed: " + e.toString());
      return null;
    }
  }

  private JSONArray generateCorrectNetworkJSONArray() {
    var firstTestPC = new JSONArray().put(new JSONObject().put("name", "PC-ST.PAULI"))
        .put(new JSONObject().put("ipv6", "1910:1910:1910::1910/48"));
    var secondTestPC = new JSONArray().put(new JSONObject().put("name", "PC-PSYDUCK"))
        .put(new JSONObject().put("ipv6", "54:54:54:54::54/64"));
    return new JSONArray().put(firstTestPC).put(secondTestPC);
  }
}