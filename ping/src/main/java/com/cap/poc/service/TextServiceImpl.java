package com.cap.poc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cap.poc.messaging.MessageSender;

/**
 * @author pparrado
 *
 */
@Service("textService")
public class TextServiceImpl implements TextService {

  @Autowired
  MessageSender sender;

  @Override
  public void sendText(String text) {

    this.sender.sendMessage(text);

  }

}
