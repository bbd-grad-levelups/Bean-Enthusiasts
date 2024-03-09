package com.bbd.BeanClient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

            /*
            Create user Post
            */
            {
                Map<String, Object> postParams = new HashMap<>();
                postParams.put("userId", 1);
                postParams.put("postId", 1);
                postParams.put("title", "My Post");
                postParams.put("content", "This is the content of my post");
                String createPostUrl = "http://localhost:5000/createpost"; // Assuming this is the correct endpoint
                // Create the request entity
                HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(postParams);

                // Create the RestTemplate instance
                RestTemplate restTemplatePost = new RestTemplate();

                ResponseEntity<Void> responseEntity = restTemplatePost.postForEntity(createPostUrl, requestEntity, Void.class);

                // Check the response status code
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Post created successfully");
                } else {
                    System.out.println("Failed to create post. Status code: " + responseEntity.getStatusCodeValue());
                }
            }

            /*
            Retrieve List of all favorite beans
            */
            {
                String favBeans;
                String favBeansUrl = "http://localhost:5000/favoritebeans";
                favBeans = restTemplate.getForObject(favBeansUrl, String.class);
                System.out.println("Fav beans are " + favBeans);
            }


            Scanner scanner = new Scanner(System.in);
            String consuming = args.length > 0 ? args[0] : "1";
            String quote;

            boolean running = true;
            while (running) {

                // resource = "http://localhost:8080/test/" + consuming;
                // quote = restTemplate.getForObject(resource, String.class);

                //System.out.println("Received: " + quote);
                System.out.println("New input? : ");
                consuming = scanner.nextLine();
                if (consuming.equals("")) {
                    running = false;
                    scanner.close();
                }


            }
        };

    }

}