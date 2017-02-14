package com.capgemini;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ClientAApplication.class)
public class ClientAApplicationTests {

  // @Inject
  // private Integration integration;

  @Test
  public void sendTest() {

    // integration.send(ctx, message);
  }

}
