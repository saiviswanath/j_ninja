package com.xyz.gson;

import org.springframework.beans.factory.FactoryBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AuthGsonFactory implements FactoryBean<Gson> {

  private Gson gson;

  public AuthGsonFactory() {
    GsonBuilder builder = new GsonBuilder();
    builder.disableHtmlEscaping();
    gson = builder.create();
  }

  @Override
  public Gson getObject() {
    return gson;
  }

  @Override
  public Class<? extends Gson> getObjectType() {
    return Gson.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

}
