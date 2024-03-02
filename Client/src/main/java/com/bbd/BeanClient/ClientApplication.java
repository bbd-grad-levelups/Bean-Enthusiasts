package com.bbd.BeanClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class ClientApplication {

  private static final Logger log = LoggerFactory.getLogger(ClientApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ClientApplication.class, args);
  }

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	@ConditionalOnProperty(name = "app.run", havingValue = "true", matchIfMissing = true)
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
      
      String consuming = args.length > 0 ? args[0] : "1";
      String resource = "http://localhost:8080/test/" + consuming;
			String quote = restTemplate.getForObject(
					resource, String.class);
			System.out.println("Received: " + quote);
      System.exit(0);
		};
    
	}

}