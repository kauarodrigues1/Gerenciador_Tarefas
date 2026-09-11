package br.com.gerenciador.gerenciador_tarefas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {

    @NotBlank
    private String title;

    @Size(max = 500)
    private String description;

    private boolean completed;
}