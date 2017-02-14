package com.capgemini;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.capgemini.app.ClientA;

@SpringBootApplication
@ComponentScan(basePackages = { "com.capgemini" })
public class ClientAApplication {

  public static void main(String[] args) throws IOException {

    ConfigurableApplicationContext ctx = SpringApplication.run(ClientAApplication.class, args);

    ClientA client = (ClientA) ctx.getBean("client-a");
    client.start(ctx);

    System.out.println("Hit 'Enter' to terminate");
    System.in.read();
    ctx.close();
  }

}
