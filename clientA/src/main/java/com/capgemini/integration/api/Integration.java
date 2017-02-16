package com.capgemini.integration.api;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author pparrado
 *
 */
public interface Integration {

  // public void send(ConfigurableApplicationContext ctx, String message);

  public Object sendAndGetReply(ConfigurableApplicationContext ctx, Object object);

  // public void sendAsJson(ConfigurableApplicationContext ctx, Object object);
}
