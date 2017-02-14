package com.capgemini.integration.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.capgemini.integration.api.Integration;
import com.capgemini.integration.api.IntegrationHandler;
import com.capgemini.integration.config.IntegrationConfig;

/**
 * @author pparrado
 *
 */
@Named
public class IntegrationImpl implements Integration {

  @Inject
  private IntegrationConfig integrationConfig;

  @Override
  public void subscribe(IntegrationHandler handler) {

    try {
      this.integrationConfig.inFlow(handler);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
