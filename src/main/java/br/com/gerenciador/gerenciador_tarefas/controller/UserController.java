package br.com.gerenciador.gerenciador_tarefas.controller;

import br.com.gerenciador.gerenciador_tarefas.dto.LoginRequest;
import br.com.gerenciador.gerenciador_tarefas.dto.UserRequest;
import br.com.gerenciador.gerenciador_tarefas.entity.User;
import br.com.gerenciador.gerenciador_tarefas.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody UserRequest userRequest) {
        return userService.create(userRequest);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.authenticate(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
    }
}