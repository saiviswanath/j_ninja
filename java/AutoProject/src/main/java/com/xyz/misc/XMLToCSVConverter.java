package com.xyz.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xyz.util.PropertyConstants;
import com.xyz.util.PropertyLoader;

public class XMLToCSVConverter {
  private static final Properties props = PropertyLoader.getProperties();

  public static void main(String... args) throws Exception {
    buildStudentCSV(getDocument(new File(props.getProperty(PropertyConstants.APP_XML_INPUT_FILE))), 
        new File(props.getProperty(PropertyConstants.APP_CSV_OUTPUT_FILE)));
  }

  private static void buildStudentCSV(Document inputDocument, File outFile) throws Exception {
    NodeList nodeList = inputDocument.getElementsByTagName("Student");
    FileWriter fw = new FileWriter(outFile);
    fw.write("ID, Name, Age, DOB\n");
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node stuNode = nodeList.item(i);
      if (stuNode.getNodeType() == Node.ELEMENT_NODE) {
        Element stuEle = (Element) stuNode;
        String id = stuEle.getAttribute("id");
        String name = stuEle.getElementsByTagName("name").item(0).getTextContent();
        String age = stuEle.getElementsByTagName("age").item(0).getTextContent();
        String dob = stuEle.getElementsByTagName("dob").item(0).getTextContent();
        fw.write(id + ", " + name + ", " + age + ", " + dob + "\n");
        fw.flush();
      }
    }
    fw.close();
  }

  public static Document getDocument(File file) throws Exception {
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = builderFactory.newDocumentBuilder();
    return builder.parse(new FileInputStream(file));
  }
}
