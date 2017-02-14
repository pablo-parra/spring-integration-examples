package com.cap.poc;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.scheduling.PollerMetadata;

//import com.cap.poc.SipingApplication.ConcatGateway;
import com.cap.poc.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringBootApplication
@IntegrationComponentScan
// @ImportResource("classpath*:META-INF/spring/integration/si-config.xml")
public class SipingApplication {

  @Autowired
  private ConnectionFactory connectionFactory;

  public static void main(String[] args) throws Exception {

    // SpringApplication.run(SipingApplication.class, args);
    ConfigurableApplicationContext ctx = SpringApplication.run(SipingApplication.class, args);

    EchoGateway echo = ctx.getBean(EchoGateway.class);
    // ConcatGateway con = ctx.getBean(ConcatGateway.class);
    JsonGateway jsonGateway = ctx.getBean(JsonGateway.class);
    XmlGateway xmlGateway = ctx.getBean(XmlGateway.class);

    xmlTransport(xmlGateway);
    // jsonTransport(jsonGateway);
    // echoToUpper(echo);
    // concatenation(con);

    System.out.println("Hit 'Enter' to terminate");
    System.in.read();
    ctx.close();
  }

  // --- GATEWAYS
  @MessagingGateway
  public interface EchoGateway {
    @Gateway(requestChannel = "goChannel")
    String echo(String s);
  }

  @Bean(name = PollerMetadata.DEFAULT_POLLER)
  public PollerMetadata poller() {

    return Pollers.fixedDelay(1000).get();
  }

  @Bean
  @SuppressWarnings("unchecked")
  public IntegrationFlow outboundFlow() {

    return IntegrationFlows.from("goChannel")
        .handle(Jms.outboundGateway(this.connectionFactory).requestDestination("amq.queue")).get();
  }

  public static void echoToUpper(EchoGateway echoGateway) {

    send("hello", echoGateway);
    send("World", echoGateway);

  }

  public static void send(String message, EchoGateway echoGateway) {

    String response = echoGateway.echo(message);
    System.out.println("############################");
    System.out.println(response);
    System.out.println("############################");
  }

  // public static void concatenation(ConcatGateway con) {
  //
  // String response1 = con.concatenate("World");
  // String response2 = con.concatenate("another wrong message");
  // String response3 = con.concatenate("the next one will pass the filter");
  // String response4 = con.concatenate("World");
  // System.out.println("////////////////");
  // System.out.println(response4);
  // System.out.println("////////////////");
  // }

  // ------ Flow in "internal" channel/queue
  // @MessagingGateway
  // public interface ConcatGateway {
  // @Gateway(requestChannel = "concatChannel")
  // String concatenate(String s);
  // }
  //
  // @Bean
  // public IntegrationFlow concatFlow() {
  //
  // return
  // IntegrationFlows.from("concatChannel").channel(MessageChannels.queue("concat.queue")).filter("World"::equals)
  // .transform("Hello "::concat).handle(System.out::println).get();
  // }

  // XML -------

  // @Bean
  // public MessageSource<?> xmlMessageSource() {
  //
  // Product p = new Product();
  // p.setName("Product xml");
  // p.setProductId("2");
  // p.setQuantity(3);
  //
  // MethodInvokingMessageSource source = new MethodInvokingMessageSource();
  // source.setObject(p);
  // return source;
  //
  // }

  @MessagingGateway /* (defaultReplyTimeout = "60000", defaultRequestTimeout = "60000") */
  public interface XmlGateway {
    @Gateway(requestChannel = "xmlChannel"/* , replyTimeout = 2, requestTimeout = 200 */)
    void sendXml(String xml);
  }

  // @Bean
  // public DirectChannel outChannel() {
  //
  // return new DirectChannel();
  // }

  @Bean
  IntegrationFlow outXmlFlow() {

    return IntegrationFlows.from("xmlChannel")
        .handle(Jms.outboundAdapter(this.connectionFactory).destination("xml.queue")).get();

    // return IntegrationFlows.from("xmlChannel")
    // .handle(Jms.outboundGateway(this.connectionFactory).requestDestination("xml.queue")).get();

  }

  public static void xmlTransport(XmlGateway xmlGateway) throws InterruptedException, FileNotFoundException {

    // xmlGateway.sendXml("xml");
    // Thread.sleep(5000);
    // xmlGateway.sendXml("xml after 5 sec.");

    Product p = new Product();
    p.setName("Product X");
    p.setProductId("2");
    p.setQuantity(3);

    OutputStream out = new ByteArrayOutputStream();

    XMLEncoder encoder = new XMLEncoder(out, "UTF-8", false, 4);
    encoder.writeObject(p);
    encoder.flush();

    String xmlStringEncoder = out.toString();
    // XStream xstream = new XStream();
    // String xmlString = xstream.toXML(p);

    // xmlGateway.sendXml(xmlString);
    xmlGateway.sendXml(xmlStringEncoder);

  }

  // JSON -------

  @MessagingGateway /* (defaultReplyTimeout = "60000", defaultRequestTimeout = "60000") */
  public interface JsonGateway {
    @Gateway(requestChannel = "jsonChannel"/* , replyTimeout = 2, requestTimeout = 200 */)
    String transport(Object o);
  }

  @Bean
  public IntegrationFlow outJsonFlow() {

    return IntegrationFlows.from("jsonChannel")
        .handle(Jms.outboundGateway(this.connectionFactory).requestDestination("json.queue"))
        /* .transform(Transformers.toJson()) */.get();
  }

  public static void jsonTransport(JsonGateway jsonTransport) throws InterruptedException, ExecutionException {

    Product p = getProduct("1", 10);
    Gson gson = new GsonBuilder().create();
    String json = gson.toJson(p);

    String response = jsonTransport.transport(json);

    System.out.println("*****************");
    System.out.println(response);
    System.out.println("*****************");
  }

  private static Product getProduct(String id, int amount) {

    Product p = new Product();
    p.setName("Product " + id);
    p.setProductId(id);
    p.setQuantity(amount);
    return p;
  }

}
