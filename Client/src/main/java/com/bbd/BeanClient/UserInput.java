package com.bbd.BeanClient;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.bbd.BeanClient.util.AuthenticationProcess;
import com.bbd.shared.models.*;


public class UserInput {
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        String userInput = "";
        while(true){
            System.out.print("Enter your text: ");
            userInput = scanner.nextLine();
            processCommand(userInput);
        }
    }


    public static void processCommand(String str){
        List<String> commandElements = Arrays.stream(str.split(" ", -2))
          .collect(Collectors.toList());

          //it being a list means that you can pop from the list and then pass in the list as a param
          //to the other functions and each one does its own check for what it needs to exist in the list for the command
          // to be valid

        boolean isFirstBean = commandElements.size() >= 1 && commandElements.get(0).equals("bean");
        if(!isFirstBean){
            System.out.println("All commands need to start with the word 'bean'.\nType 'bean help' for help with commands.");
        } else {
            commandElements.remove(0);
        }

        String second = commandElements.size() >= 1 ? commandElements.get(0) : "";
        switch (second) {
            case "help":
                help(commandElements);
                break;
            case "post":
                post(commandElements);
                break;
            case "com":
                comment(commandElements);
                break;
            case "set":
                set(commandElements);
                break;
            case "react":
                react(commandElements);
                break;
            case "view":
                view(commandElements);
                break;
            case "add":
                add(commandElements);
                break;
            case "rem":
                rem(commandElements);
                break;
            case "ban":
                ban(commandElements);
                break;
            default:
                System.out.println("Command not recognised, please type 'bean help' for help with commands");
                return;
        }
    }

    private static void help(List<String> commandElements){
        commandElements.remove(0);
        if(commandElements.size() >= 1){
            System.out.println("Note, the help command does not accept any other arguments");
        }
        
        System.out.println("Help Screen:");
        System.out.println("bean help\t\t\t\tDisplay this help screen.");
        System.out.println("bean prof\t\t\t\tCreate or view user profiles.");
        System.out.println("bean post <PostTitle>\t\t\tCreate a new post with the specified title.");
        System.out.println("bean com <PostID> <comment>\t\tAdd a comment to a post.");
        System.out.println("bean set <FavouriteBean>\t\tSet your favorite bean.");
        System.out.println("bean react <type=ID> <reaction>\tReact to a post or comment.");
        System.out.println("bean view <option>\t\t\tView posts, profiles, or other data.");
        System.out.println("bean add <option>\t\t\tAdd new beans or tags.");
        System.out.println("bean rem <BeanName>\t\t\tRemove a bean.");
        System.out.println("bean ban <BeanName> [true/false]\tBan or unban a bean.");

    }

    private static void post(List<String> commandElements){
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'post' commands needs a PostTitle when using it.\nPlease run 'bean post <PostTitle>'");
            return;
        }

        String postTitle = String.join(" ", commandElements);

        String postContent = "";
        while(postContent.equals("")){
            System.out.print("Please provide the post content:");
            postContent = scanner.nextLine();
        }
        String postTag = "";
        //todo all possible tags
        List<String> allTags = List.of("LIMA", "KIDNEY", "BLOOD");
        while(postTag.equals("")){
            System.out.println("All Possible Tags Are:");
            allTags.forEach(System.out::println);
            System.out.print("PostTag: ");
            postTag = scanner.nextLine();
            if(!allTags.contains(postTag.toUpperCase())){
                postTag = "";
                System.out.println("Please supply a valid tag from the supplied list");
            }
        }
        //todo run the make post function passing in
        // postTitle, postContent, postTag
        System.out.println(postTitle);
        System.out.println(postContent);
        System.out.println(postTag.toUpperCase());
    }

    public static void makeProfile(String username){
        String favBean = "";
        String bio = "";

        //CHECK TO MAKE SURE USERNAME ISNT TAKEN
        System.out.println("Please enter a short bio about yourself:");
        bio = scanner.nextLine();
        viewBeans();
        System.out.println("Please enter your favorite bean from the available list: ");
        favBean = scanner.nextLine();
        createUserProfile(getBeanID(favBean),username,bio);
    }

    /*
     * Create User Profile
     */
    private static void createUserProfile(int favBean, String  username, String bio) {
        String endpoint = "http://localhost:5000";
        Users newUser = new Users(1,favBean,username,bio);
        
        String createUserUrl = endpoint + "/createUserProfile";
        boolean execution_success = handleRequest(createUserUrl, newUser, HttpMethod.POST);
        if (execution_success) {
            System.out.println("Successfully added user");
        }
    }

    public static int getBeanID(String name)
    {
        int beanId = 10;
        String endpoint = "http://localhost:5000";
        String url = endpoint + "/favoritebean/find";
        FavoriteBean requestBean = new FavoriteBean(name);
        ResponseEntity<FavoriteBean> response = executeClassRequest(url,requestBean,HttpMethod.POST,FavoriteBean.class);
        if(response.getBody()!=null){
            beanId = response.getBody().getFavoriteBeanId(); 
        }else{
            System.out.println("Please select an actual bean :'(");
        }
        return beanId;
    }

    private static void comment(List<String> commandElements){
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'com' command needs a PostID when using it.\nPlease run 'bean com <PostID> <comment>'");
            return;
        }
        String postID = commandElements.get(0);
        //todo check that this is a valid postID
        commandElements.remove(0);

        if(commandElements.size() == 0){
            System.out.println("The 'com' command needs a Comment when using it.\nPlease run 'bean com <PostID> <comment>'");
            return;
        }
        String comment = String.join(" ", commandElements);

        //todo insert function that makes a comment
        //using postID, comment
    }

    private static void set(List<String> commandElements){
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'set' command needs a favouriteBean when using it.\nPlease run 'bean set <FavouriteBean>'");
            return;
        }
        String favBean = commandElements.get(0);
        //todo check that the inputted bean is valid
    }

    private static void react(List<String> commandElements){
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'react' command is used incorrectly.\n\tPlease run 'bean help' for help.");
            return;
        }
        if(!commandElements.get(0).contains("=")){
            System.out.println("Incorrect usage of the 'react' command.\n\tCheck the 'bean help' command for usage.");
            return;
        }
        List<String> third = Arrays.stream(commandElements.get(0).split("=", -2))
            .collect(Collectors.toList());
        boolean isPost;
        String ID = third.get(1);
        switch (third.get(0)) {
            case "post":
                isPost = true;
                break;
            case "comment":
                isPost = false;
                break;
            default:
                System.out.println(third.get(0) + " is not a valid option for the 'react' command.\n\tCheck the 'bean help' command.");
                return;
        }
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'react' command is used incorrectly.\n\tPlease run 'bean help' for help.");
            return;
        }
        String body = String.join(" ", commandElements);

        if(isPost){
            //todo make sure that ID contains a valid postID
            //todo the react command was used to react to a POST
            //todo make sure the reaction is a valid reaction
        } else {
            //todo make sure that ID contains a valid commentID
            //todo the react command was used to react to a COMMENT
            //todo make sure the reaction is a valid reaction
        } 
    }


    private static void view(List<String> commandElements){
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'view' command is used incorrectly.\n\tPlease run 'bean help' for help.");
            return;
        }

        String third = commandElements.get(0);
        switch (third) {
            case "post":
                viewSpecificPost(commandElements);
                break;
            case "post-all":
                viewAllPosts();
                break;
            case "post-recent":
                viewRecentPost();
                break;
            case "post-me":
                viewMyPosts(commandElements);
                break;
            case "prof":
                viewProfile(commandElements);
                break;
            case "favbeans":
                viewBeans();
                break;
            case "tags":
                viewTags();
                break;
        
            default:
            System.out.println(third + " is not a valid option.\n\tRun 'bean help' for a list of options.");
                return;
        }
    }

    private static void add(List<String> commandElements) {
        if (!ClientApplication.isAdmin) {
            System.out.println("Only admins have access to this command.");
            return;
        }
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'add' command is used incorrectly.\n\tPlease run 'bean help' for help.");
            return;
        }

        String command = commandElements.get(0);
        switch (command) {
            case "bean":
                addBean(commandElements);
                break;
            case "tag":
                addTag(commandElements);
                break;
            default:
                System.out.println(command + " is not a valid option.\n\tRun 'bean help' for a list of options.");
                return;
        }
    }

    private static void rem(List<String> commandElements) {
        if (!ClientApplication.isAdmin) {
            System.out.println("Only admins have access to this command.");
            return;
        }
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'remove' command is used incorrectly.\n\tPlease run 'bean help' for help.");
            return;
        }

        String command = commandElements.get(0);
        switch (command) {
            case "bean":
                removeBean(commandElements);
                break;
            case "tag":
                removeTag(commandElements);
                break;
            default:
                System.out.println(command + " is not a valid option.\n\tRun 'bean help' for a list of options.");
                return;
        }
    }

    private static List<Comment> getPostComments(int postID) {
        String url = ClientApplication.endpoint + "/post/comment";

        ResponseEntity<List<Comment>> responseEntity = executeViewRequest(url, postID, HttpMethod.POST, 
        new ParameterizedTypeReference<List<Comment>>() {});
        return responseEntity.getBody();

    }


    private static void viewSpecificPost(List<String> commandElements) {
        commandElements.remove(0);
        int postID;
        if (commandElements.isEmpty()) {
            System.out.println("Post not given");
            return;
        } else {
            postID = Integer.parseInt(commandElements.get(0));
        }



        String url = ClientApplication.endpoint + "/posts/find";

        try {

            ResponseEntity<Post> responseEntity = executeClassRequest(url, postID, HttpMethod.POST, Post.class);
            Post post = responseEntity.getBody();
            List<Comment> comments = getPostComments(post.getPostId());

            StringBuilder sb = new StringBuilder();
            sb.append("Post Information:\n");
            sb.append("-----------------\n");
            sb.append("Post ID: ").append(post.getPostId()).append("\n");
            sb.append("Username: ").append(post.getUserId()).append("\n");
            sb.append("Tag name: ").append(post.getTagId()).append("\n");
            sb.append("Post Title: ").append(post.getPostTitle()).append("\n");
            sb.append("Post Content: ").append(post.getPostContent()).append("\n");
            sb.append("Date Posted: ").append(post.getDatePosted()).append("\n");
            sb.append("-----------------\n");
            System.out.println(sb.toString());

            System.out.println("\nComments:");
            System.out.println("--------------------\n");
            System.out.println(comments.stream()
            .map(comment -> 
            String.format("Comment ID: %d\nUser ID: %d\nComment: %s\nDate Posted: %s\n",comment.getComment_id(), comment.getUser_id(), comment.getComment_info(), comment.getDatePosted()))
            .collect(Collectors.joining("\n")));

        } catch (HttpClientErrorException.BadRequest ex) {
            System.out.println("Invalid request: " + ex.getResponseBodyAsString());
        } catch (HttpClientErrorException.NotFound ex) {
            System.out.println("Invalid request: User not found");
        }
    }

    
    private static void viewAllPosts() {
        String url = ClientApplication.endpoint + "/posts";

        try {
            ResponseEntity<List<Post>> responseEntity = executeViewRequest(url, "string", HttpMethod.POST, 
            new ParameterizedTypeReference<List<Post>>() {});

            String postList = responseEntity.getBody().stream()
            .map(post -> { 
                StringBuilder sb = new StringBuilder();
                sb.append("Post Information:\n");
                sb.append("-----------------\n");
                sb.append("Post ID: ").append(post.getPostId()).append("\n");
                sb.append("User ID: ").append(post.getUserId()).append("\n");
                sb.append("Tag ID: ").append(post.getTagId()).append("\n");
                sb.append("Post Title: ").append(post.getPostTitle()).append("\n");
                sb.append("Post Content: ").append(post.getPostContent()).append("\n");
                sb.append("Date Posted: ").append(post.getDatePosted()).append("\n");
                sb.append("-----------------\n");
                return sb.toString();

            }).collect(Collectors.joining("\n"));

            System.out.println("All posts");
            System.out.println(postList);
        } catch (Exception e) {
            System.out.println("An error occurred while loading posts");
        }
    }

    private static void viewRecentPost() {
        String url = ClientApplication.endpoint + "/posts/new";
        
        try {

            ResponseEntity<Post> responseEntity = executeClassRequest(url, "string", HttpMethod.POST, Post.class);
            Post post = responseEntity.getBody();
            List<Comment> comments = getPostComments(post.getPostId());

            StringBuilder sb = new StringBuilder();
            sb.append("Post Information:\n");
            sb.append("-----------------\n");
            sb.append("Post ID: ").append(post.getPostId()).append("\n");
            sb.append("Username: ").append(post.getUserId()).append("\n");
            sb.append("Tag name: ").append(post.getTagId()).append("\n");
            sb.append("Post Title: ").append(post.getPostTitle()).append("\n");
            sb.append("Post Content: ").append(post.getPostContent()).append("\n");
            sb.append("Date Posted: ").append(post.getDatePosted()).append("\n");
            sb.append("-----------------\n");
            System.out.println(sb.toString());

            System.out.println("\nComments:");
            System.out.println("--------------------\n");
            System.out.println(comments.stream()
            .map(comment -> 
            String.format("Comment ID: %d\nUser ID: %d\nComment: %s\nDate Posted: %s\n",comment.getComment_id(), comment.getUser_id(), comment.getComment_info(), comment.getDatePosted()))
            .collect(Collectors.joining("\n")));

        } catch (HttpClientErrorException.BadRequest ex) {
            System.out.println("Invalid request: " + ex.getResponseBodyAsString());
        } catch (HttpClientErrorException.NotFound ex) {
            System.out.println("Invalid request: User not found");
        }
    }


    private static void viewMyPosts(List<String> commandElements) {
        commandElements.remove(0);
        String username;
        if (commandElements.isEmpty()) {
            username = ClientApplication.a.getUsername();
        } else {
            username = commandElements.get(0);
        }

        String url = ClientApplication.endpoint + "/posts/user";

        try {
            Users newUser = new Users(username);

            ResponseEntity<List<Post>> responseEntity = executeViewRequest(url, newUser,
            HttpMethod.POST, new ParameterizedTypeReference<List<Post>>() {});

            String postList = responseEntity.getBody().stream()
            .map(post -> {
                StringBuilder sb = new StringBuilder();
                sb.append("Post Information:\n");
                sb.append("-----------------\n");
                sb.append("Post ID: ").append(post.getPostId()).append("\n");
                sb.append("User ID: ").append(post.getUserId()).append("\n");
                sb.append("Tag ID: ").append(post.getTagId()).append("\n");
                sb.append("Post Title: ").append(post.getPostTitle()).append("\n");
                sb.append("Post Content: ").append(post.getPostContent()).append("\n");
                sb.append("Date Posted: ").append(post.getDatePosted()).append("\n");
                sb.append("-----------------\n");
                return sb.toString();
            })
            .collect(Collectors.joining("\n\n"));

            System.out.println(postList);
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }

    private static void viewProfile(List<String> commandElements) {
        commandElements.remove(0);
        String username;
        if (commandElements.isEmpty()) {
            username = ClientApplication.a.getUsername();
        } else {
            username = commandElements.get(0);
        }

        String url = ClientApplication.endpoint + "/user/find";
        
        try {
            Users newUser = new Users(username);

            ResponseEntity<Users> responseEntity = executeClassRequest(url, newUser, HttpMethod.POST, Users.class);
            Users user = responseEntity.getBody();
            
            System.out.println("User Profile:");
            System.out.println("-------------");
            System.out.println("User ID: " + user.getUser_id());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Bio: " + user.getBio());
            System.out.println("User Role ID: " + user.getUser_role_id());
            System.out.println("Favorite Bean ID: " + user.getFavorite_bean_id());
            System.out.println("-------------");


        } catch (HttpClientErrorException.BadRequest ex) {
            System.out.println("Invalid request: " + ex.getResponseBodyAsString());
        } catch (HttpClientErrorException.NotFound ex) {
            System.out.println("Invalid request: User not found");
        }

    }

    @SuppressWarnings("null")
    private static void viewBeans() {
        String url = ClientApplication.endpoint + "/favoritebean";
        
        ResponseEntity<List<FavoriteBean>> responseEntity = executeViewRequest(url, null, 
        HttpMethod.GET, new ParameterizedTypeReference<List<FavoriteBean>>() {});

        String beanList = responseEntity.getBody().stream()
        .map(x -> String.format("%-20s %-10s", x.getBeanName(), x.isBanned()))
        .collect(Collectors.joining("\n"));

        System.out.println("All Available beans: ");
        System.out.println("Name                Banned");
        System.out.println("-------------------- ----------");
        System.out.println(beanList);
        
    }

    @SuppressWarnings("null")
    private static void viewTags() {
        String url = ClientApplication.endpoint + "/tag";
        
        ResponseEntity<List<Tag>> responseEntity = executeViewRequest(
                url,
                null,
                HttpMethod.GET,
                new ParameterizedTypeReference<List<Tag>>() {});

        String entityList = responseEntity.getBody().stream()
        .map(x -> String.format("%-5s %-10s", x.getTag_id(), x.getTag_name()))
        .collect(Collectors.joining("\n"));

        System.out.println("All Available tags: ");
        System.out.println("ID    Name");
        System.out.println("----- ----------");
        System.out.println(entityList);
        
    }

    private static void addBean(List<String> commandElements) {
        //todo check that the user is an admin
        commandElements.remove(0);
        if(commandElements.size() <= 1){
            System.out.println("The 'add' command is used incorrectly, name and banned status not provided.\n\tPlease run 'bean help' for help.");
            return;
        } else if (commandElements.get(0).isEmpty()) {
            System.out.println("Please enter an actual bean name");
            return;
        } else if (!Arrays.asList("true", "false").contains(commandElements.get(1).toLowerCase())) {
            System.out.println("Invalid input argument for banned status");
            return;
        } else {

            String url = ClientApplication.endpoint + "/favoritebean/add";
            FavoriteBean newEntity = new FavoriteBean(commandElements.get(0), Boolean.parseBoolean(commandElements.get(1)));

            boolean execution_success = handleRequest(url, newEntity, HttpMethod.POST);
            if (execution_success) {
                System.out.println("Successfully added " + newEntity.getBeanName() + " to the system!");
            }
        }

        
        
    }

    private static void addTag(List<String> commandElements) {
        //todo check that the user is an admin
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'add' command is used incorrectly, name not provided.\n\tPlease run 'bean help' for help.");
            return;
        } else if (commandElements.get(0).isEmpty()) {
            System.out.println("Please enter an actual tag name");
            return;
        } else {

            String url = ClientApplication.endpoint + "/tag/add";
            RestTemplate restTemplate = new RestTemplate();
            Tag newEntity = new Tag(commandElements.get(0));

            boolean execution_success = handleRequest(url, newEntity, HttpMethod.POST);
            if (execution_success) {
                System.out.println("Successfully added " + newEntity.getTag_name() + " to the system!");
            }
        }
        
    }

    private static void removeBean(List<String> commandElements){
        //todo check that the user is an admin
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'remove' command is used incorrectly no BeanName provided.\n\tPlease run 'bean help' for help.");
            return;
        } else if (commandElements.get(0).isEmpty()) {
            System.out.println("Please enter an actual bean name");
            return;
        } else {
            
            String url = ClientApplication.endpoint + "/favoritebean/remove";
            FavoriteBean newEntity = new FavoriteBean(commandElements.get(0));

            boolean execution_success = handleRequest(url, newEntity, HttpMethod.POST);
            if (execution_success) {
                System.out.println("Successfully removed " + newEntity.getBeanName() + " from the system!");
            }
        }
    }

    private static void removeTag(List<String> commandElements){
        //todo check that the user is an admin
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'remove' command is used incorrectly no Tag name provided.\n\tPlease run 'bean help' for help.");
            return;
        } else if (commandElements.get(0).isEmpty()) {
            System.out.println("Please enter an actual tag name");
            return;
        } else {
            
            String url = ClientApplication.endpoint + "/tag/remove";
            Tag newEntity = new Tag(commandElements.get(0));

            boolean execution_success = handleRequest(url, newEntity, HttpMethod.POST);
            if (execution_success) {
                System.out.println("Successfully removed " + newEntity.getTag_name() + " from the system!");
            }
        }
    }

    private static void ban(List<String> commandElements){
        if (!ClientApplication.isAdmin) {
            System.out.println("Only admins have access to this command.");
            return;
        }
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'ban' command is used incorrectly: BeanName provided.\n\tPlease run 'bean help' for help.");
            return;
        } else if (commandElements.size() == 1) {
            System.out.print("Bean's new ban status (true/false): ");
            commandElements.add(scanner.nextLine());            
        }

        String beanName = commandElements.get(0);
        boolean is_banned;
        if (beanName.isEmpty()) {
            System.out.println("Please enter an actual bean name");
            return;
        } else if (!Arrays.asList("true", "false").contains(commandElements.get(1).toLowerCase())) {
            System.out.println("Invalid input argument for banned status");
            return;
        } else {
            is_banned = Boolean.parseBoolean(commandElements.get(1));
        }

        String url = ClientApplication.endpoint + "/favoritebean/edit";
        FavoriteBean requestBean = new FavoriteBean(beanName, is_banned);
        boolean execution_success = handleRequest(url, requestBean, HttpMethod.POST);
        if (execution_success) {
            System.out.println("Successfully changed " + beanName + "'s status.");
        }

    }

    private static <T> boolean handleRequest(String url, T requestBody, HttpMethod requestType) {
        try {        
            var response = executeClassRequest(url, requestBody, requestType, Object.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return true;
            }
        } catch (HttpClientErrorException.BadRequest ex) {
            System.out.println("Invalid request: " + ex.getResponseBodyAsString());
        } catch (HttpClientErrorException.NotFound ex) {
            System.out.println("Invalid request: Requested item not found");
        }
        return false;
    }

    @SuppressWarnings("null")
    public static <T, M> ResponseEntity<M> executeClassRequest(String url, T requestBody, HttpMethod requestType, Class<M> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AuthenticationProcess.getAccessToken());
        HttpEntity<T> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        // Sending the request and getting the response
        ResponseEntity<M> responseEntity = restTemplate.exchange(
                url,
                requestType,
                requestEntity,
                responseType);

        return responseEntity;
    }

    @SuppressWarnings("null")
    public static <T, M> ResponseEntity<M> executeViewRequest(String url, T requestBody, HttpMethod requestType, ParameterizedTypeReference<M> responseType) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AuthenticationProcess.getAccessToken());

        HttpEntity<T> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        // Sending the request and getting the response
        ResponseEntity<M> responseEntity = restTemplate.exchange(
                url,
                requestType,
                requestEntity,
                responseType);

        return responseEntity;
    }

}


