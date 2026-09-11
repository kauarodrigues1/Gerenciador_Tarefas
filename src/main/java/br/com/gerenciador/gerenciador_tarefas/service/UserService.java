package br.com.gerenciador.gerenciador_tarefas.service;

import br.com.gerenciador.gerenciador_tarefas.dto.UserRequest;
import br.com.gerenciador.gerenciador_tarefas.entity.User;
import br.com.gerenciador.gerenciador_tarefas.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public UserService(
            JwtService jwtService, UserRepository userRepository,
            PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        String hashedPassword =
                passwordEncoder.encode(userRequest.getPassword());

        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }
    public String authenticate(String email, String rawPassword) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        rawPassword
                )
        );

        User user = findByEmail(email);

        return jwtService.generateToken(user);
    }
}