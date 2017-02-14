package com.cap.poc;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.dsl.support.GenericHandler;

import com.cap.poc.service.MessageReceiverHandler;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SpringBootApplication
// @IntegrationComponentScan
public class SipongApplication {

  private final static Logger LOGGER = Logger.getLogger(SipongApplication.class.getName());

  @Autowired
  private ConnectionFactory connectionFactory;

  public static void main(String[] args) throws Exception {

    SpringApplication.run(SipongApplication.class, args);

  }

  @Bean
  public IntegrationFlow inboundFlow() {

    return IntegrationFlows.from(Jms.inboundGateway(this.connectionFactory).destination("amq.queue"))
        .transform((String s) -> s.toUpperCase()).get();
  }

  @Bean
  public IntegrationFlow inJsonFlow() {

    return IntegrationFlows.from(Jms.inboundGateway(this.connectionFactory).destination("json.queue"))
        .wireTap(flow -> flow.handle(System.out::println)).handle(new GenericHandler<String>() {

          @Override
          public String handle(String payload, Map<String, Object> headers) {

            try {

              JsonParser parser = new JsonParser();
              JsonObject o = parser.parse(payload).getAsJsonObject();
              JsonElement e = o.get("quantity");
              int result = Integer.parseInt(e.toString()) * 2;
              return String.valueOf(result);

            } catch (Exception e) {
              // TODO: handle exception
              return null;
            }

          }

        }).get();
  }

  @Bean
  public IntegrationFlow inXmlFlow() throws Exception {

    MessageReceiverHandler messageHandler = new MessageReceiverHandler();
    return IntegrationFlows.from(Jms.inboundAdapter(this.connectionFactory).destination("xml.queue"),
        c -> c.poller(Pollers.fixedRate(10000, TimeUnit.MILLISECONDS)/* fixedRate(100) */)).handle(m -> {
          try {
            messageHandler.handleMessageInternal(m.getPayload());
          } catch (Exception e) {
            e.printStackTrace();
          }
        }).get();
  }

}
