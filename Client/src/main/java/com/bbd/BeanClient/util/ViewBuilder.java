package com.bbd.BeanClient.util;

import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

import com.bbd.BeanClient.ClientApplication;
import com.bbd.shared.models.Comment;
import com.bbd.shared.models.FavoriteBean;
import com.bbd.shared.models.Post;
import com.bbd.shared.models.Tag;
import com.bbd.shared.models.UserRole;
import com.bbd.shared.models.Users;

public class ViewBuilder {
  
  public static String buildProfile(Users user) {
    
    StringBuilder sb = new StringBuilder();
    sb.append("User Profile:\n");
    sb.append("-------------\n");
    sb.append("User ID: ").append(user.getUser_id()).append("\n");
    sb.append("Username: ").append(user.getUsername()).append("\n");
    sb.append("Bio: ").append(user.getBio()).append("\n");
    sb.append("User Role ID: ").append(getRolebyID(user.getUser_role_id())).append("\n");
    sb.append("Favorite Bean ID: ").append(getBeanbyID(user.getFavorite_bean_id())).append("\n");
    sb.append("-------------\n");
    
    return sb.toString();
  }

  public static String buildPost(Post post) {

    StringBuilder sb = new StringBuilder();
    sb.append("Post Information:\n");
    sb.append("-----------------\n");
    sb.append("Post ID: ").append(post.getPostId()).append("\n");
    sb.append("User: ").append(getUserbyID(post.getUserId())).append("\n");
    sb.append("Tag ID: ").append(getTagbyID(post.getTagId())).append("\n");
    sb.append("Post Title: ").append(post.getPostTitle()).append("\n");
    sb.append("Post Content: ").append(post.getPostContent()).append("\n");
    sb.append("Date Posted: ").append(post.getDatePosted()).append("\n");
    sb.append("-----------------\n");
    return sb.toString();
  }

  public static String buildComment(Comment comment) {

    StringBuilder sb = new StringBuilder();
    sb.append("Comment ID: ").append(comment.getComment_id()).append("\n");
    sb.append("User: ").append(getUserbyID(comment.getUser_id())).append("\n");
    sb.append("Comment: ").append(comment.getComment_info()).append("\n");
    // sb.append("Date Posted: ").append(comment.getDate_posted()).append("\n");

    return sb.toString();
  }


  private static String getUserbyID(int userID) {
    String url = ClientApplication.endpoint + "/user/find/" + userID;

    try {
      return authGet(url, Users.class).getUsername();
    } catch (Exception e) {
      return "None";
    }
  }

  private static String getTagbyID(int tagID) {
    String url = ClientApplication.endpoint + "/tag/find/" + tagID;
    try {
      return authGet(url, Tag.class).getTag_name();
    } catch (Exception e) {
      System.out.println(e.toString());
      return "None";
    }
  }

  private static String getRolebyID(int roleID) {
    String url = ClientApplication.endpoint + "/role/find/" + roleID;
    try {
      return authGet(url, UserRole.class).getRole_name();
    } catch (Exception e) {
      return "None";
    }
  }

  private static String getBeanbyID(int beanID) {
    String url = ClientApplication.endpoint + "/favoritebean/find/" + beanID;
    try {
      
      return authGet(url, FavoriteBean.class).getBeanName();
    } catch (Exception e) {
      return "None";
    }
  }

  private static <T> T authGet(@NonNull String url, @NonNull Class<T> returnType) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(AuthenticationProcess.getAccessToken());

    HttpEntity<T> requestEntity = new HttpEntity<>(null, headers);
    RestTemplate restTemplate = new RestTemplate();
    HttpMethod exchangeMethod = HttpMethod.GET;
    // Sending the request and getting the response
    ResponseEntity<T> responseEntity = restTemplate.exchange(url, exchangeMethod, 
    requestEntity, returnType);
    return responseEntity.getBody();

  }



}
