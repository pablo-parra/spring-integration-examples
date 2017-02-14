package com.capgemini;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.capgemini.app.ClientB;

@SpringBootApplication
public class ClientBApplication {

  public static void main(String[] args) throws IOException {

    ConfigurableApplicationContext ctx = SpringApplication.run(ClientBApplication.class, args);

    ClientB client = (ClientB) ctx.getBean("client-b");
    client.start();
    System.out.println("Hit 'Enter' to terminate");
    System.in.read();
    ctx.close();
  }
}
