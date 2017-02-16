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
@Component("emitter")
public class Emitter {

  @Inject
  private Integration integration;

  public void start(ConfigurableApplicationContext ctx) {

    this.integration.send(ctx, "hello");

    Product p = new Product();
    p.setName("Product A");
    p.setProductId("1");
    p.setPrice(25);

    this.integration.sendAsXml(ctx, p);

    this.integration.sendAsJson(ctx, p);

    // CREATING NEW FLOWS
    // this.integration.createFlow(ctx, "myFlow", "myNewQueue");

  }

}
