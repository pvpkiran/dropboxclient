package de.optile.dropbox.config;

import com.dropbox.core.DbxWebAuth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

/**
 * Created by panikiran on 03.05.17.
 */
@Configuration
public class BeansConfig {

  @Bean
  public DbxWebAuth.Request getDbWebAuthRequest() {
    return DbxWebAuth.Request.newBuilder().withNoRedirect().build();
  }

  @Bean
  public InputStream getInputStream(){
    return System.in ;
  }
}
