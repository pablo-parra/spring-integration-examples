package com.cap.poc.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * @author pparrado
 *
 */
@Component
public class MessageSender {

  @Autowired
  JmsTemplate jmsTemplate;

  public void sendResponseMessage(final String text) {

    MessageCreator mc = new MessageCreator() {

      @Override
      public Message createMessage(Session session) throws JMSException {

        return session.createTextMessage(text);
      }

    };

    this.jmsTemplate.send(mc);

  }

}
