package com.reto.tasks.service;

import com.reto.tasks.dto.TaskRequestDTO;
import com.reto.tasks.dto.TaskResponseDTO;
import com.reto.tasks.exception.BusinessRuleException;
import com.reto.tasks.model.Priority;
import com.reto.tasks.model.Status;
import com.reto.tasks.model.Task;
import com.reto.tasks.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponseDTO createTask(TaskRequestDTO request) {
        // Regla de Negocio B: Priority HIGH requiere dueDate
        if (request.getPriority() == Priority.HIGH && request.getDueDate() == null) {
            throw new BusinessRuleException("La fecha de vencimiento es obligatoria para tareas de prioridad ALTA");
        }

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        task.setStatus(request.getStatus() != null ? request.getStatus() : Status.TODO);

        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());

        Task savedTask = taskRepository.save(task);
        return mapToResponseDTO(savedTask);
    }

    private TaskResponseDTO mapToResponseDTO(Task task) {
        TaskResponseDTO response = new TaskResponseDTO();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        response.setDueDate(task.getDueDate());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        return response;
    }
    public java.util.List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(java.util.stream.Collectors.toList());
    }
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        if (request.getStatus() == Status.DONE &&
                task.getDueDate() != null &&
                task.getDueDate().isBefore(java.time.LocalDate.now())) {
            throw new BusinessRuleException("No se puede marcar como completada una tarea vencida");
        }
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());

        Task updatedTask = taskRepository.save(task);
        return mapToResponseDTO(updatedTask);
    }
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));
        return mapToResponseDTO(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Tarea no encontrada");
        }
        taskRepository.deleteById(id);
    }
    public java.util.Map<String, Long> getTaskStatistics() {
        return taskRepository.findAll().stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        task -> task.getStatus().name(),
                        java.util.stream.Collectors.counting()
                ));
    }

}