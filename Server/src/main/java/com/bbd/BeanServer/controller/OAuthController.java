package com.bbd.BeanServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuthController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;


    @GetMapping("/login/oauth2/code/github")
    public String oauth2LoginCallback(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User oauthUser = oauthToken.getPrincipal();

            String login = oauthUser.getAttribute("login");
            String name = oauthUser.getAttribute("name");
            // Other user details...

            OAuth2AccessToken accessToken = getAccessToken(oauthToken);

            // Optionally, you can save or update the user in your database

            // Redirect to a success page or home page
            return "redirect:/";
        } else {
            // Handle authentication failure
            return "redirect:/login?error";
        }
    }

    private OAuth2AccessToken getAccessToken(OAuth2AuthenticationToken oauthToken) {
        String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
        String principalName = oauthToken.getName();
        return authorizedClientService.loadAuthorizedClient(clientRegistrationId, principalName)
                .getAccessToken();
    }
}