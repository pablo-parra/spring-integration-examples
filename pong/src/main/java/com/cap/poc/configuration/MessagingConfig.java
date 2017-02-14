package com.cap.poc.configuration;

import java.util.Arrays;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import com.cap.poc.messaging.MessageReceiver;

/**
 * @author pparrado
 *
 */
@Configuration
public class MessagingConfig {
  private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";

  private static final String QUEUE_A = "queue-A";

  private static final String QUEUE_B = "queue-B";

  @Autowired
  MessageReceiver messageReceiver;

  @Bean
  public ConnectionFactory connectionFactory() {

    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
    connectionFactory.setTrustedPackages(Arrays.asList("com.cap.poc"));
    return connectionFactory;
  }

  @Bean
  public MessageListenerContainer getContainer() {

    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
    container.setConnectionFactory(connectionFactory());
    container.setDestinationName(QUEUE_A);
    container.setMessageListener(this.messageReceiver);
    return container;
  }

  @Bean
  MessageConverter converter() {

    return new SimpleMessageConverter();
  }

  @Bean
  public JmsTemplate jmsTemplate() {

    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory());
    template.setDefaultDestinationName(QUEUE_B);
    return template;
  }
}
