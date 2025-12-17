package tech.kvothe.libraryapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.kvothe.libraryapi.model.User;
import tech.kvothe.libraryapi.service.UserService;

import java.io.IOException;

@Component
public class JwtCustomAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    public JwtCustomAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (shouldConvert(authentication)) {
            String login = authentication.getName();
            User user = userService.getByLogin(login);

            if (user != null) {
                authentication = new CustomAuthentication(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    public boolean shouldConvert(Authentication authentication) {
        return authentication instanceof JwtAuthenticationToken;
    }
}
