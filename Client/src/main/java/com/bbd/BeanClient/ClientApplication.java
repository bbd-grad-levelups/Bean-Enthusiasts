package com.bbd.BeanClient;

import com.bbd.shared.models.CommentReaction;
import com.bbd.shared.models.Post;
import com.bbd.shared.models.Reaction;
import com.bbd.shared.models.Users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.bbd.BeanClient.model.FavoriteBean;
import com.bbd.BeanClient.requestmodel.BanBeanRequest;

import java.util.Scanner;

@SpringBootApplication
public class ClientApplication {


    private final static String endpoint = "http://localhost:5000";

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
            
            createPost();
            commentReaction();
            createUserProfile();
            //retrieveFavorite Beans();
            boolean beanResult = banBean(1, true);

            System.out.println(String.format("bean result: %s", beanResult));

            Scanner scanner = new Scanner(System.in);
            String consuming = args.length > 0 ? args[0] : "1";

            boolean running = true;
            while (running) {

                System.out.println("New input? : ");
                consuming = scanner.nextLine();
                if (consuming.equals("")) {
                    running = false;
                    scanner.close();
                }


            }
        };

    }

    /*
     * Create Post
     */
    private static void createPost() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Post newPost = new Post(1, 1, "My Post", "This is the content of my post", currentTime);

        String createPostUrl = endpoint + "/createpost";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(createPostUrl, newPost, Void.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Post created successfully");
        } else {
            System.out.println("Failed to create post. Status code: " + responseEntity.getStatusCodeValue());
        }
    }

    private static void createUserProfile() {
        Users newUser = new Users(1,5,"testingUser","I like beans");
        
        String createUserUrl = endpoint + "/createUserProfile";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("1");
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(createUserUrl, newUser, Void.class);
        System.out.println("2");
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("User created successfully");
        } else {
            System.out.println("Failed to create user. Status code: " + responseEntity.getStatusCodeValue());
        }
    }


    /*
     * Fetch Favorite Beans
     */
    public static String retrieveFavoriteBeans() {
        String favBeansUrl = endpoint + "/favoritebeans";

        RestTemplate restTemplate = new RestTemplate();

        String favBeans = restTemplate.getForObject(favBeansUrl, String.class);

        System.out.println("Fav beans are " + favBeans);

        return favBeans;
    }

    private boolean banBean(int bean_id, boolean new_status) {
        RestTemplate restTemplate = new RestTemplate();

        // Define request URL
        String url = endpoint + "/favoritebean/ban";

        // Define request body
        BanBeanRequest request = new BanBeanRequest(bean_id, new_status);

        // Send POST request and get response
        ResponseEntity<FavoriteBean> response = restTemplate.postForEntity(url, request, FavoriteBean.class);

        // Print response
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());
        return response.getStatusCode().is2xxSuccessful();
    }

    /*
        Can you use this to like /dislike comment
    */
    private static void commentReaction() {
        RestTemplate restTemplate = new RestTemplate();
        String url = endpoint + "/commentreaction";

        int userId = 0;
        int reactionTypeId = 2;
        int commentId = 1;

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Reaction newReaction = new Reaction(userId, reactionTypeId, currentTime);
        CommentReaction newCommentReaction = new CommentReaction(reactionTypeId, commentId);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("reaction", newReaction);
        requestBody.put("commentReaction", newCommentReaction);


        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(url, requestBody, Void.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Comment upvoted successfully");
        } else {
            System.out.println("Failed to upvote comment. Status code: " + responseEntity.getStatusCodeValue());
        }
    }

}