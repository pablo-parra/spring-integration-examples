package com.capgemini.app;

import javax.inject.Inject;

import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.stereotype.Component;

import com.capgemini.integration.api.Integration;

/**
 * @author pparrado
 *
 */
@Component("client-b")
public class ClientB {

  @Inject
  private Integration integration;

  @Inject
  private IntegrationFlowContext context;

  public void start() {

    this.integration.subscribe(new MyHandler());
  }
}
