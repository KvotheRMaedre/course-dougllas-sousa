package tech.kvothe.libraryapi.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tech.kvothe.libraryapi.model.User;
import tech.kvothe.libraryapi.service.UserService;

import java.io.IOException;

@Component
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    public LoginSocialSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuthUser = auth2AuthenticationToken.getPrincipal();
        String email = oAuthUser.getAttribute("email");

        User user = userService.getByEmail(email);

        CustomAuthentication customAuthentication = new CustomAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(customAuthentication);

        super.onAuthenticationSuccess(request, response, customAuthentication);

    }
}
