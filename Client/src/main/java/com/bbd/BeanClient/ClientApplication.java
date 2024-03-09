package com.bbd.BeanClient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.util.Scanner;

@SpringBootApplication
public class ClientApplication {

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
      System.out.println("Welcome... to BEANS");
      Scanner scanner = new Scanner(System.in);
      String consuming = args.length > 0 ? args[0] : "1";
      String resource;
      String quote;
      
      boolean running = true;
      while (running) {

        resource = "http://localhost:8080/test/" + consuming;
        quote = restTemplate.getForObject(resource, String.class);
        
        System.out.println("Received: " + quote);
        System.out.println("New input? : ");
        consuming = scanner.nextLine();
        if (consuming.equals(""))
        {
          running = false;
          scanner.close();
        }


      }
		};
    
	}

}