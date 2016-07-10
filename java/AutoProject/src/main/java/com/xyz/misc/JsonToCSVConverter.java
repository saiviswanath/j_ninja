package com.xyz.misc;

import java.io.File;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.beans.Employee;
import com.xyz.util.PropertyConstants;
import com.xyz.util.PropertyLoader;

public class JsonToCSVConverter {
  private static final Properties props = PropertyLoader.getProperties();

  public static void main(String... args) throws Exception {
    JsonParser jsonParser =
        new JsonFactory().createParser(new File(props
            .getProperty(PropertyConstants.APP_JSON_INPUT_FILE)));
    ObjectMapper objectMapper = new ObjectMapper();
    MappingIterator<Employee> mi = objectMapper.readValues(jsonParser, Employee.class);
    while (mi.hasNext()) {
      Employee emp = mi.next();
      System.out.println(emp);
    }
  }
}
