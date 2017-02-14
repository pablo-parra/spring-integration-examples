package com.capgemini.integration.config;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.jms.Jms;

/**
 * @author pparrado
 *
 */
@Configuration
public class IntegrationConfig {

  @Inject
  private ConnectionFactory connectionFactory;

  @Value("${integration.queue}")
  private String queue;

  @Bean
  IntegrationFlow outFlow() {

    return IntegrationFlows.from("1dChannel")
        .handle(Jms.outboundAdapter(this.connectionFactory).destination(this.queue)).get();

  }

  @Bean
  @SuppressWarnings("unchecked")
  public IntegrationFlow outboundFlow() {

    return IntegrationFlows.from("echoChannel")
        .handle(Jms.outboundGateway(this.connectionFactory).requestDestination("echo.queue")).get();
  }

  @MessagingGateway
  public interface OneDirectionGateway {
    @Gateway(requestChannel = "1dChannel")
    void send(String message);
  }

  @MessagingGateway
  public interface EchoGateway {
    @Gateway(requestChannel = "echoChannel")
    String echo(String message);
  }

}
