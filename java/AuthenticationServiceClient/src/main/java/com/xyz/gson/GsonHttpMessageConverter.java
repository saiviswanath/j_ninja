package com.xyz.gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.google.gson.Gson;

public class GsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

  public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
  private Gson gson;

  public GsonHttpMessageConverter() {
    super(new MediaType("application", "json", DEFAULT_CHARSET));
  }

  public Gson getGson() {
    return gson;
  }

  public void setGson(Gson gson) {
    this.gson = gson;
  }


  @Override
  protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    return gson.fromJson(new InputStreamReader(inputMessage.getBody(), DEFAULT_CHARSET), clazz);
  }

  @Override
  protected boolean supports(Class<?> clazz) {
    return true;
  }

  @Override
  protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException,
  HttpMessageNotWritableException {
    OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody(), DEFAULT_CHARSET);
    gson.toJson(o, writer);
    writer.flush();
  }

}
