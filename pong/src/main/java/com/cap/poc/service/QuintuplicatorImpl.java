package com.cap.poc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cap.poc.messaging.MessageSender;

/**
 * @author pparrado
 *
 */
@Service("x5")
public class QuintuplicatorImpl implements Quintuplicator {

  @Autowired
  MessageSender sender;

  public void x5(String base) {

    int baseNumber = Integer.parseInt(base);
    int result = baseNumber * 5;
    this.sender.sendResponseMessage(String.valueOf(result));
  }
}
