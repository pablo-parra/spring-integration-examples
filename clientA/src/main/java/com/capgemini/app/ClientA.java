package com.capgemini.app;

import javax.inject.Inject;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.capgemini.integration.api.Integration;
import com.capgemini.model.Product;

/**
 * @author pparrado
 *
 */
@Component("client-a")
public class ClientA {

  @Inject
  private Integration integration;

  public void start(ConfigurableApplicationContext ctx) {

    Product p1 = new Product();
    p1.setName("Product A");
    p1.setProductId("1");
    p1.setPrice(25);

    printProduct(p1);

    Product p2 = (Product) this.integration.getReply(ctx, p1);

    printProduct(p2);
  }

  public void printProduct(Product p) {

    System.out.println("### PRODUCT ###");
    System.out.println("NAME: " + p.getName());
    System.out.println("ID: " + p.getProductId());
    System.out.println("PRICE: " + String.valueOf(p.getPrice()));
    System.out.println("###############");
  }
}
