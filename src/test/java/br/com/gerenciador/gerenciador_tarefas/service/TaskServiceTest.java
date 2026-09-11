package br.com.gerenciador.gerenciador_tarefas.service;

import br.com.gerenciador.gerenciador_tarefas.dto.TaskRequest;
import br.com.gerenciador.gerenciador_tarefas.entity.Task;
import br.com.gerenciador.gerenciador_tarefas.entity.User;
import br.com.gerenciador.gerenciador_tarefas.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldCreateTaskForUser() {

        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");

        TaskRequest request = new TaskRequest();
        request.setTitle("Study Spring Boot");
        request.setDescription("Study JUnit and Mockito");
        request.setCompleted(false);

        Task savedTask = new Task();
        savedTask.setId(10L);
        savedTask.setTitle("Study Spring Boot");
        savedTask.setDescription("Study JUnit and Mockito");
        savedTask.setCompleted(false);
        savedTask.setUser(user);

        when(userService.findByEmail("user@email.com"))
                .thenReturn(user);

        when(taskRepository.save(any(Task.class)))
                .thenReturn(savedTask);

        Task result = taskService.create(request, "user@email.com");

        assertEquals("Study Spring Boot", result.getTitle());
        assertEquals("Study JUnit and Mockito", result.getDescription());
        assertFalse(result.isCompleted());
        assertEquals(user, result.getUser());
    }

    @Test
    void shouldNotFindTaskFromAnotherUser() {

        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");

        when(userService.findByEmail("user@email.com"))
                .thenReturn(user);

        when(taskRepository.findByIdAndUserId(10L, 1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> taskService.findById(10L, "user@email.com")
        );
    }
    @Test
    void shouldUpdateTaskForUser() {

        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");

        Task existingTask = new Task();
        existingTask.setId(10L);
        existingTask.setTitle("Old title");
        existingTask.setDescription("Old description");
        existingTask.setCompleted(false);
        existingTask.setUser(user);

        TaskRequest request = new TaskRequest();
        request.setTitle("New title");
        request.setDescription("New description");
        request.setCompleted(true);

        when(userService.findByEmail("user@email.com"))
                .thenReturn(user);

        when(taskRepository.findByIdAndUserId(10L, 1L))
                .thenReturn(Optional.of(existingTask));

        when(taskRepository.save(existingTask))
                .thenReturn(existingTask);

        Task result = taskService.update(
                10L,
                request,
                "user@email.com"
        );

        assertEquals("New title", result.getTitle());
        assertEquals("New description", result.getDescription());
        assertTrue(result.isCompleted());
        assertEquals(user, result.getUser());
    }
}