package com.cap.poc.service;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.springframework.context.annotation.Configuration;

import com.cap.poc.model.Product;

/**
 * @author pparrado
 *
 */
@Configuration
public class MessageReceiverHandler /* extends AbstractMessageHandler */ {

  private final static Logger LOGGER = Logger.getLogger(MessageReceiverHandler.class.getName());

  // @Override
  public void handleMessageInternal(Object payload) throws Exception {

    LOGGER.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    System.out.println(payload.toString());
    LOGGER.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$");

    InputStream in = new ByteArrayInputStream(payload.toString().getBytes());
    XMLDecoder decoder = new XMLDecoder(in);
    Product p = (Product) decoder.readObject();

    printObject(p);

    decoder.close();
    try {
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void printObject(Product p) {

    LOGGER.info("### PRODUCT ###");
    LOGGER.info("NAME: " + p.getName());
    LOGGER.info("ID: " + p.getProductId());
    LOGGER.info("QUANTITY: " + String.valueOf(p.getQuantity()));
    LOGGER.info("###############");
  }

}
