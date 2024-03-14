package com.bbd.BeanClient;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

import ch.qos.logback.core.pattern.PostCompileProcessor;


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

    public static int userId;


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
        //TODO: implement tag functionality
        List<String> allTags = List.of("LIMA", "KIDNEY", "BLOOD");
        while(postTag.equals("")){
            System.out.println("All Possible Tags Are:");
            allTags.forEach(System.out::println);
            System.out.print("PostTag: ");
            postTag = scanner.nextLine();
            if(!allTags.contains(postTag.toUpperCase())){
                postTag = "";
                System.out.println("Please supply a valid tag from the supplied list");
                return;
            }
        }
        makePost(postTitle, postContent, postTag);
    }

    public static void makePost(String postTitle, String postContent, String postTag){

        String endpoint = "http://localhost:5000";
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Post newPost = new Post(userId,1,postTitle,postContent,currentTime);
        
        String createPostUrl = endpoint + "/createpost";
        boolean execution_success = handleRequest(createPostUrl, newPost, HttpMethod.POST);
        if (execution_success) {
            System.out.println("Successfully created Post");
        }

    }

    public static void makeComment(int postId, String commentContent){

        String endpoint = "http://localhost:5000";
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Comment newComment = new Comment(postId,userId,commentContent,currentTime);
        
        String createCommentUrl = endpoint + "/createcomment";
        boolean execution_success = handleRequest(createCommentUrl, newComment, HttpMethod.POST);
        if (execution_success) {
            System.out.println("Successfully created comment on post.");
        }

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
        ClientApplication.profileGet();
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

    public static int getTagID(String name)
    {
        int tagId = 1;
        String endpoint = "http://localhost:5000";
        String url = endpoint + "/tag/find";
        Tag requestTag = new Tag(name);
        ResponseEntity<Tag> response = executeClassRequest(url,requestTag,HttpMethod.POST,Tag.class);
        if(response.getBody()!=null){
            tagId = response.getBody().getTag_id(); 
        }else{
            System.out.println("NO TAG PROVIDED!!!!!Defaulting :'(");
        }
        return tagId;
    }

    public static boolean checkPostByID(int postID)
    {
        String endpoint = "http://localhost:5000";
        String url = endpoint + "/findpost";
        Post requestPost = new Post(postID);
        ResponseEntity<Post> response = executeClassRequest(url,requestPost,HttpMethod.POST,Post.class);
        if(response.getBody()!=null){
            return true;
        }else{
            System.out.println("Selected post does not exist");
            return false;
        }
    }

    public static boolean checkReactionByID(int reactionID)
    {
        String endpoint = "http://localhost:5000";
        String url = endpoint + "/findreaction";
        PostReaction requestReaction = new PostReaction(reactionID);
        ResponseEntity<Post> response = executeClassRequest(url,requestReaction,HttpMethod.POST,Post.class);
        if(response.getBody()!=null){
            return true;
        }else{
            System.out.println("Selected reaction does not exist");
            return false;
        }
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
        makeComment( Integer.parseInt(postID),comment);
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
            if(checkPostByID(Integer.parseInt("1")) && checkReactionByID(Integer.parseInt("1"))){
                System.out.println("Please enter the ID of a post that exists");
                return;
            }else{

            }
            //todo make sure the reaction is a valid reaction

        } else {
            //todo make sure that ID contains a valid commentID
            //todo the react command was used to react to a COMMENT
            //todo make sure the reaction is a valid reaction
        } 
    }

    public static void makeReaction(int postID, int reactID){
        String endpoint = "http://localhost:5000";
        PostReaction newReaction = new PostReaction(postID, reactID);
        
        String createReactUrl = endpoint + "/postreaction";
        boolean execution_success = handleRequest(createReactUrl, newReaction, HttpMethod.POST);
        if (execution_success) {
            System.out.println("Successfully added reaction!");
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
            case "post-all":
                //todo view all posts
                break;
            case "post-recent":
                //todo view most recent post
                break;
            case "post-me":
                //todo view users own posts
                break;
            case "prof":
                //todo view your own profile
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
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<List<Tag>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
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
            RestTemplate restTemplate = new RestTemplate();
            FavoriteBean newBean = new FavoriteBean(commandElements.get(0), Boolean.parseBoolean(commandElements.get(1)));

            try {        
                var response = restTemplate.postForEntity(url, newBean, Object.class);            
                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Successfully added " + newBean.getBeanName() + " to the system!");
                }
            } catch (HttpClientErrorException.BadRequest ex) {
                System.out.println("Invalid request: " + ex.getResponseBodyAsString());
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

            try {        
                var response = restTemplate.postForEntity(url, newEntity, Object.class);            
                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Successfully added " + newEntity.getTag_name() + " to the system!");
                }
            } catch (HttpClientErrorException.BadRequest ex) {
                System.out.println("Invalid request: " + ex.getResponseBodyAsString());
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
            RestTemplate restTemplate = new RestTemplate();
            FavoriteBean newEntity = new FavoriteBean(commandElements.get(0));

            try {        
                var response = restTemplate.postForEntity(url, newEntity, Object.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Successfully removed " + newEntity.getBeanName() + " from the system!");
                }
            } catch (HttpClientErrorException.BadRequest ex) {
                System.out.println("Invalid request: " + ex.getResponseBodyAsString());
            } catch (HttpClientErrorException.NotFound ex) {
                System.out.println("Invalid request: Bean does not exist");
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
            RestTemplate restTemplate = new RestTemplate();
            Tag newEntity = new Tag(commandElements.get(0));

            try {        
                var response = restTemplate.postForEntity(url, newEntity, Object.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Successfully removed " + newEntity.getTag_name() + " from the system!");
                }
            } catch (HttpClientErrorException.BadRequest ex) {
                System.out.println("Invalid request: " + ex.getResponseBodyAsString());
            } catch (HttpClientErrorException.NotFound ex) {
                System.out.println("Invalid request: Tag does not exist");
            }
        }
    }


    private static void ban(List<String> commandElements){
        //todo check that the user is an admin
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
    public static <T> ResponseEntity<T> executeViewRequest(String url, T requestBody, HttpMethod requestType, ParameterizedTypeReference<T> responseType) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AuthenticationProcess.getAccessToken());

        HttpEntity<T> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        // Sending the request and getting the response
        ResponseEntity<T> responseEntity = restTemplate.exchange(
                url,
                requestType,
                requestEntity,
                responseType);

        return responseEntity;
    }

}


