package com.cap.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@ComponentScan(basePackages = "com.cap.poc")
public class PongApplication {

  public static void main(String[] args) {

    SpringApplication.run(PongApplication.class, args);

    try {
      Thread.sleep(60000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
}
