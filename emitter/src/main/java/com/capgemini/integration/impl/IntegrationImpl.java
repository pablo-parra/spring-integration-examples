package com.capgemini.integration.impl;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.gateway.GatewayProxyFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import com.capgemini.integration.api.Integration;
import com.capgemini.integration.config.ConfigUtils;
import com.capgemini.integration.config.IntegrationConfig.OneDirectionGateway;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author pparrado
 *
 */
@Named
@Component
public class IntegrationImpl implements Integration {

  @Inject
  private ConnectionFactory connectionFactory;

  @Override
  public void send(ConfigurableApplicationContext ctx, String message) {

    OneDirectionGateway oneDirectionGateway = ctx.getBean(OneDirectionGateway.class);
    oneDirectionGateway.send(message);

  }

  @Override
  public void sendAsXml(ConfigurableApplicationContext ctx, Object object) {

    String xml = convertObjectToXml(object);
    OneDirectionGateway oneDirectionGateway = ctx.getBean(OneDirectionGateway.class);
    oneDirectionGateway.send(xml);
  }

  @Override
  public void sendAsJson(ConfigurableApplicationContext ctx, Object object) {

    String json = convertObjectToJson(object);
    OneDirectionGateway oneDirectionGateway = ctx.getBean(OneDirectionGateway.class);
    oneDirectionGateway.send(json);

  }

  private String convertObjectToXml(Object o) {

    OutputStream out = new ByteArrayOutputStream();

    XMLEncoder encoder = new XMLEncoder(out, "UTF-8", false, 4);
    encoder.writeObject(o);
    encoder.flush();

    return out.toString();
  }

  private String convertObjectToJson(Object o) {

    Gson gson = new GsonBuilder().create();
    return gson.toJson(o);
  }

  @Override
  public void createFlow(ConfigurableApplicationContext ctx, String channelName, String queueName) {

    // AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RootConfiguration.class);

    ConfigUtils utils = new ConfigUtils();

    ConfigurableListableBeanFactory beanFactory = ctx.getBeanFactory();

    GatewayProxyFactoryBean gateway = createInboundGateway(beanFactory);

    IntegrationFlow flow2 = IntegrationFlows.from("newChannel")
        .handle(Jms.outboundAdapter(this.connectionFactory).destination("new-queue")).get();

    beanFactory.registerSingleton("testFlow", flow2);
    beanFactory.initializeBean(flow2, "testFlow");

    ctx.start();
    // NewGateway nGateway = ctx.getBean(NewGateway.class);
    // nGateway.send("foo");

    ctx.close();

  }

  public GatewayProxyFactoryBean createInboundGateway(ConfigurableListableBeanFactory beanFactory

  // AbstractConnectionFactory factory,
  // BeanFactory beanFactory,
  // MessageChannel input,
  // int replyTimeout,
  // int retryInterval
  ) {

    GatewayProxyFactoryBean gateway = new GatewayProxyFactoryBean();
    gateway.setDefaultRequestChannelName("newChannel");

    // gateway.setConnectionFactory(factory);
    // gateway.setClientMode(true);
    // gateway.setReplyTimeout(replyTimeout);
    // gateway.setRetryInterval(retryInterval);
    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    scheduler.initialize();
    gateway.setTaskScheduler(scheduler);
    gateway.setBeanFactory(beanFactory);

    return gateway;
  }
}
