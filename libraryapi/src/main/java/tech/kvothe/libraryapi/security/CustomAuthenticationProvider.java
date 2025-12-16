package tech.kvothe.libraryapi.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tech.kvothe.libraryapi.model.User;
import tech.kvothe.libraryapi.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String typedPassword = authentication.getCredentials().toString();

        User user = userService.getByLogin(login);

        if (user == null)
            throw userNotFound();

        String encryptPassword = user.getPassword();
        boolean doPasswordMatch = passwordEncoder.matches(typedPassword, encryptPassword);

        if (doPasswordMatch)
            return new CustomAuthentication(user);

        throw userNotFound();
    }

    private static UsernameNotFoundException userNotFound() {
        return new UsernameNotFoundException("Usuário e/ou senha incorretos!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
