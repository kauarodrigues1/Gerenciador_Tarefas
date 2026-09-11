package br.com.gerenciador.gerenciador_tarefas.service;

import br.com.gerenciador.gerenciador_tarefas.dto.TaskRequest;
import br.com.gerenciador.gerenciador_tarefas.entity.Task;
import br.com.gerenciador.gerenciador_tarefas.entity.User;
import br.com.gerenciador.gerenciador_tarefas.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(
            TaskRepository taskRepository,
            UserService userService
    ) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public Task create(TaskRequest taskRequest, String email) {

        User user = userService.findByEmail(email);

        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCompleted(taskRequest.isCompleted());
        task.setUser(user);

        return taskRepository.save(task);
    }

    public List<Task> findAllByUser(String email) {

        User user = userService.findByEmail(email);

        return taskRepository.findByUserId(user.getId());
    }

    public Task findById(Long id, String email) {

        User user = userService.findByEmail(email);

        return taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task update(Long id, TaskRequest taskRequest, String email) {

        User user = userService.findByEmail(email);

        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCompleted(taskRequest.isCompleted());

        return taskRepository.save(task);
    }

    public void delete(Long id, String email) {

        User user = userService.findByEmail(email);

        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskRepository.delete(task);
    }
}