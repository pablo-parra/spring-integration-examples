package com.capgemini.integration.api;

/**
 * @author pparrado
 *
 */
public interface Integration {

  void subscribe(IntegrationHandler handler);

}
