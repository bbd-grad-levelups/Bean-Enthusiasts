package com.bbd.BeanClient;

import com.bbd.BeanClient.util.AuthenticationProcess;

import com.bbd.shared.models.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.context.annotation.Bean;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;




@SpringBootApplication
public class ClientApplication {

    public static boolean isAdmin = false;

    public static AuthenticationProcess a = new AuthenticationProcess("bb6557e63877b23e4b6f");
    
    public final static String endpoint = "http://localhost:5000";

    public static void main(String[] args) {
        a.loginFlow();
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

    }

    private static void profileGet(){
        String username = AuthenticationProcess.getUsername();
        String url = ClientApplication.endpoint + "/user/find";
        //check if in database
         try {        
                var response = UserInput.executeClassRequest(url,new Users(username),HttpMethod.POST,Users.class);            
                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("User found.");
                    if(response.getBody().getUser_role_id()==2){
                        isAdmin = true;
                        System.out.println("Admin user detected!");
                    }else{
                        isAdmin = false;
                        System.out.println("Regular user detected!");
                    }
                }
            } catch (HttpClientErrorException.BadRequest ex) {
                System.err.println("Bad Request!!!");
            }
            catch(HttpClientErrorException.NotFound x){
                System.out.println("You must bean new here! Please enter some information about yourself:");
                UserInput.makeProfile(username);
            }
    }

    /*
     * Create Post
     */
    private static void createPost() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Post newPost = new Post(4, 1, "My Post", "This is the content of my post", currentTime);

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
     * Create Comment
     */
    private static void createComment() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Comment newComment = new Comment(1, 2, "Hey ya!", currentTime);
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

    /*
        Can you use this to like /dislike comment
    */
    private static void commentReaction() {
        RestTemplate restTemplate = new RestTemplate();
        String url = endpoint + "/commentreaction";

        int userId = 0;
        int reactionTypeId = 2;
        int commentId = 5;

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Reaction newReaction = new Reaction(userId, reactionTypeId, currentTime);
        CommentReaction newCommentReaction = new CommentReaction(commentId, reactionTypeId);

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



    public ResponseEntity<String> yourMethod(String accessToken) {
        // Your logic to generate response
        String responseBody = "Your response body here";

        // Creating HttpHeaders object to add headers
        HttpHeaders headers = new HttpHeaders();

        // Adding headers to HttpHeaders object
        headers.add("Custom-Header", "Custom-Value");
        headers.add("Another-Header", "Another-Value");
        headers.add("Authorization", "Bearer " + accessToken);

        // Creating ResponseEntity with headers and status
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, headers, HttpStatus.OK);

        return responseEntity;
    }

    /*
     * Can use this to like /dislike post
     */
    @SuppressWarnings("deprecation")
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