package com.bbd.BeanServer.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
 
public class UserAuthenticationService{
 
    public void authenticateUser() {
        // Retrieve the authentication object from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
 
        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Retrieve the username or any other details
            String username = authentication.getName();
            // Or you can cast it to UserDetails to access more details if needed
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Access other user details like authorities, etc.
            // userDetails.getAuthorities(), userDetails.getUsername(), etc.
            // Do something with the user information
            System.out.println("Logged in user: " + username);
        } else {
            // Handle unauthenticated access
            System.out.println("No user logged in");
        }
    }
    @GetMapping("/userinfo")
    public String getUserInfo(Authentication authentication) {
    System.out.println("Authentication: " + authentication);
    return "userinfo";
}
}