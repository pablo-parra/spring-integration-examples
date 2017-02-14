package com.cap.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;

import com.cap.poc.service.TextService;

//@SpringBootApplication
@ComponentScan(basePackages = "com.cap.poc")
// @Import({ MessagingConfig.class })
public class PingApplication {

  // @Autowired
  // TextService textService;

  public static void main(String[] args) {

    SpringApplication.run(PingApplication.class, args);

    start();

  }

  public static void start() {

    AbstractApplicationContext context = new AnnotationConfigApplicationContext(PingApplication.class);
    TextService textService = (TextService) context.getBean("textService");
    // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    try {
      // while (true) {
      // Thread.sleep(3000);
      // Date date = new Date();
      // String text = dateFormat.format(date);
      // System.out.println(text);
      // textService.sendText(text);
      // }

      for (int i = 0; i < 4; i++) {
        Thread.sleep(2000);
        textService.sendText(String.valueOf(i));
      }

      while (true) {

      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      context.close();
    }

  }
}
