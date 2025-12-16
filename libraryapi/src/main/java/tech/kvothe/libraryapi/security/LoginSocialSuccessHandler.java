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
import java.util.List;

@Component
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String DEFAULT_PASSWORD = "202200200220";
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

        if (user == null)
            user = singUp(email);

        CustomAuthentication customAuthentication = new CustomAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(customAuthentication);

        super.onAuthenticationSuccess(request, response, customAuthentication);

    }

    public User singUp(String email) {
        User user = new User();
        user.setEmail(email);
        user.setLogin(getLoginFromEmail(email));
        user.setPassword(DEFAULT_PASSWORD);
        user.setRoles(List.of("USER"));

        return userService.save(user);
    }

    private String getLoginFromEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
