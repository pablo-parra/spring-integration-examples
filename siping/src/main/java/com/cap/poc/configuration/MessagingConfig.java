package com.cap.poc.configuration;

import org.springframework.context.annotation.Configuration;

/**
 * @author pparrado
 *
 */
@Configuration
// @EnableIntegration
public class MessagingConfig {

  // public static final String queue_amq = "amq.outbound";

  // private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";

  // @Bean
  // public ConnectionFactory connectionFactory() {
  //
  // ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
  // connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
  // connectionFactory.setTrustedPackages(Arrays.asList("com.cap.poc"));
  // return connectionFactory;
  // }

  // @Gateway(requestChannel = "goChannel")

  // @Bean(name = "sequenceChannel")
  // public DirectChannel sequenceChannel() {
  //
  // return new DirectChannel();
  // }
  //
  // @Bean(name = "requestChannel")
  // public DirectChannel requestChannel() {
  //
  // return new DirectChannel();
  // }
  //
  // @Bean(name = "amq.outbound")
  // public Queue go() {
  //
  // return new ActiveMQQueue(queue_amq);
  // }

  // @Bean(name = "goChannel")
  // public MessageChannel goChannel() {
  //
  // return new DirectChannel();
  // }

}
