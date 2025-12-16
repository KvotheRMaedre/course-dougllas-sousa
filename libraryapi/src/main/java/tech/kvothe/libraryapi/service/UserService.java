package tech.kvothe.libraryapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.kvothe.libraryapi.model.User;
import tech.kvothe.libraryapi.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
