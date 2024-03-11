package com.bbd.BeanClient;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class UserInput {
    public static void main(String[] args) {
        System.out.print("Enter your text: ");
        String response = getUserCommand();
        processCommand(response);
    }


    private static String getUserCommand(){
        String userInput = "";
        try (Scanner scanner = new Scanner(System.in)) {
            userInput = scanner.nextLine();
            scanner.close();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return userInput;
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
            //todo Throw an error???
        } else {
            commandElements.remove(0);
        }

        String second = commandElements.size() >= 1 ? commandElements.get(0) : "";
        switch (second) {
            case "help":
                help(commandElements);
                break;
            case "prof":
                //profile function
                break;
            case "post":
                post(commandElements);
                break;
            case "com":
                //comment function
                break;
            case "set":
                //set fav bean function
                break;
            case "react":
                //react function
                break;
            case "view":
                //view function
                break;
            case "add":
                //add function
                break;
            case "rem":
                //remove function
                break;
            case "ban":
                //ban function
                break;
            default:
                System.out.println("Command not recognised, please type 'bean help' for help with commands");
                //todo throw error??
                break;
        }
    }

    private static void help(List<String> commandElements){
        commandElements.remove(0);
        if(commandElements.size() >= 1){
            System.out.println("Note, the help command does not accept any other arguments");
        }
        //todo display the help screen
    }

    private static void post(List<String> commandElements){
        commandElements.remove(0);
        if(commandElements.size() == 0){
            System.out.println("The 'post' commands needs a PostTitle when using it.\nPlease run 'bean post <PostTitle>'");
        }

        @SuppressWarnings("unused") //todo remove this once postTitle is used
        String postTitle = commandElements.get(0);

        String postContent = "";
        Scanner scanner = new Scanner(System.in);
        while(postContent.equals("")){
            System.out.print("Please provide the post content:");
            postContent = scanner.nextLine();
        }
        String postTag = "";
        while(postTag.equals("")){
            System.out.println("All Possible Tags Are:");
            //todo get a list of all tags to display
            System.out.print("PostTag: ");
            postTag = scanner.nextLine();
        }
        scanner.close();
        //todo run the make post function passing in
        // postTitle, postContent, postTag
    }

}


