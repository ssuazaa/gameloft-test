package com.susocode.gamelofttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.susocode.gamelofttest.user.infrastructure.spring",
    "com.susocode.gamelofttest.shared.infrastructure.spring"
})
public class GameloftTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(GameloftTestApplication.class, args);
  }

}
