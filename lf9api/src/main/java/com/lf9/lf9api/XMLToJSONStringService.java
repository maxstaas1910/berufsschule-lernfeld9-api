package com.lf9.lf9api;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

@Component
public class XMLToJSONStringService {

  private final String XML_FILE = "network.xml";
  public JSONArray convert() {
    var networkXMLAsDocument = readXMLFromFileAndParseToDocument();
    return convertToJSONAndFilterDevicesAndIPV6(networkXMLAsDocument);
  }
  private Document readXMLFromFileAndParseToDocument() {
    try {
      var file = new File(XML_FILE);
      var documentBuilderFactory = DocumentBuilderFactory
          .newInstance();
      var documentBuilder = documentBuilderFactory.newDocumentBuilder();
      return documentBuilder.parse(file);
    } catch (ParserConfigurationException | IOException | SAXException e) {
      System.out.println("Exception occurred: " + e.toString());
      return null;
    }
  }

  private JSONArray convertToJSONAndFilterDevicesAndIPV6(Document networkXMLAsDocument) {
    var devices = networkXMLAsDocument.getElementsByTagName("DEVICE");
    var deviceAmount = devices.getLength();
    System.out.println(deviceAmount);
    var resultJson = new JSONArray();

    for (int i=0; i < deviceAmount; i++) {
      {
        var node = devices.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE)
        {
          var deviceJSON = new JSONArray();
          var device = (Element) node;
          var deviceName = device.getElementsByTagName("NAME").item(0).getTextContent();
          var deviceIPV6 = device.getElementsByTagName("ADDRESS").item(0).getTextContent();
          var deviceNetMASK = device.getElementsByTagName("PREFIX").item(0).getTextContent();
          var deviceFullIPV6 = deviceIPV6 + "/" + deviceNetMASK;

          try {
            deviceJSON.put(new JSONObject().put("name", deviceName));
            deviceJSON.put(new JSONObject().put("ipv6", deviceFullIPV6));
          } catch (JSONException e) {
            System.out.println("Exception occurred: " + e.toString());
          }

          resultJson.put(deviceJSON);
        }
      }
    }
    return resultJson;
  }
}
