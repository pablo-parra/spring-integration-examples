package com.capgemini.integration.config;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;

/**
 * @author pparrado
 *
 */
public class ConfigUtils {

  @Inject
  private ConnectionFactory connectionFactory;

  public ConnectionFactory getConnectionFactory() {

    return this.connectionFactory;
  }
}
