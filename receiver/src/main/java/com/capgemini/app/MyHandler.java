package com.capgemini.app;

import org.springframework.stereotype.Component;

import com.capgemini.integration.api.IntegrationHandler;

/**
 * @author pparrado
 *
 */
@Component
public class MyHandler implements IntegrationHandler {

  @Override
  public Object handleMessage(Object payload) {

    System.out.println(payload.toString());
    return null;
  }

}