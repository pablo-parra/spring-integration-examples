package com.capgemini;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.capgemini.app.Receiver;

@SpringBootApplication
public class ReceiverApplication {

  public static void main(String[] args) throws IOException {

    ConfigurableApplicationContext ctx = SpringApplication.run(ReceiverApplication.class, args);

    Receiver receiver = (Receiver) ctx.getBean("receiver");
    receiver.start();
    System.out.println("Hit 'Enter' to terminate");
    System.in.read();
    ctx.close();
  }
}
