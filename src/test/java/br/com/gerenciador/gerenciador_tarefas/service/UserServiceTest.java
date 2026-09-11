package br.com.gerenciador.gerenciador_tarefas.service;

import br.com.gerenciador.gerenciador_tarefas.dto.UserRequest;
import br.com.gerenciador.gerenciador_tarefas.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldNotCreateUserWithExistingEmail() {

        UserRequest request = new UserRequest();

        request.setName("Kaua");
        request.setEmail("kaua@email.com");
        request.setPassword("123456");

        when(userRepository.existsByEmail("kaua@email.com"))
                .thenReturn(true);

        assertThrows(
                RuntimeException.class,
                () -> userService.create(request)
        );
    }
}