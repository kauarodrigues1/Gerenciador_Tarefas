package br.com.gerenciador.gerenciador_tarefas.controller;

import br.com.gerenciador.gerenciador_tarefas.dto.TaskRequest;
import br.com.gerenciador.gerenciador_tarefas.entity.Task;
import br.com.gerenciador.gerenciador_tarefas.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task create(
            @Valid @RequestBody TaskRequest taskRequest,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return taskService.create(taskRequest, email);
    }

    @GetMapping
    public List<Task> findAll(Authentication authentication) {

        String email = authentication.getName();

        return taskService.findAllByUser(email);
    }

    @GetMapping("/{id}")
    public Task findById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return taskService.findById(id, email);
    }

    @PutMapping("/{id}")
    public Task update(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest taskRequest,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return taskService.update(id, taskRequest, email);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();

        taskService.delete(id, email);
    }
}