package com.xyz.serviceutils.json.jackson;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class ObjectMapperFactory extends AbstractFactoryBean<ObjectMapper> {

  public static ObjectMapper configureObjectMapper(final ObjectMapper om) {
    return om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
        .configure(SerializationFeature.INDENT_OUTPUT, true)
        .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
  }


  @Override
  public Class<?> getObjectType() {
    return ObjectMapper.class;
  }

  @Override
  protected ObjectMapper createInstance() throws Exception {
    JsonFactory jf = new JsonFactory();
    jf.configure(JsonFactory.Feature.INTERN_FIELD_NAMES, false);
    return configureObjectMapper(new ObjectMapper(jf));
  }

}
