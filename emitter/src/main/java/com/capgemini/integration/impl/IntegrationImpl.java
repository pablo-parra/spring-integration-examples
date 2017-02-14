package com.capgemini.integration.impl;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.inject.Named;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.capgemini.integration.api.Integration;
import com.capgemini.integration.config.IntegrationConfig.OneDirectionGateway;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author pparrado
 *
 */
@Named
@Component
public class IntegrationImpl implements Integration {

  @Override
  public void send(ConfigurableApplicationContext ctx, String message) {

    OneDirectionGateway oneDirectionGateway = ctx.getBean(OneDirectionGateway.class);
    oneDirectionGateway.send(message);

  }

  @Override
  public void sendAsXml(ConfigurableApplicationContext ctx, Object object) {

    String xml = convertObjectToXml(object);
    OneDirectionGateway oneDirectionGateway = ctx.getBean(OneDirectionGateway.class);
    oneDirectionGateway.send(xml);
  }

  @Override
  public void sendAsJson(ConfigurableApplicationContext ctx, Object object) {

    String json = convertObjectToJson(object);
    OneDirectionGateway oneDirectionGateway = ctx.getBean(OneDirectionGateway.class);
    oneDirectionGateway.send(json);

  }

  private String convertObjectToXml(Object o) {

    OutputStream out = new ByteArrayOutputStream();

    XMLEncoder encoder = new XMLEncoder(out, "UTF-8", false, 4);
    encoder.writeObject(o);
    encoder.flush();

    return out.toString();
  }

  private String convertObjectToJson(Object o) {

    Gson gson = new GsonBuilder().create();
    return gson.toJson(o);
  }
}
