package com.capgemini.app;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Component;

import com.capgemini.integration.api.IntegrationHandler;
import com.capgemini.model.Product;

/**
 * @author pparrado
 *
 */
@Component
public class MyHandler implements IntegrationHandler {

  @Override
  public Object handleMessage(Object message) {

    return updateXmlProduct(message);

  }

  private String updateXmlProduct(Object message) {

    InputStream in = new ByteArrayInputStream(message.toString().getBytes());
    XMLDecoder decoder = new XMLDecoder(in);
    Product p = (Product) decoder.readObject();
    p.setName("Product B");
    p.setPrice(50);
    decoder.close();

    OutputStream out = new ByteArrayOutputStream();

    XMLEncoder encoder = new XMLEncoder(out, "UTF-8", false, 4);
    encoder.writeObject(p);
    encoder.flush();
    encoder.close();
    try {
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return out.toString();
  }

}
