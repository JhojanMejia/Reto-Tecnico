package com.reto.tasks.dto;

import com.reto.tasks.model.Priority;
import com.reto.tasks.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class TaskRequestDTO {
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 80, message = "El título debe tener entre 3 y 80 caracteres")
    private String title;

    @Size(max = 250, message = "La descripción no puede exceder los 250 caracteres")
    private String description;

    private Status status;

    @NotNull(message = "La prioridad es obligatoria")
    private Priority priority;

    private LocalDate dueDate;

    public TaskRequestDTO() {}


    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}