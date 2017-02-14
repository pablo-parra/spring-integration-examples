package com.capgemini.integration.api;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author pparrado
 *
 */
public interface Integration {

  public void send(ConfigurableApplicationContext ctx, String message);

  public void sendAsXml(ConfigurableApplicationContext ctx, Object object);

  public void sendAsJson(ConfigurableApplicationContext ctx, Object object);
}
