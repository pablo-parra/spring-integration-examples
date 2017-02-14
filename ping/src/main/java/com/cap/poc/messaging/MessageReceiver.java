package com.cap.poc.messaging;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

/**
 * @author pparrado
 *
 */
@Component
public class MessageReceiver implements MessageListener {

  @Override
  public void onMessage(Message message) {

    System.out.println("The consumer has responded: " + message);

  }

}
