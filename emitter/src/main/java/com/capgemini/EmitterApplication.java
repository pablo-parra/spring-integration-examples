package com.capgemini;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.capgemini.app.Emitter;

@SpringBootApplication
public class EmitterApplication {

  public static void main(String[] args) throws IOException {

    ConfigurableApplicationContext ctx = SpringApplication.run(EmitterApplication.class, args);

    Emitter emitter = (Emitter) ctx.getBean("emitter");
    emitter.start(ctx);

    System.out.println("Hit 'Enter' to terminate");
    System.in.read();
    ctx.close();
  }
}
