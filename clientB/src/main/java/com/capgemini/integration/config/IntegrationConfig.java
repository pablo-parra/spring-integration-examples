package com.capgemini.integration.config;

import java.util.Map;
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
import org.springframework.integration.dsl.support.GenericHandler;

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
  public IntegrationFlow inFlow(IntegrationHandler handler) {

    return IntegrationFlows.from(Jms.inboundAdapter(this.connectionFactory).destination(this.queue),
        c -> c.poller(Pollers.fixedRate(this.rate, TimeUnit.MILLISECONDS))).handle(m -> {
          try {
            handler.handleMessage(m.getPayload());
          } catch (Exception e) {
            e.printStackTrace();
          }
        }).get();
  }

  @Bean
  public IntegrationFlow inAndOutFlow(IntegrationHandler h) {

    return IntegrationFlows.from(Jms.inboundGateway(this.connectionFactory).destination("echo.queue"))
        .wireTap(flow -> flow.handle(System.out::println))
        .handle(/*
                 * Message, m -> { try { return h.handleMessage(m.getPayload()); } catch (Exception e) {
                 * e.printStackTrace(); return null; } }
                 */
            new GenericHandler<String>() {

              @Override
              public Object handle(String payload, Map<String, Object> headers) {

                try {

                  return h.handleMessage(payload);

                } catch (Exception e) {
                  // TODO: handle exception
                  return null;
                }

              }

            }

        ).get();
  }
}
