package com.bbd.BeanClient;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class UserInput {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        String userInput = "";
        while(true){
            System.out.print("Enter your text: ");
            userInput = scanner.nextLine();
            processCommand(userInput);
        }
    }


    private static void processCommand(String str){
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
            case "prof":
                profile(commandElements);
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
        System.out.println("Help Screen");
        //todo display the help screen
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

    private static void profile(List<String> commandElements){
        //todo I am not gonna make this now LOL
        //the stuff to make a profile needs to be in the command or the user will be prompted to make one, still unsure
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
                //todo view a list of all favourite bean (list shows the beans and if they are banned or not)
                break;
        
            default:
            System.out.println(third + " is not a valid option.\n\tRun 'bean help' for a list of options.");
                return;
        }
    }


    private static void add(List<String> commandElements){
        //todo check that the user is an admin
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'add' command is used incorrectly no BeanName provided.\n\tPlease run 'bean help' for help.");
            return;
        }

        String beanName = commandElements.get(0);
        //todo run the add bean command
        //todo check if the bean doesn't already exist
    }


    private static void rem(List<String> commandElements){
        //todo check that the user is an admin
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'remove' command is used incorrectly no BeanName provided.\n\tPlease run 'bean help' for help.");
            return;
        }
        
        String beanName = commandElements.get(0);
        //todo run the remove bean command
        //todo check if the bean does exist
    }


    private static void ban(List<String> commandElements){
        //todo check that the user is an admin
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'ban' command is used incorrectly no BeanName provided.\n\tPlease run 'bean help' for help.");
            return;
        }

        String beanName = commandElements.get(0);
        //todo run the ban bean command
        //todo check if the bean does exist
    }

}


