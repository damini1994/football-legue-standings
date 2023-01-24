package com.microservice.footballleaguestandings.config;

import com.microservice.footballleaguestandings.config.model.RestClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

  @Autowired
  private RestClientProperties restClientProperties;

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate(getClientHttpRequestFactory());
  }

  private ClientHttpRequestFactory getClientHttpRequestFactory() {
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    clientHttpRequestFactory.setReadTimeout(restClientProperties.getReadTimeout());
    clientHttpRequestFactory.setConnectTimeout(restClientProperties.getConnectTimeout());
    return clientHttpRequestFactory;
  }
}
