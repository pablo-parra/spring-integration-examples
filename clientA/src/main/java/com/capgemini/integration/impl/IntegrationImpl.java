package com.capgemini.integration.impl;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Named;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.capgemini.integration.api.Integration;
import com.capgemini.integration.config.IntegrationConfig.EchoGateway;

/**
 * @author pparrado
 *
 */
@Named
@Component
public class IntegrationImpl implements Integration {

  // @Override
  // public void send(ConfigurableApplicationContext ctx, String message) {
  //
  // OneDirectionGateway oneDirectionGateway = ctx.getBean(OneDirectionGateway.class);
  // oneDirectionGateway.send(message);
  //
  // }

  @Override
  public Object sendAndGetReply(ConfigurableApplicationContext ctx, Object p1) {

    String xml = convertObjectToXml(p1);
    EchoGateway echoGateway = ctx.getBean(EchoGateway.class);
    String xmlResponse = echoGateway.echo(xml);
    Object objResponse = convertXmlToObject(xmlResponse);
    return objResponse;
  }

  // @Override
  // public void sendAsJson(ConfigurableApplicationContext ctx, Object object) {
  //
  // String json = convertObjectToJson(object);
  // OneDirectionGateway oneDirectionGateway = ctx.getBean(OneDirectionGateway.class);
  // oneDirectionGateway.send(json);
  //
  // }

  private String convertObjectToXml(Object o) {

    OutputStream out = new ByteArrayOutputStream();

    XMLEncoder encoder = new XMLEncoder(out, "UTF-8", false, 4);
    encoder.writeObject(o);
    encoder.flush();
    encoder.close();
    return out.toString();
  }

  private Object convertXmlToObject(String xml) {

    InputStream in = new ByteArrayInputStream(xml.getBytes());
    XMLDecoder decoder = new XMLDecoder(in);
    Object xmlObj = decoder.readObject();
    decoder.close();
    return xmlObj;
  }
}
