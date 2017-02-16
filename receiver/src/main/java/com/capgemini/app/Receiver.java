package com.capgemini.app;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.capgemini.integration.api.Integration;

/**
 * @author pparrado
 *
 */
@Component("receiver")
public class Receiver {

  @Inject
  private Integration integration;

  public void start() {

    // this.integration.subscribe(new MyHandler());
  }
}
