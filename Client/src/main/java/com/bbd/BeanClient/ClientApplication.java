package com.bbd.BeanClient;


import com.bbd.shared.models.*;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.bbd.BeanClient.model.FavoriteBean;
import com.bbd.BeanClient.requestmodel.BanBeanRequest;


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
            //createPost();
            //commentReaction();
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


            // Running tests
            try {
                boolean beanResult = banBean(1, true);

                System.out.println(String.format("bean result: %s", beanResult));

                createPost();
                commentReaction();
                createComment();
            } catch (Exception e) {
                System.out.println("Nope, sorry. Error: " + e.toString());
            }

            System.out.println("Tests completed, starting client");

            while (true) {
                System.out.print("\n\n>");
                String userInput = UserInput.scanner.nextLine();
                if (Arrays.asList("quit", "exit", "terminate").contains(userInput)) {
                    System.out.println("It's bean a pleasure! Goodbye");
                    UserInput.scanner.close();
                    break;
                } else {
                    UserInput.processCommand(userInput);
                }
            }
            System.exit(0);
        };
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
            System.out.println("Post created successfully: " + responseEntity.getStatusCode());
        } else {
            System.out.println("Failed to create post. Status code: " + responseEntity.getStatusCode());
        }
    }

    /*
     * Create User Profile
     */
    private static void createUserProfile() {
        Users newUser = new Users(1,10,"testingUser2","I like beans again");
        
        String createUserUrl = endpoint + "/createUserProfile";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(createUserUrl, newUser, Void.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("User created successfully");
        } else {
            System.out.println("Failed to create user. Status code: " + responseEntity.getStatusCodeValue());
        }
    }


    /*
     * Create Comment
     */
    private static void createComment() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Comment newComment = new Comment(1, 2, "This is my comment!", currentTime);
        String createCommentUrl = endpoint + "/createcomment";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(createCommentUrl, newComment, Void.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Post created successfully: " + responseEntity.getStatusCode());
        } else {
            System.out.println("Failed to create post. Status code: " + responseEntity.getStatusCode());
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

    /*
     * Can use this to like /dislike post
     */
    private static void postReaction() {
        RestTemplate restTemplate = new RestTemplate();
        String url = endpoint + "/postreaction";

        int userId = 0;
        int reactionTypeId = 2;
        int postId = 1;

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Reaction newReaction = new Reaction(userId, reactionTypeId, currentTime);
        PostReaction newPostReaction = new PostReaction(postId, reactionTypeId);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("reaction", newReaction);
        requestBody.put("postReaction", newPostReaction);


        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(url, requestBody, Void.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Post upvoted successfully");
        } else {
            System.out.println("Failed to upvote comment. Status code: " + responseEntity.getStatusCodeValue());
        }
    }

}