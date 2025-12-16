package tech.kvothe.libraryapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.kvothe.libraryapi.dto.UserDTO;
import tech.kvothe.libraryapi.mapper.UserMapper;
import tech.kvothe.libraryapi.service.UserService;

import java.net.URI;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class UserController implements GenericController{

    private final UserService userService;
    private final UserMapper mapper;

    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid UserDTO request) {
        var user = userService.save(mapper.toEntity(request));
        URI location = generateHeaderLocation(user.getId());
        return ResponseEntity.created(location).build();
    }
}
