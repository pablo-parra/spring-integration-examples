package com.cap.poc.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.cap.poc.service.Quintuplicator;

/**
 * @author pparrado
 *
 */
@Component
public class MessageReceiver implements MessageListener {

  static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

  @Autowired
  MessageConverter messageConverter;

  @Autowired
  Quintuplicator q;

  @Override
  public void onMessage(Message message) {

    Object m;
    try {
      m = this.messageConverter.fromMessage(message);
      LOG.info("I have received: " + m.toString());
      this.q.x5(m.toString());
    } catch (MessageConversionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JMSException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
