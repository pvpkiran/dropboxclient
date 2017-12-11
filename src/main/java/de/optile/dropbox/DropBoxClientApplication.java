package de.optile.dropbox;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by panikiran on 03.05.17.
 */
@SpringBootApplication
public class DropBoxClientApplication{

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(DropBoxClientApplication.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.setLogStartupInfo(false);
    app.run(args);
  }
}
