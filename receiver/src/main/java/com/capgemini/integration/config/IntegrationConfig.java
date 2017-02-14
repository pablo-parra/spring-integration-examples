package com.capgemini.integration.config;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.jms.Jms;

import com.capgemini.integration.api.IntegrationHandler;

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

  @Value("${integration.poller.rate}")
  private int rate;

  @Bean
  public IntegrationFlow inFlow(IntegrationHandler handler) throws Exception {

    return IntegrationFlows.from(Jms.inboundAdapter(this.connectionFactory).destination(this.queue),
        c -> c.poller(Pollers.fixedRate(this.rate, TimeUnit.MILLISECONDS)/* fixedRate(100) */)).handle(m -> {
          try {
            handler.handleMessage(m.getPayload());
          } catch (Exception e) {
            e.printStackTrace();
          }
        }).get();
  }
}
