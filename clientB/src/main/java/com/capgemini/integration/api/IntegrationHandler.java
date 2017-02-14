package com.capgemini.integration.api;

/**
 * @author pparrado
 *
 */
public interface IntegrationHandler {
  Object handleMessage(Object payload);
}
